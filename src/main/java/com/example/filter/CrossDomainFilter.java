package com.example.filter;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器，解决前后端分离时前端项目的跨域请求报错
 */
@Component
@WebFilter(filterName = "crossDomain", urlPatterns = "/*")
public class CrossDomainFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        if(CorsUtils.isCorsRequest(request)){
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
            response.setHeader("Access-Control-Max-Age", "3600");
        }

        if(CorsUtils.isPreFlightRequest(request)){
            return;
        }
        filterChain.doFilter(request, response);
    }
}