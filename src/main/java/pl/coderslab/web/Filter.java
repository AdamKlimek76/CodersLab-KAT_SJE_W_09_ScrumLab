package pl.coderslab.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/app")
public class Filter implements javax.servlet.Filter {
    private  String charsetEncoding="utf-8";
    private  String contentType="";




    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
        request.setCharacterEncoding(charsetEncoding);
        response.setContentType(contentType);
        response.setCharacterEncoding(charsetEncoding);
        chain.doFilter(request,response);
    }
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
