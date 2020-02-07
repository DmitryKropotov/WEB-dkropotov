package com.webapp.securityConfig;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Component
public class SpringSecurityFilterChain implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("This is init method in SecurityFilter. filterConfig is " + filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Principal principal;
        if (servletRequest instanceof HttpServletRequest) {
            principal = ((HttpServletRequest)servletRequest).getUserPrincipal();
            System.out.println(principal.getName());
            System.out.println(principal);
        }
        System.out.println("This is doFilter method in SecurityFilter. servletRequest is " + servletRequest + " ServletResponse is " + servletResponse +
               " FilterChain is " + filterChain);
        if (servletRequest != null && servletResponse != null && filterChain != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println("This is destroy method in SecurityFilter");
    }
}
