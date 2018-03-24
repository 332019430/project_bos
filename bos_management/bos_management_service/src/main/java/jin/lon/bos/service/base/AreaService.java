package jin.lon.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.base.Area;

/**  
 * ClassName:areaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:47:39 <br/>
 * Author:   郑云龙 
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> findAll(Pageable pageRequest);

    List<Area> findByQ(String q);

}
  
