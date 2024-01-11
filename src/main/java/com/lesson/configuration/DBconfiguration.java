// configuration :: 배열, 배치, 환경설정

package com.lesson.configuration;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// @Configuration :: spring.datasource.hikari 로 시작하는 설정을 메서드에 매핑
@Configuration
@PropertySource("classpath:/application.properties")
public class DBconfiguration
{
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	// hikariConfig :: Connection Pool 라이브러리
	public HikariConfig hikariConfig()
	{
		return new HikariConfig();
	}
	
	@Bean
	// dataSource :: 데이터 소스 객체 생성
	public DataSource dataSource()
	{
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception
	{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.getObject();
		
		return factoryBean.getObject();
	}
	
	@Bean
	// sqlSession :: 데이터베이스 커넥션과 SQL 실행을 제어하는 객체 생성
	public SqlSessionTemplate sqlSession() throws Exception
	{
		return new SqlSessionTemplate(sqlSessionFactory());
	}
}
