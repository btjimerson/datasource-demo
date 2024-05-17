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

/**
 * Configuration for the data source for the gm_common database. This datasource
 * is shared across all users / plants.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "commonEntityManagerFactory", transactionManagerRef = "commonTransactionManager", basePackages = "com.yugabyte.datasource.common")
public class CommonDataSourceConfiguration {

    /**
     * Creates the properties for the common data source.
     * 
     * @return The properties for the common data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.common")
    public DataSourceProperties commonDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the common database.
     * 
     * @return The data source for the common database.
     */
    @Bean
    public DataSource commonDataSource() {
        return commonDataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the entity manager factory for the common data source.
     * 
     * @param dataSource The common data source.
     * @param builder    An autowired factory builder.
     * @return The entity manager factory for the common data source.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean commonEntityManagerFactory(
            @Qualifier("commonDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder.dataSource(dataSource).packages("com.yugabyte.datasource.common").build();
    }

    /**
     * Creates the transaction manager for the common entity manager.
     * 
     * @param commonEntityManagerFactory The entity manager factory for the common
     *                                   data source.
     * @return A transaction manager for the common entity manager.
     */
    @Bean
    public PlatformTransactionManager commonTransactionManager(
            @Qualifier("commonEntityManagerFactory") LocalContainerEntityManagerFactoryBean commonEntityManagerFactory) {
        return new JpaTransactionManager(commonEntityManagerFactory.getObject());
    }
}