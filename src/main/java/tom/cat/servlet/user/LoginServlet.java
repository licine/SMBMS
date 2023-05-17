package tom.cat.servlet.user;

import tom.cat.pojo.User;
import tom.cat.service.user.UserService;
import tom.cat.service.user.UserServiceImpl;
import tom.cat.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shkstart
 * @create 2023-05-02 11:35
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("LoginServlet---start---");

        // 获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 和数据库中的密码进行对比， 调用业务层
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user!=null){
            // 查有此人，可以登录，将用户信息保存在Session中
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            // 查无此人，无法登录，转发回登录业，提醒用户名或密码错误
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
