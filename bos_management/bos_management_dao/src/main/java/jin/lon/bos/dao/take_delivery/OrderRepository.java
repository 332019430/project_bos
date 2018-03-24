package jin.lon.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import jin.lon.bos.bean.take_delivery.Order;

/**  
 * ClassName:OrderRepositiry <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:19:49 <br/>
 * Author:   郑云龙 
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}
  
