package br.com.monitum.init;



import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan("br.com.monitum")
@PropertySource("classpath:monitum.properties")
@EnableJpaRepositories(basePackages = {"br.com.monitum.repository", "br.com.monitum.*"})
@Import({ SecurityConfig.class })
public class SpringRootConfig {
	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() throws IllegalStateException, PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		dataSource.setDriverClass(env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_DATABASE_DRIVER).trim());
		dataSource.setJdbcUrl(env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_DATABASE_URL).trim());
		dataSource.setUser(env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_DATABASE_PASSWORD));
		dataSource.setMaxStatements(500);
		dataSource.setMaxIdleTime(1800);
		dataSource.setMaxPoolSize(50);
		dataSource.setMinPoolSize(2);
		dataSource.setInitialPoolSize(2);
		dataSource.setAcquireIncrement(3);
		dataSource.setIdleConnectionTestPeriod(3000);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalStateException, PropertyVetoException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan("br.com.monitum.entity");

		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PropertyConstants.PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PropertyConstants.PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PropertyConstants.PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
		return properties;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() throws IllegalStateException, PropertyVetoException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
	@Bean
    public SessionFactory sessionFactory() throws IllegalStateException, PropertyVetoException {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder
        	.scanPackages("br.com.monitum.entity")
            .addProperties(hibProperties());
        return builder.buildSessionFactory();
    }
	@Bean
    public HibernateTransactionManager txManager() throws IllegalStateException, PropertyVetoException {
        return new HibernateTransactionManager(sessionFactory());
    }
	@Bean
	public JavaMailSender mailSender() {
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.transport.protocol", env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_MAIL_PROTOCOL));
		javaMailProperties.put("mail.smtp.auth", env.getRequiredProperty(PropertyConstants.PROPERTY_MAIL_SMTP_AUTH));
		javaMailProperties.put("mail.smtp.starttls.enable",	env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_MAIL_STARTTLS));

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setHost(env.getRequiredProperty(PropertyConstants.PROPERTY_MAIL_HOST));
		mailSender.setPort(env.getRequiredProperty(PropertyConstants.PROPERTY_MAIL_PORT, Integer.class));
		mailSender.setUsername(env.getRequiredProperty(PropertyConstants.PROPERTY_MAIL_USERNAME));
		mailSender.setPassword(env.getRequiredProperty(PropertyConstants.PROPERTY_MAIL_PASSWORD));
		mailSender.setProtocol(env.getRequiredProperty(PropertyConstants.PROPERTY_NAME_MAIL_PROTOCOL));

		return mailSender;
	}
}
