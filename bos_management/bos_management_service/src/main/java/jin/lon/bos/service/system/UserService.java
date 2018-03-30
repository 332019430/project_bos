package jin.lon.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:57:28 <br/>
 * Author:   郑云龙 
 */
public interface UserService {

    Page<User> findAll(Pageable pageable);

    void save(User model, Long[] roleIds);

}
  
