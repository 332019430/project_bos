package jin.lon.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import jin.lon.bos.bean.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午5:06:57 <br/>
 * Author:   郑云龙 
 */
public interface UserRepository  extends JpaRepository<User, Long>{
    User findByUsername(String name);
}
  
