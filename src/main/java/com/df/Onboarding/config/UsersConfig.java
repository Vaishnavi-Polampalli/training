package com.df.Onboarding.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import liquibase.integration.spring.SpringLiquibase;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "usersEntityManagerFactory",
        transactionManagerRef = "usersTransactionManager",
        basePackages = { "com.df.Onboarding.users.repo" }
)
public class UsersConfig {

    @Bean(name="usersDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.usersdb.datasource")
    public DataSource usersDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "usersEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                            @Qualifier("usersDataSource") DataSource accountDataSource) {
        return builder
                .dataSource(accountDataSource)
                .packages("com.df.Onboarding.users.model")
                .build();
    }

    @Bean(name = "usersTransactionManager")
    public PlatformTransactionManager usersTransactionManager(
            @Qualifier("usersEntityManagerFactory") EntityManagerFactory usersEntityManagerFactory) {
        return new JpaTransactionManager(usersEntityManagerFactory);
    }


    @Bean
    public SpringLiquibase usersLiquibase(@Qualifier("usersDataSource") DataSource usersDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(usersDataSource);
        liquibase.setChangeLog("classpath:changelog/liquibase-changelog.xml");
        return liquibase;
    }


}
