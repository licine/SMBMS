package tom.cat.dao.user;

import tom.cat.pojo.Role;
import tom.cat.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-05-01 22:24
 */
public interface UserDao {

    // 得到要登录的用户
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws Exception;


    // 修改当前用户密码
    public int updatePwd(Connection connection, int id, String password) throws Exception;

    // 查询用户总数
    public int getUserCount(Connection connection, String username, int userRole) throws Exception;

    // 查询用户列表
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;




}
