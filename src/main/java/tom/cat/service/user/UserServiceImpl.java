package tom.cat.service.user;

import com.sun.org.apache.bcel.internal.generic.IfInstruction;
import org.junit.Test;
import tom.cat.dao.BaseDao;
import tom.cat.dao.user.UserDao;
import tom.cat.dao.user.UserDaoImpl;
import tom.cat.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-05-01 23:04
 */
public class UserServiceImpl implements UserService{

    // 业务层都会调用dao层，所以我们要引入Dao层
    private UserDao userDao;

    // 无参构造器
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }


    // 登录
    public User login(String userCode, String password) {

        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        return user;
    }

    // 修改密码
    public boolean updatePwd(int id, String pwd) {
        boolean flag = false;
        Connection connection = null;

        connection = BaseDao.getConnection();
        try {
            if (userDao.updatePwd(connection,id, pwd) > 0){
                flag = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            BaseDao.closeResource(connection, null, null);
        }

        return flag;
    }


    // 查询记录数
    public int getUserCount(String username, int userRole) {

        Connection connection = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, username, userRole);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }

    // 查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
//    @Test
//    public void test(){
//        UserServiceImpl userService = new UserServiceImpl();
//        User admin = userService.login("admin","123");
//        System.out.println(admin.getUserPassword());
//    }

//    @Test
//    public void test(){
//        UserServiceImpl userService = new UserServiceImpl();
//        int userCount = userService.getUserCount(null, 2);
//        System.out.println(userCount);
//    }
}
