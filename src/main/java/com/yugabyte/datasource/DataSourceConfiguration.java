package com.yugabyte.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class DataSourceConfiguration {

    @Value("${datasources.count}")
    @Getter
    private int dataSourceCount;

    @Primary
    @Bean(name = "dataSource1")
    @ConfigurationProperties("datasources.datasource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource2")
    @ConfigurationProperties("datasources.datasource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource3")
    @ConfigurationProperties("datasources.datasource3")
    public DataSource dataSource3() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource4")
    @ConfigurationProperties("datasources.datasource4")
    public DataSource dataSource4() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource5")
    @ConfigurationProperties("datasources.datasource5")
    public DataSource dataSource5() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource6")
    @ConfigurationProperties("datasources.datasource6")
    public DataSource dataSource6() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource7")
    @ConfigurationProperties("datasources.datasource7")
    public DataSource dataSource7() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource8")
    @ConfigurationProperties("datasources.datasource8")
    public DataSource dataSource8() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource9")
    @ConfigurationProperties("datasources.datasource9")
    public DataSource dataSource9() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "dataSource10")
    @ConfigurationProperties("datasources.datasource10")
    public DataSource dataSource10() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_1, dataSource1());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_2, dataSource2());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_3, dataSource3());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_4, dataSource4());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_5, dataSource5());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_6, dataSource6());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_7, dataSource7());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_8, dataSource8());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_9, dataSource9());
        targetDataSources.put(DataSourceContext.DataSource.DATA_SOURCE_10, dataSource10());

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(dataSource1());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;

    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(multiRoutingDataSource());
        emf.setPackagesToScan("com.yugabyte.datasource");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan("com.yugabyte.datasource");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        return properties;
    }
}