package io.fdlessard.codebites.order.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by fdlessard on 16-08-14.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("io.fdlessard.codebites.order.repositories")
public class PersistenceConfiguration {



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        Properties jpaProperties = new Properties();

        jpaProperties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        jpaProperties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:test");
        jpaProperties.put("javax.persistence.jdbc.username", "APP");
        jpaProperties.put("javax.persistence.jdbc.password", "APP");

        jpaProperties.put("javax.persistence.schema-generation.database.action", "create");
        jpaProperties.put("eclipselink.logging.parameters", "true");
        jpaProperties.put("eclipselink.logging.level", "FINEST");

        jpaProperties.put("eclipselink.target-database", "org.eclipse.persistence.platform.database.H2Platform");
        jpaProperties.put("eclipselink.weaving", "false");
        jpaProperties.put("eclipselink.ddl-generation", "drop-and-create-tables");

        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.H2Platform");
        jpaVendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(this.dataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.lessard.codesamples.order"});
        entityManagerFactoryBean.setPersistenceUnitName("MyTestPU");

        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }


    @Bean
    public DataSource dataSource() {

        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();

        return embeddedDatabase;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    @Bean
    public ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("UTF-8");
        populator.addScript(new ClassPathResource("data.sql"));
        return populator;
    }

}
