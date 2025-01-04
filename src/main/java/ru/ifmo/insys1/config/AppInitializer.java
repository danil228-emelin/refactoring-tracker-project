package ru.ifmo.insys1.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.exception.LiquibaseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@WebListener
@Slf4j
public class AppInitializer implements ServletContextListener {

    @Inject
    private LiquibaseProducer liquibaseProducer;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent event) {
        try {
            liquibaseProducer.runLiquibaseMigration();
        } catch (SQLException | LiquibaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
