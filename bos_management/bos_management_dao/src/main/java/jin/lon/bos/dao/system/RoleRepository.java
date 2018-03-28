package jin.lon.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import jin.lon.bos.bean.system.Role;

/**
 * ClassName:RoleRepository <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午8:48:43 <br/>
 * Author: 郑云龙
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
