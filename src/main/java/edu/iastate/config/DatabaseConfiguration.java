package edu.iastate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(value = "edu.iastate.repository", repositoryImplementationPostfix = "Impl")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
