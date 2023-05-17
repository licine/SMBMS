package tom.cat.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import jdk.nashorn.internal.ir.RuntimeNode;
import tom.cat.pojo.Role;
import tom.cat.pojo.User;
import tom.cat.service.role.RoleServiceImpl;
import tom.cat.service.user.UserService;
import tom.cat.service.user.UserServiceImpl;
import tom.cat.util.Constants;
import tom.cat.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2023-05-03 0:11
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if (method.equals("savepwd")&&method!=null){
            this.updatePwd(req, resp);
        }else if (method.equals("pwdmodify")&&method!=null){
            this.pwdModify(req, resp);
        }else if (method.equals("query")&&method!=null){
            this.query(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    // 修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        // 从Session中拿id;
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        boolean flag = false;
        if (o!=null && !StringUtils.isNullOrEmpty(newpassword)){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag){
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                // 密码修改成功移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("message", "密码修改失败");
            }
        }else {
            req.setAttribute("message", "新密码有问题");
        }

        try {
            req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 验证旧密码，从session中拿旧密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){

        //从session中拿到用户信息
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        //Map存储结果集
        Map<String, String> resultMap = new HashMap<String, String>();
        if (o == null){ // session失效，或过期了
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){  // 输入密码为空
            resultMap.put("result", "error");
        }else {
            String userPassword = ((User)o).getUserPassword();
            if (oldpassword.equals(userPassword)){
                resultMap.put("result", "true");
            }else {
                resultMap.put("result", "false");
            }
        }

        try {
            // 返回应用的类型为json
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            // JSONArray 阿里巴巴 JSON 工具类，转换格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 查询用户列表
    public void query(HttpServletRequest req, HttpServletResponse resp){

        // 从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;
        List<User> userList = null;

        // 获取用户列表
        UserServiceImpl userService = new UserServiceImpl();

        int pageSize = 5;
        int currentPageNo = 1;


        if (queryUserName == null){
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex!= null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 获取用户总数（分页： 上一页， 下一页的情况）
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        // 总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();

        // 控制首页尾页
        if (currentPageNo < 1){
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        // 获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList", userList);

        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}


