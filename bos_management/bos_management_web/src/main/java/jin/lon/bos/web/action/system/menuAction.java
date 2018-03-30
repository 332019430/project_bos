package jin.lon.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
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

import jin.lon.bos.bean.system.Menu;
import jin.lon.bos.bean.system.User;
import jin.lon.bos.service.system.MenuService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

/**
 * ClassName:menuAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午4:20:59 <br/>
 * Author: 郑云龙
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class menuAction extends CommonAction<Menu> {

    public menuAction() {
        super(Menu.class);
    }

    @Autowired
    private MenuService menuService;

    @Action(value = "menuAction_findAll")
    public String logout() throws IOException {
        List<Menu> list = menuService.findByPidIsNull();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu"});

        list2json(list, jsonConfig);
        return NONE;
    }

    @Action(value = "menuAction_save",
            results = {
                    @Result(name = "success", location = "/pages/system/menu.html",
                            type = "redirect"),
                    @Result(name = "login", location = "/login.html", type = "redirect")})
    public String save() throws IOException {

        menuService.save(model);

        return SUCCESS;
    }

    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value = "menuAction_pageQuery")
    public String menuAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(Integer.parseInt(model.getPage()) - 1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu"});
        page2json(page, jsonConfig);
        return NONE;
    }

    @Action(value = "menuAction_findbyUser")
    public String menuAction_findbyUser() throws IOException {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> list = menuService.findbyUid(user.getId());
      
        JsonConfig jsonConfig =new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles", "childrenMenus", "parentMenu","children"});
        list2json(list, jsonConfig );
        return NONE;
    }
}
