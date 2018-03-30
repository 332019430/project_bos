package jin.lon.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.system.Role;
import jin.lon.bos.bean.system.User;
import jin.lon.bos.dao.system.UserRepository;
import jin.lon.bos.service.system.UserService;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午11:00:47 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    @Override
    public void save(User model, Long[] roleIds) {
        userRepository.save(model);
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                model.getRoles().add(role);
            }
        }

    }

}
