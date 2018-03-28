package jin.lon.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jin.lon.bos.bean.system.User;
import jin.lon.bos.dao.system.UserRepository;

/**
 * ClassName:UserRealm <br/>
 * Function: <br/>
 * Date: 2018年3月26日 下午4:57:48 <br/>
 * Author: 郑云龙
 */
@Component                      //实现授权借口--目的是连接到权限管理的库，框架会认证当事人和授权给当事人
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;

    // 授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("courier_pageQuery");
        info.addStringPermission("deleteCourier");
        info.addRole("1");
        return info;
    }

    // 认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 根据用户名找用户
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 找到用户比对密码
            /*
             * @param principal “用户”
             * 
             * @param credentials “密码”--从数据库中查询出来的密码
             * 
             * @param realmName the realm from where the principal and credentials were acquired.
             */
            AuthenticationInfo info =
                    new SimpleAuthenticationInfo(user, user.getPassword(), getName());

            return info;

            // 比对成功继续执行
        }

        return null;
    }

}
