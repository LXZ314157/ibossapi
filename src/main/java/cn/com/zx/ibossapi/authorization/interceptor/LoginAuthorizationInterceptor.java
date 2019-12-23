package cn.com.zx.ibossapi.authorization.interceptor;

import cn.com.zx.ibossapi.authorization.annotation.LoginAuthorization;
import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.util.HttpRequestUtil;
import cn.com.zx.ibossapi.util.LogUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;

/**
 * 登录校验拦截器
 */
@Component
public class LoginAuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(Constant.DEV.equals(env)){
            return true;
        }

        //如果不是映射到方法直接通过（比如访问静态资源）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        //获取类
        Class<?> clazz = handlerMethod.getBeanType();
        //获取方法
        Method method = handlerMethod.getMethod();

        //类名
        String className = handlerMethod.getBeanType().getSimpleName().toLowerCase().replaceAll("controller","");
        //方法名
        String methodName = handlerMethod.getMethod().getName();
        //返回类型
        Class<?> returnType = handlerMethod.getMethod().getReturnType();
        if(returnType.equals(String.class)){
            System.out.println("返回类型是String");
        }
        //参数名
        StringBuffer sb = new StringBuffer();
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        for(Parameter parameter : parameters){
            sb.append(parameter.getName()+"、");
        }
        System.out.println(sb.toString());
        if(clazz.getAnnotation(LoginAuthorization.class)!=null
                || method.getAnnotation(LoginAuthorization.class)!=null){
            //方法类型 GET、POST、PUT、DELETE
            String methodType = request.getMethod();
            System.out.println("methodType==="+methodName);
            System.out.println("getAuthType==="+request.getAuthType());
            System.out.println("getContextPath==="+request.getContextPath());
            System.out.println("getServletName==="+request.getHttpServletMapping().getServletName());
            System.out.println("getPathInfo==="+request.getPathInfo());
            System.out.println("getPathTranslated==="+request.getPathTranslated());
            System.out.println("getServerName==="+request.getServerName());
            System.out.println("getServerPort==="+request.getServerPort());
            System.out.println("getParameterNames==="+request.getParameterNames().toString());
            System.out.println("getContextPath==="+request.getContextPath());
            System.out.println("getQueryString==="+request.getQueryString());
            System.out.println("getRemoteUser==="+request.getRemoteUser());
            System.out.println("getRequestURI==="+request.getRequestURI());
            System.out.println("getContentType==="+request.getContentType());
            System.out.println("getRequestURL==="+request.getRequestURL());
            System.out.println("getServletPath==="+request.getServletPath());
            System.out.println("getServletContext==="+request.getServletContext());

            Object object = request.getSession().getAttribute("userId");
            if(object==null){
                //如果session过期则跳转到指定页面
                doResponse(method,request,response);
            }
        }
        return true;
    }

    private void doResponse(Method method,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Class<?> returnType = method.getReturnType();
        //AJAX时返回json
        if (Result.class.equals(returnType)) {
            Result result = new Result();
            result.setCode(402);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setStatus(200);
            try {
                response.getWriter().write(JSON.toJSONString(result));
            } catch (IOException ex) {
                LogUtil.getLogger(HttpRequestUtil.class).error(ex.getMessage(), ex);
            }
            return;
        }
        //默认返回无权限的错误页
        response.sendRedirect(MessageFormat.format("{0}/nopermission", request.getContextPath()));
    }
}
