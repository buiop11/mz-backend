package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@Lazy
@RequiredArgsConstructor
@MapperScan(basePackages = "com.matjzing.mapper")
public class MybatisConfig {

	private final ApplicationContext context;

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		// 실제 쿼리가 들어갈 xml 패키지 경로
		sessionFactory.setMapperLocations(context.getResources("classpath:/mapper/*.xml"));

		// Value Object를 선언해 놓은 package 경로
		// Mapper의 result, parameterType의 패키지 경로를 클래스만 작성 할 수 있도록 도와줌.
		sessionFactory.setTypeAliasesPackage("com.matjzing.dto");

		// Mybatis ResultSet 객체와 LocalDate / LocalDateTime 클래스를 바인딩 할 수 있도록 도와줌.
		sessionFactory.setTypeHandlersPackage("org.apache.ibatis.type.LocalDateTypeHandler");
		sessionFactory.setTypeHandlersPackage("org.apache.ibatis.type.LocalDateTimeTypeHandler");
		SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
		if (sqlSessionFactory != null) {
			sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
			sqlSessionFactory.getConfiguration().setLogImpl(StdOutImpl.class);
		}
		return sqlSessionFactory;
	}

	// Mybatis Template
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}