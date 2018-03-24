package jin.lon.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.dao.base.StandardDao;
import jin.lon.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 上午9:06:39 <br/>
 * Author:   郑云龙 
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService{
    @Autowired
    private StandardDao standardDao;
    
    public void save(Standard standard){
        standardDao.save(standard);
    }

    @Override
    public Page<Standard> findByPage(Pageable pageable) {
        return standardDao.findAll(pageable);
    }

    
    
    
    
}
  
