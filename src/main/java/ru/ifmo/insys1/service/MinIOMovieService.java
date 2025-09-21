package ru.ifmo.insys1.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Pair;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.FileUploadForm;
import ru.ifmo.insys1.request.ImportRequest;

import java.io.InputStream;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static ru.ifmo.insys1.config.AppInitializer.BUCKET_NAME;
import static ru.ifmo.insys1.config.AppInitializer.MINIO_URL;

@Slf4j
@Setter
@ApplicationScoped
public class MinIOMovieService {

    private static final String FILE_NAME = "movies";

    private static final String CONTENT_TYPE = "application/json";

    private MinioClient minioClient;

    @Inject
    private ImportService importService;

    public void deleteImportFile(String fileKey) {
        try {
            if (fileKey != null) {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(buildFileName(UUID.fromString(fileKey)))
                        .build());
            }
        } catch (Exception ex) {
            log.error("Failed to rollback file upload", ex);
            throw new ServiceException(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    public Pair<String, String> saveImportFile(FileUploadForm form) {
        try {
            InputStream movieStream = form.getFile();
            movieStream.reset();
            UUID fileKey = UUID.randomUUID();
            String fileName = buildFileName(fileKey);
            String fileUrl = uploadFile(
                    fileName,
                    movieStream,
                    movieStream.available()
            );
            return Pair.of(fileKey.toString(), fileUrl);
        } catch (Exception e) {
            log.error("Error saving import file", e);
            importService.persist(new ImportRequest(false, 0, null));
            throw new ServiceException(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    private String buildFileName(UUID fileKey) {
        return String.format("%s%s.json", FILE_NAME, fileKey);
    }

    private String getFileUrl(String key) {
        return String.format("%s/%s/%s", MINIO_URL, BUCKET_NAME, key);
    }

    private String uploadFile(String key, InputStream fileStream, long fileSize) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(key)
                .stream(fileStream, fileSize, -1)
                .contentType(CONTENT_TYPE)
                .build());
        return getFileUrl(key);
    }
}
