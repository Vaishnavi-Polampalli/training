package com.df.Onboarding.config;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
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

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "phoneEntityManagerFactory",
        transactionManagerRef = "phoneTransactionManager",
        basePackages = { "com.df.Onboarding.phone.repo" }
)
public class PhoneNumberConfig {
    @Bean(name="phoneDataSource")
    @ConfigurationProperties(prefix="spring.phonedb.datasource")
    public DataSource phoneDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "phoneEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean phoneEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                            @Qualifier("phoneDataSource") DataSource accountDataSource) {
        return builder
                .dataSource(accountDataSource)
                .packages("com.df.Onboarding.phone.model")
                .build();
    }

    @Bean(name = "phoneTransactionManager")
    public PlatformTransactionManager phoneTransactionManager(
            @Qualifier("phoneEntityManagerFactory") EntityManagerFactory phoneEntityManagerFactory) {
        return new JpaTransactionManager(phoneEntityManagerFactory);
    }

    @Bean
    public SpringLiquibase phoneLiquibase(@Qualifier("phoneDataSource") DataSource phoneDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(phoneDataSource);
        liquibase.setChangeLog("classpath:changelog/phone-liquibase-changelog.xml");
        return liquibase;
    }
}
