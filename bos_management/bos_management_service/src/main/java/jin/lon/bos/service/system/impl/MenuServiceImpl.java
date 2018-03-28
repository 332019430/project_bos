package jin.lon.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.system.Menu;
import jin.lon.bos.dao.system.MenuRepository;
import jin.lon.bos.service.system.MenuService;

/**
 * ClassName:MenuServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午4:57:09 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findByPidIsNull() {

        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
        Menu parentMenu = model.getParentMenu();
        if (parentMenu!=null && parentMenu.getId()==null) {
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
        return menuRepository.findAll(pageable);
    }
    
    
}
