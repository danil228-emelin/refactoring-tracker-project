package ru.ifmo.insys1.config;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.exception.LiquibaseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.service.MinIOMovieService;

import java.sql.SQLException;
import java.util.List;

@WebListener
@Slf4j
public class AppInitializer implements ServletContextListener {

    public static final String MINIO_URL = "http://localhost:9000";
    private static final String ACCESS_KEY = "mxlGjuAR4SlkJiaUUHAJ";
    private static final String SECRET_KEY = "c1bp1pcVKeOevkAfUB0dD7lzNoXkK53GNi9fqpAY";
    public static final String BUCKET_NAME = "movies-import-logs";

    @Inject
    private LiquibaseProducer liquibaseProducer;

    @Inject
    private MinIOMovieService minIOMovieService;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent event) {
        try {
            liquibaseProducer.runLiquibaseMigration();
            setMinIOClient();
        } catch (SQLException | LiquibaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private void setMinIOClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(MINIO_URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
        minIOMovieService.setMinioClient(client);
        createBucketIfNotExists(client);
        log.info("Connected to MinIO server, url: {}", MINIO_URL);
    }

    private void createBucketIfNotExists(MinioClient minioClient) throws Exception {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            boolean bucketExists = buckets.stream()
                    .anyMatch(bucket -> bucket.name().equals(BUCKET_NAME));
            if (!bucketExists) {
                log.warn("Bucket {} does not exist", BUCKET_NAME);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                log.warn("Creating bucket {}", BUCKET_NAME);
            }
        } catch (Exception e) {
            log.error("Error creating bucket", e);
            throw e;
        }
    }
}
