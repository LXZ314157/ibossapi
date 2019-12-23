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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author lvxuezhan
 * @date 2019/11/3
 * 支付系统数据库配置
 **/
@Configuration
@MapperScan(basePackages = "cn.com.zx.ibossapi.mapper.pay", sqlSessionFactoryRef = "paySqlSessionFactory")
public class PayDataSourceConfig {

    @Bean(name = "payDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pay")
    public DataSource getpayDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "paySqlSessionFactory")
    public SqlSessionFactory paySqlSessionFactory(@Qualifier("payDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(// 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mapper/pay/*.xml"));
        return bean.getObject();
    }
    @Bean("paySqlSessionTemplate")
    public SqlSessionTemplate test1sqlsessiontemplate(@Qualifier("paySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}