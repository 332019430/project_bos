package jin.lon.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.system.Permission;
import jin.lon.bos.dao.system.PermissionRepository;
import jin.lon.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:23:40 <br/>
 * Author:   郑云龙 
 */
@Service
@Transactional
public class PermissionServiceImpl  implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
          
        return permissionRepository.findAll(pageable);
    }
    @Override
    public void save(Permission model) {
          
        permissionRepository.save(model);        
    }

    

}
  
