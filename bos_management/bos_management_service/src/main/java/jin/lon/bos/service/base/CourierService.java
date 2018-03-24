package jin.lon.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jin.lon.bos.bean.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午6:50:38 <br/>
 * Author:   郑云龙 
 */
public interface CourierService {

    void save(Courier model);

    Page<Courier> findAll(Pageable pageable);

    void delete(String id);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

    List<Courier> findByDeltagIsNotNull();
    
    
}
  
