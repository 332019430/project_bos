package jin.lon.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.take_delivery.WayBill;
import jin.lon.bos.dao.take_delivery.WayBillRepository;
import jin.lon.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:50:50 <br/>
 * Author:   郑云龙 
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WayBillRepository wayBillRepository;

    @Override
    public void save(WayBill model) {
          
        wayBillRepository.save(model);        
    }
    
}
  
