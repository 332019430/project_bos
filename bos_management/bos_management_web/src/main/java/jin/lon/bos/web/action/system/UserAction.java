package jin.lon.bos.web.action.system;

import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import jin.lon.bos.bean.system.Permission;
import jin.lon.bos.bean.system.User;
import jin.lon.bos.service.system.UserService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

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

    @Autowired
    private UserService userService;

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
            // “用户”登录 -- 需要接收一个认证令牌

            AuthenticationToken token =
                    new UsernamePasswordToken(model.getUsername(), model.getPassword());

            try {
                subject.login(token);
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

    @Action(value = "userAction_logout",
            results = {@Result(name = "success", location = "/login.html", type = "redirect")})
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ServletActionContext.getRequest().getSession().removeAttribute("validateCode");

        return SUCCESS;
    }

    @Action(value = "userAction_pageQuery")
    public String userAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<User> page = userService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        page2json(page, jsonConfig);
        return NONE;
    }
    
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    @Action(value = "userAction_save", results = {
            @Result(name = "success", location = "/pages/system/userlist.html", type = "redirect")})
    public String save() {
        userService.save(getModel(), roleIds);
        return SUCCESS;
    }

}
