package jin.lon.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:47:09 <br/>
 * Author:   郑云龙 
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void save(Role model, long[] permissionIds, String menuIds);

}
  
