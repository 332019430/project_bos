package jin.lon.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:49:55 <br/>
 * Author:   郑云龙 
 */

public interface MenuService {

    

    List<Menu> findByPidIsNull();

    void save(Menu model);

    Page<Menu> findAll(Pageable pageable);

    List<Menu> findbyUid(Long id);
    
}
  
