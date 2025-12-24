package ru.ifmo.insys1.config.event;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

@ApplicationScoped
public class DataSourceProducer {

    private DataSource dataSource;

    @PostConstruct
    public void init() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/user");
        ds.setUser("user");
        ds.setPassword("1");
        this.dataSource = ds;
    }

    @Produces
    public DataSource getDataSource() {
        return dataSource;
    }
}