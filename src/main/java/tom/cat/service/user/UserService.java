package tom.cat.service.user;

import tom.cat.pojo.User;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-05-01 23:03
 */
public interface UserService {

    // 用户登录
    public User login(String userCode, String password);

    // 修改密码
    public boolean updatePwd(int id, String pwd);

    // 查询记录数
    public int getUserCount(String username, int userRole);

    // 查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);


}
