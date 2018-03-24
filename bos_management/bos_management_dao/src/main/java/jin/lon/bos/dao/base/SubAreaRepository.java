package jin.lon.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.bean.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午12:18:23 <br/>
 * Author:   郑云龙 
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>{
    List<SubArea> findByFixedAreaIsNull();

    List<SubArea> findByFixedArea(FixedArea fixedArea);
    
    
    
    
}
  
