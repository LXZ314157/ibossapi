package cn.com.zx.ibossapi.config;

import cn.com.zx.ibossapi.authorization.interceptor.LoginAuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 类似springmvc的配置
 * 将拦截器加入到配置中
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginAuthorizationInterceptor loginAuthorizationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthorizationInterceptor).addPathPatterns("/**");
    }

}
