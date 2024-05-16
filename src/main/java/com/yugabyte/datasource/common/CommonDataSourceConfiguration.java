package com.yugabyte.datasource.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "commonEntityManagerFactory", transactionManagerRef = "commonTransactionManager", basePackages = "com.yugabyte.datasource.common")
public class CommonDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.common")
    public DataSourceProperties commonDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource commonDataSource() {
        return commonDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean commonEntityManagerFactory(
            @Qualifier("commonDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder.dataSource(dataSource).packages("com.yugabyte.datasource.common").build();
    }

    @Bean
    public PlatformTransactionManager commonTransactionManager(
            @Qualifier("commonEntityManagerFactory") LocalContainerEntityManagerFactoryBean commonEntityManagerFactory) {
        return new JpaTransactionManager(commonEntityManagerFactory.getObject());
    }
}