package jin.lon.bos.web.action.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jin.lon.bos.bean.system.User;
import jin.lon.bos.web.action.CommonAction;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2018年3月26日 下午4:41:20 <br/>
 * Author: 郑云龙
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    public UserAction() {

        super(User.class);

    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "userAction_login",
            results = {@Result(name = "success", location = "/index.html", type = "redirect"),
                    @Result(name = "login", location = "/login.html", type = "redirect")})
    public String login() {
        String checkCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("validateCode");
        if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(checkCode)
                && checkCode.equals(checkcode)) {
            // 从安全工具类获取“用户”
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token =
                    new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
            // “用户”登录 -- 需要接收一个认证令牌
            
            
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                System.out.println("用户名写错了");
                e.printStackTrace();
            } catch (IncorrectCredentialsException e) {
                System.out.println("密码错误");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("其他错误");
                e.printStackTrace();
            }
        }

        return LOGIN;
    }
}
