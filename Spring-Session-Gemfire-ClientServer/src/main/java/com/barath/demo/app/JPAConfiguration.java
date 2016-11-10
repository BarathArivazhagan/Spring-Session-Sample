package com.barath.demo.app;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class JPAConfiguration {
	
	@Value("${spring.profiles.active}")
	private String[] profiles;
	
	
	@Value("${datasource.connection.${spring.profiles.active}.url}")
	private String dataSourceConnectionUrl;
	
	
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaDialect(hibernateJpaDialect());
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
		entityManagerFactory.setPersistenceUnitName("session-pcf");
		entityManagerFactory.setPackagesToScan("com.barath.demo.app");
		//entityManagerFactory.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
		return entityManagerFactory;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager 	transactionManager=new JpaTransactionManager();
		//transactionManager.setEntityManagerFactory( entityManagerFactory().getEntityManagerInterface().);
		transactionManager.setJpaDialect(hibernateJpaDialect());
		transactionManager.setDataSource(dataSource());
		
		return 	transactionManager;
	}
	
	
	@Bean
	public JpaDialect hibernateJpaDialect(){
		return new HibernateJpaDialect();
	}
	
	@Bean
	public JpaVendorAdapter hibernateJpaVendorAdapter(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter=new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.HSQL);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
		
	}
	
	
	@Bean
	public DataSource dataSource(){
		
		EmbeddedDatabaseBuilder builder=new EmbeddedDatabaseBuilder();
		EmbeddedDatabase database=builder.setType(EmbeddedDatabaseType.HSQL).setName("sessiondb").build();
		return (DataSource) database;
	}
	
	@Bean(initMethod="addUsers")
	public UserDetails details(){
		return new UserDetails();
	}
	

}
