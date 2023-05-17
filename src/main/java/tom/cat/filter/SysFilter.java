package tom.cat.filter;

import tom.cat.pojo.User;
import tom.cat.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shkstart
 * @create 2023-05-02 22:01
 */
public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        if (user == null){
            response.sendRedirect("/smbms/syserror.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    public void destroy() {

    }
}
