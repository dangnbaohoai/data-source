package com.c1se44.school_connect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@PropertySources({ @PropertySource("classpath:/application.properties") })
//@EnableJpaRepositories(basePackages = {"com.c1se44.school_connect.repository"}
//			,entityManagerFactoryRef = "SConAEntityManager",
//			transactionManagerRef = "SConATransactionManager"
//)
//public class DatabaseConfigs {
//	@Autowired
//	private Environment env;
//
//	@Primary
//	@Bean
//	@ConfigurationProperties(prefix="spring.datasource")
//	public DataSourceProperties meatboxDataSourceProperties() {
//		return new DataSourceProperties();
//	}
//
//	@Bean(name="SConADataSource")
//	public DataSource dataSource() {
//		DataSourceProperties meatboxDataSourceProperties = meatboxDataSourceProperties();
//		return DataSourceBuilder.create()
//					.driverClassName(meatboxDataSourceProperties.getDriverClassName())
//					.url(meatboxDataSourceProperties.getUrl())
//					.username(meatboxDataSourceProperties.getUsername())
//					.password(meatboxDataSourceProperties.getPassword())
//					.build();
//	}
//
//	@Primary
//	@Bean(name ="SConAEntityManager")
//	public LocalContainerEntityManagerFactoryBean meatboxEntityManager() {
//		LocalContainerEntityManagerFactoryBean em
//					= new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource());
//		em.setPackagesToScan(new String[] { "com.c1se44.school_connect.entity" });
//
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setJpaProperties(additionalProperties());
//		return em;
//	}
//
//
//	@Primary
//	@Bean(name = "SConATransactionManager")
//	public PlatformTransactionManager meatboxTransactionManager() {
//		EntityManagerFactory factory = meatboxEntityManager().getObject();
//		return new JpaTransactionManager(factory);
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	Properties additionalProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//		properties.setProperty("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
//
//		return properties;
//	}
//}
