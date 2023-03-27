package com.app.starcom.config.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@EnableTransactionManagement
public class AppConfiguration {
	
		@Autowired
		Environment env;
	
		@Bean
		@Primary
		@ConfigurationProperties(prefix="spring.jpa")
		public JpaProperties starcomJpaProperties() {
			return new JpaProperties();
		}	
		
		@Bean
		@ConfigurationProperties(prefix="spring.datasource")
		public DataSourceProperties dataSourceProperties() {
			return new DataSourceProperties();
		}
		
		@Bean
		public LocalSessionFactoryBean sessionFactory() {
			LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource());
			sessionFactory.setPackagesToScan("com.app.starcom.model");  //setPackagesToScan({"com.baelung.hibernate.bootstrap.model"});
			sessionFactory.setHibernateProperties(hibernateProperties());
			return sessionFactory;
		}
	  
		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
			return new PersistenceExceptionTranslationPostProcessor();
		}
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(dataSourceProperties().getDriverClassName());   //("org.h2.Driver");
			dataSource.setUrl(dataSourceProperties().getUrl());    //("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
			dataSource.setUsername(dataSourceProperties().getUsername());
			dataSource.setPassword(dataSourceProperties().getPassword());
			return dataSource;
		}
		
		
		@Bean
		@Primary
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			
			HashMap<String, Object> properties = new HashMap<String, Object>();
			//properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
			//properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		    properties.put("hibernate.hbm2ddl.auto", "create");
		    properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		    properties.put("hibernate.format_sql", "true");
			
			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			vendorAdapter.setShowSql(starcomJpaProperties().isShowSql());
			vendorAdapter.setDatabase(starcomJpaProperties().getDatabase());
			vendorAdapter.setDatabasePlatform(starcomJpaProperties().getDatabasePlatform());
			vendorAdapter.setGenerateDdl(starcomJpaProperties().isGenerateDdl());
			
			
			em.setPersistenceUnitName("starcomdbContext");
			em.setPackagesToScan("com.app.starcom");
			em.setJpaVendorAdapter(vendorAdapter);
			em.setJpaPropertyMap(properties);
			em.setDataSource(dataSource());
			
			return em;
		}
		
		@Bean("transactionManager")
		@Autowired
		public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
		  final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		  transactionManager.setSessionFactory(sessionFactory().getObject());
		  return transactionManager;
		}
		
		Properties hibernateProperties() {
			Properties properties = new Properties();
			//properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
			//properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		    properties.put("hibernate.hbm2ddl.auto", "create");
		    properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		    properties.put("hibernate.format_sql", "true");
		    return properties;
		}		
}