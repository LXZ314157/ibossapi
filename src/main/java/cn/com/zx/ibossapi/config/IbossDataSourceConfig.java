package cn.com.zx.ibossapi.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author lvxuezhan
 * @date 2019/11/3
 * iboss主数据库配置
 **/
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "cn.com.zx.ibossapi.mapper.iboss", sqlSessionFactoryRef = "ibossSqlSessionFactory")
public class IbossDataSourceConfig {

    @Bean(name = "ibossDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.iboss")
    @Primary // 表示这个数据源是默认数据源,必须要加
    public DataSource getibossDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ibossSqlSessionFactory")
    @Primary
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory ibossSqlSessionFactory(@Qualifier("ibossDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(// 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mapper/iboss/*.xml"));
        return bean.getObject();
    }

    @Bean("ibossSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate test1sqlsessiontemplate(@Qualifier("ibossSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}