package org.quetzaco.archives.util.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class OADruidConfig implements EnvironmentAware {
  private static final Log logger = LogFactory.getLog(OADruidConfig.class);
  private RelaxedPropertyResolver propertyResolver;

  @Bean("oaDataSource")
  public DruidDataSource oaDataSource(){
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(propertyResolver.getProperty("url"));
    datasource.setUsername(propertyResolver.getProperty("username"));
    datasource.setPassword(propertyResolver.getProperty("password"));
    datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
    datasource.setInitialSize(propertyResolver.getProperty("initialSize",Integer.class,1));
    datasource.setMinIdle(propertyResolver.getProperty("minIdle",Integer.class,1));
    datasource.setMaxActive(propertyResolver.getProperty("maxActive",Integer.class,20));
    datasource.setMaxWait(propertyResolver.getProperty("maxWait",Long.class,60000l));
    datasource.setTimeBetweenEvictionRunsMillis(propertyResolver.getProperty("timeBetweenEvictionRunsMillis",Long.class,60000l));
    datasource.setMinEvictableIdleTimeMillis(propertyResolver.getProperty("minEvictableIdleTimeMillis",Long.class,300000l));
    datasource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
    datasource.setTestWhileIdle(propertyResolver.getProperty("testWhileIdle",Boolean.class,true));
    datasource.setTestOnBorrow(propertyResolver.getProperty("testOnBorrow",Boolean.class,false));
    datasource.setTestOnReturn(propertyResolver.getProperty("testOnReturn",Boolean.class,false));
    datasource.setPoolPreparedStatements(propertyResolver.getProperty("poolPreparedStatements",Boolean.class,true));
    try {
      datasource.setFilters(propertyResolver.getProperty("filters","stat"));
    } catch (SQLException e) {
      logger.error("druid configuration initialization filter", e);
    }
    return datasource;
  }

  @Bean
  @Qualifier("oaDataSource")
  public JdbcTemplate jdbcTemplate(DruidDataSource oaDataSource){
    return new JdbcTemplate(oaDataSource);
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.propertyResolver = new RelaxedPropertyResolver(environment,"spring.oa.datasource.");
  }


}