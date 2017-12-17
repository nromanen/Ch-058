package com.shrralis.ssdemo1.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("com.shrralis.ssdemo1.repository")
@ComponentScan(basePackages = "com.shrralis.ssdemo1")
@PropertySource("classpath:application.properties")
public class AppConfig {
    private static final String DATABASE_DRIVER = "db.driver";
    private static final String DATABASE_PASS = "db.password";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USERNAME = "db.username";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENTITY_MANAGER_PACKAGES_TO_SCAN = "entity_manager.packages.to.scan";

    @Value("${environment.debug}")
    public static Boolean DEBUG;
    @Resource
    private Environment env;

//    @Bean
//    public IUserDAO getUserDAO() {
//        return new UserDAOImpl();
//    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(DATABASE_PASS));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.
                getRequiredProperty(ENTITY_MANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setJpaProperties(getHibProperties());
        return entityManagerFactoryBean;
    }

    private Properties getHibProperties() {
        Properties properties = new Properties();

        properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    /*@Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog("classpath:changeLog.xml");
        liquibase.setDataSource(getDataSource());
        return liquibase;
    }*/

    /*@Bean
    public SessionFactory getSessionFactory() {
        return new LocalSessionFactoryBuilder(getDataSource())
                .addAnnotatedClasses(Image.class)
                .addAnnotatedClasses(User.class)
                .buildSessionFactory();
    }

    @Bean
    public HibernateTemplate getHibernateTemplate() {
        return new HibernateTemplate(getSessionFactory());
    }

    @Bean
    public HibernateTransactionManager getHibernateTransManager() {
        return new HibernateTransactionManager(getSessionFactory());
    }*/
}
