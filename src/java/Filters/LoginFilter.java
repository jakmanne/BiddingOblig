/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Handler.LoginHandler;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jakob
 * All webpages under the "secured" folder are redirected to the loginfilter that checks if the user is logged in or not. If the quires are HttpSerlvet requests the
 * method extracts the urlparameter and attaches it to the url redirect to the next page. If we have not done this the URL parameter would have dissapared. 
 * 
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        LoginHandler loginBean = (LoginHandler) ((HttpServletRequest) request).getSession().getAttribute("loginHandler");
        String queryString = "undefined";
        
        //Logic to find out where the user wants to go after being redirected to login. 
        if (request instanceof HttpServletRequest) {
            queryString = ((HttpServletRequest) request).getQueryString();
        }
     
        if (loginBean == null || !loginBean.isLoggedIn()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
             
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml?" + queryString);
        } else {

            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    } 

}