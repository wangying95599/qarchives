package org.quetzaco.archives.util.config;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@ConditionalOnClass({EnableTransactionManagement.class})
@AutoConfigureAfter({DruidConfig.class, PageHelperAutoConfiguration.class})
@MapperScan({"org.quetzaco.archives.application.dao"})
public class MybatisConfig implements TransactionManagementConfigurer, EnvironmentAware {

    @Autowired
    @Qualifier("archivesDataSource")
    private DataSource archivesDataSource;

    private RelaxedPropertyResolver propertyResolver;

    /**
     * 创建sqlSessionFactoryBean 实例
     * 并且设置configtion 如驼峰命名.等等
     * 设置mapper 映射路径
     * 设置datasource数据源
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
       /* * 设置mybatis configuration 扫描路径 */
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
                .getResource(propertyResolver.getProperty("configLocation")));
        /** 设置datasource */
        sqlSessionFactoryBean.setDataSource(archivesDataSource);
        /** 设置typeAlias 包扫描路径 */
        sqlSessionFactoryBean.setTypeAliasesPackage(
        propertyResolver.getProperty("typeAliasesPackage"));

        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    @Qualifier("archivesDataSource")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(archivesDataSource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment,
                "mybatis.");
    }
}