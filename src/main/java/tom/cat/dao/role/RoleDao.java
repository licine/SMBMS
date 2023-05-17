package tom.cat.dao.role;

import tom.cat.pojo.Role;

import java.sql.Connection;
import java.util.List;

/**
 * @author Administrator
 * @Auther: wuxy
 * @Date: 2021/1/28 - 01 - 28 - 20:43
 * @Description: com.wxy.dao.role
 * @version: 1.0
 */
public interface RoleDao {
    public List<Role> getRoleList(Connection connection)throws Exception;
}
