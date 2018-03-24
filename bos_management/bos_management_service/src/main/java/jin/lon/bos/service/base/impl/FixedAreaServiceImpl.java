package jin.lon.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.Courier;
import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.TakeTime;
import jin.lon.bos.dao.base.CourierDao;
import jin.lon.bos.dao.base.FixedAreaDao;
import jin.lon.bos.dao.base.TakeTimeDao;
import jin.lon.bos.service.base.FixedAreaService;

/**
 * ClassName:FixedAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:56:59 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaDao fixedAreaDaoao;
    @Autowired
    private TakeTimeDao timeDao;
    @Autowired
    private CourierDao courierDao;
    
    @Override
    public void save(FixedArea model) {

        fixedAreaDaoao.save(model);

    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {

        // TODO Auto-generated method stub
        return fixedAreaDaoao.findAll(pageable);
    }

    @Override
    public void fixedAreaAction_associationCourierToFixedAreaAddTime(Long id, Long courierId,
            Long takeTimeId) {
        
        TakeTime time = timeDao.findOne(takeTimeId);
        Courier courier = courierDao.findOne(courierId);
        FixedArea fixedArea = fixedAreaDaoao.findOne(id);
        
        courier.setTakeTime(time);
        fixedArea.getCouriers().add(courier);
    }

}
