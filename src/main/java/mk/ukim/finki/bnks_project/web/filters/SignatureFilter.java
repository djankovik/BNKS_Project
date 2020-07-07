package mk.ukim.finki.bnks_project.web.filters;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

//temporarily turned off filtering
//@Component
@Order(1)
public class SignatureFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();

        for(String header: Collections.list(httpRequest.getHeaderNames())){
            System.out.println("\t"+header+" : "+httpRequest.getHeader(header));
        }

        String path = httpRequest.getServletPath();
        if(path.contains("signedendpoint")){
            if(httpRequest.getHeader("authorization") == null && httpRequest.getHeader("signature") == null)
                httpResp.addHeader("WWW-Authenticate","Signature realm=\"Example\",headers=\"(request-target) date");
                httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No signature nor authorisation header");
        } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
    }

    @Override
    public void destroy() {

    }
}
