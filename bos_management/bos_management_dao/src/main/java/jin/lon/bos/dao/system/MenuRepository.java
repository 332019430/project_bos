package jin.lon.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.system.Menu;

/**  
 * ClassName:MenuReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:48:00 <br/>
 * Author:   郑云龙 
 */
public interface MenuRepository extends JpaRepository<Menu, Long>{


    List<Menu> findByParentMenuIsNull();
    
    
    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id=?")
    List<Menu> findbyUid(Long id);

}
  
