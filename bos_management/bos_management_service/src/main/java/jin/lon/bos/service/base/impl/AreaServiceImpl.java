package jin.lon.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.Area;
import jin.lon.bos.dao.base.AreaDao;
import jin.lon.bos.service.base.AreaService;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:49:27 <br/>
 * Author:   郑云龙 
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao dao;
    @Override
    public void save(List<Area> list) {

        dao.save(list);

    }
    @Override
    public Page<Area> findAll(Pageable pageRequest) {
          
        
        return dao.findAll(pageRequest);
    }
    @Override
    public List<Area> findByQ(String q) {
          
        
        return dao.findByQ("%"+q.toUpperCase()+"%");
    }

}
  
