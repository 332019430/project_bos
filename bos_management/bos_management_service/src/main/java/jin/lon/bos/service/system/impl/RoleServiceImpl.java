package jin.lon.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.system.Menu;
import jin.lon.bos.bean.system.Permission;
import jin.lon.bos.bean.system.Role;
import jin.lon.bos.dao.system.RoleRepository;
import jin.lon.bos.service.system.RoleService;

/**
 * ClassName:RoleServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午8:47:50 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> findAll(Pageable pageable) {

        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role model, long[] permissionIds, String menuIds) {

        roleRepository.save(model);
        
        if (permissionIds!=null&&permissionIds.length>0) {
            for (long permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                model.getPermissions().add(permission);
            }
        }
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuId));
                model.getMenus().add(menu);
            }
        }

    }

}
