package jin.lon.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.Courier;
import jin.lon.bos.dao.base.CourierDao;
import jin.lon.bos.service.base.CourierService;

/**
 * ClassName:CourierServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午6:51:24 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierDao dao;

    @Override
    public void save(Courier model) {
        dao.save(model);
    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }
    
    
    @RequiresPermissions("deleteCourier")
    @Override
    public void delete(String id) {
        if (StringUtils.isNotEmpty(id)) {
            String[] split = id.split(",");
            for (String ids : split) {
                dao.updateDeltagByID(Long.parseLong(ids));
            }
        }
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
          
        return dao.findAll(specification, pageable);
    }

    @Override
    public List<Courier> findByDeltagIsNotNull() {
          
        return dao.findByDeltagIsNull();
    }
    
    
}
