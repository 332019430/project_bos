package jin.lon.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:52:22 <br/>
 * Author:   郑云龙 
 */

public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);

    void fixedAreaAction_associationCourierToFixedAreaAddTime(Long id, Long courierId,
            Long takeTimeId);

}
  
