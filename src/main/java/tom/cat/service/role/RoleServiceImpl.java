package tom.cat.service.role;

import org.junit.Test;
import tom.cat.dao.BaseDao;
import tom.cat.dao.role.RoleDao;
import tom.cat.dao.role.RoleDaoImpl;
import tom.cat.pojo.Role;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @Auther: wuxy
 * @Date: 2021/1/28 - 01 - 28 - 20:46
 * @Description: com.wxy.service.role
 * @version: 1.0
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }

    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }
    
    @Test
    public void test(){
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        System.out.println(Arrays.toString(roleList.toArray()));
    }
}