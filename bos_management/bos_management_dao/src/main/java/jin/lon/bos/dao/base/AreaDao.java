package jin.lon.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.base.Area;

/**  
 * ClassName:AreaDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:50:47 <br/>
 * Author:   郑云龙 
 */
public interface AreaDao extends JpaRepository<Area, Long> {
    @Query("from Area where province like ?1 or  city like ?1  or  district like ?1  or  postcode like ?1  or  citycode like ?1  or  shortcode like ?1")
    List<Area> findByQ(String q);

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);

   
    
     
}
