package jin.lon.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 上午9:10:52 <br/>
 * Author:   郑云龙 
 */
public interface StandardService {
     void save(Standard standard);

     Page<Standard> findByPage(Pageable pageable);

   

    
}
  
