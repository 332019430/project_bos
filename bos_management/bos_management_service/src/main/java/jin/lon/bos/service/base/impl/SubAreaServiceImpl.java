package jin.lon.bos.service.base.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.SubArea;
import jin.lon.bos.dao.base.FixedAreaDao;
import jin.lon.bos.dao.base.SubAreaRepository;
import jin.lon.bos.service.base.SubAreaService;

/**
 * ClassName:SubAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午12:17:20 <br/>
 * Author: 郑云龙
 */
@Transactional
@Service
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Override
    public void save(SubArea model) {
        subAreaRepository.save(model);
    }

    @Override
    public Page<SubArea> pageQuery(Pageable pageRequest) {

        // TODO Auto-generated method stub
        return subAreaRepository.findAll(pageRequest);
    }

    @Override
    public List<SubArea> findFixedAreaIsNull() {

        
        return subAreaRepository.findByFixedAreaIsNull();
    }

    @Override
    public List<SubArea> findByFixedArea(Long id) {

        FixedArea fixedArea = new FixedArea();
        fixedArea.setId(id);
        return subAreaRepository.findByFixedArea(fixedArea);
    }

    @Override
    public void sub_Arae2s2FixedArea(Long id, Long[] sub_Arae2Ids) {
        
        FixedArea fixedArea = new FixedArea();
        System.out.println("sub_Arae2s2FixedArea:"+id);
        fixedArea.setId(id);
        List<SubArea> subAreas = subAreaRepository.findByFixedArea(fixedArea);
        for (SubArea subArea : subAreas) {
            subArea.setFixedArea(null);
        }
        for (Long long1 : sub_Arae2Ids) {
            SubArea subArea = subAreaRepository.findOne(long1);
            subArea.setFixedArea(fixedArea);
        }
        
        
    }

    @Override
    public void sub_AreaSetFixedAreaNull(Long id) {
        FixedArea fixedArea = new FixedArea();
        fixedArea.setId(id);
        List<SubArea> subAreas = subAreaRepository.findByFixedArea(fixedArea);
        for (SubArea subArea : subAreas) {
            subArea.setFixedArea(null);
        }
        
    }

}
