package ru.ifmo.insys1.config;

import jakarta.annotation.Resource;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.ifmo.insys1.config.event.MigrationsCompleted;

import javax.sql.DataSource;
import java.sql.SQLException;

@ApplicationScoped
@TransactionManagement(TransactionManagementType.BEAN)
public class LiquibaseProducer {

    @Resource(lookup = "java:jboss/datasources/postgres")
    private DataSource dataSource;

    @Inject
    private Event<MigrationsCompleted> migrationsCompletedEvent;

    public void runLiquibaseMigration() throws SQLException, LiquibaseException {
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

        Liquibase liquibase = new Liquibase(
                "db/db.changelog.xml",
                new ClassLoaderResourceAccessor(),
                database
        );

        liquibase.update(new Contexts(), new LabelExpression());

        migrationsCompletedEvent.fire(new MigrationsCompleted());
    }
}
