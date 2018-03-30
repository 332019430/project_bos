package jin.lon.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.system.Role;


/**
 * ClassName:RoleRepository <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午8:48:43 <br/>
 * Author: 郑云龙
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r From Role r inner join r.users u Where u.id=?")
    List<Role> findbyUid(Long id);

}
