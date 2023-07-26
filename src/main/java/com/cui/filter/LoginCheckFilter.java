package com.cui.filter;



import com.alibaba.fastjson.JSON;
import com.cui.common.BaseContext;
import com.cui.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 定义个人的过滤器
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter  implements Filter {
    //路径匹配器，支持通配符

    private  final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URI

        String uri = httpServletRequest.getRequestURI();
//        log.info("本次的请求路径是：{}",uri);

        //定义不需要处理的请求路径

        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**"
        };


        //2、判断本次请求是否需要处理
        boolean check = check(urls, uri);


        //3、如果不需要处理，则直接放行
        if(check) {
            log.info("本次请求{}不需要处理",uri);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
        if (httpServletRequest.getSession().getAttribute("employee") != null){
            log.info("用户已经登录 用户id为：{}",httpServletRequest.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long) httpServletRequest.getSession().getAttribute("employee"));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }


        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        log.info("用户未登录！！！");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param url
     * @return
     */
    public boolean check(String[] urls,String url){
        for (String url1 : urls) {
            boolean match = PATH_MATCHER.match(url1, url);
            if(match){
                return true;
            }
        }
        return false;
    }
}
