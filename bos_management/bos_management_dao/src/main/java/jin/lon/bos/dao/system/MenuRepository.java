package jin.lon.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jin.lon.bos.bean.system.Menu;

/**  
 * ClassName:MenuReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:48:00 <br/>
 * Author:   郑云龙 
 */
public interface MenuRepository extends JpaRepository<Menu, Long>{


    List<Menu> findByParentMenuIsNull();

}
  
