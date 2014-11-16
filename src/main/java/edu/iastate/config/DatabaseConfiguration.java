package edu.iastate.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
//@EnableJpaRepositories(value = "edu.iastate.classes.repository", repositoryImplementationPostfix = "ISUImpl")
//@EnableJpaRepositories(repositoryImplementationPostfix = "Impl")
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {
    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private RelaxedPropertyResolver sourceDataPropertyResolver;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
        this.sourceDataPropertyResolver = new RelaxedPropertyResolver(environment, "spring.masterDatasource.");
    }

    public DataSource db2Data()
    {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(sourceDataPropertyResolver.getProperty("dataSourceClassName"));
        config.addDataSourceProperty("serverName", sourceDataPropertyResolver.getProperty("serverName"));
        config.addDataSourceProperty("portNumber", sourceDataPropertyResolver.getProperty("portNumber"));
        config.addDataSourceProperty("databaseName", sourceDataPropertyResolver.getProperty("databaseName"));

        config.addDataSourceProperty("user", sourceDataPropertyResolver.getProperty("username"));
        config.addDataSourceProperty("password", sourceDataPropertyResolver.getProperty("password"));
        config.addDataSourceProperty("driverType", sourceDataPropertyResolver.getProperty("driverType"));

        HikariDataSource db = new HikariDataSource(config);

        return db;
    }
}
