package jin.lon.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jin.lon.bos.bean.base.Courier;

/**  
 * ClassName:CourierDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午6:53:42 <br/>
 * Author:   郑云龙 
 */
public interface CourierDao extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier>{
    @Modifying
    @Query("update Courier set deltag = 1 where id = ?")
    void updateDeltagByID(Long id);

    List<Courier> findByDeltagIsNull();
    
    
    
}
  
