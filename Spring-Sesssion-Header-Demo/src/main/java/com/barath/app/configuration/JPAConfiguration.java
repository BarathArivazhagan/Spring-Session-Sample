package com.barath.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
public class JPAConfiguration {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
		entityManagerFactory.setPersistenceUnitName("barath-session");
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaDialect(jpaDialect());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		return entityManagerFactory;
	}
	
	@Bean
	public DriverManagerDataSource dataSource(){
		
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
		
		HibernateJpaVendorAdapter jpaVendorAdapter=new  HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.HSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}

	@Bean
	public HibernateJpaDialect jpaDialect(){
		return new HibernateJpaDialect();
	}
	
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager transactionManager=new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(jpaDialect());
		return transactionManager;
	}
}
