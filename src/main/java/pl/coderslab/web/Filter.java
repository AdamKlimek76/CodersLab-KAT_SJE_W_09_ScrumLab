package pl.coderslab.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class Filter implements javax.servlet.Filter {

   private  String charsetEncoding="utf-8";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1= (HttpServletRequest) request;
        HttpServletResponse response1=(HttpServletResponse) response;
        HttpSession session= request1.getSession();
        if(session.getAttribute("admin")==null){
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath()+"/login");
        }
        else {
            request.setCharacterEncoding(charsetEncoding);
            String contentType = "text/html";
            response.setContentType(contentType);
            response.setCharacterEncoding(charsetEncoding);
            chain.doFilter(request, response);
        }


    }
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
