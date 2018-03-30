package jin.lon.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:25:36 <br/>
 * Author:   郑云龙 
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select p from Permission p inner join p.roles r join r.users u where u.id=?")
    List<Permission> findbyUid(Long id);

}
  
