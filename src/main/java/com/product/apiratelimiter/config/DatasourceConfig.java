/**
 * 
 */
package com.product.apiratelimiter.config;

import java.lang.invoke.MethodHandles;

import javax.inject.Named;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import lombok.Getter;
import lombok.Setter;

@Configuration
@MapperScan(basePackages = "com.product.apiratelimiter.mapper",
        sqlSessionFactoryRef = DatasourceConfig.SQL_SESSION_FACTORY_NAME)
@Getter
@Setter
public class DatasourceConfig {

    public static final String SQL_SESSION_FACTORY_NAME = "writeSessionFactory";
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    @Qualifier(DatabaseConfiguration.WRITE_DATASOURCE)
    DataSource writeDS;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String url;

    @Bean(name = DatasourceConfig.SQL_SESSION_FACTORY_NAME)
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean(
            @Named(DatabaseConfiguration.WRITE_DATASOURCE) final DataSource stagingDatasource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);

        sqlSessionFactoryBean.setDataSource(stagingDatasource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {

        return new DataSourceTransactionManager(writeDS);
    }
}
