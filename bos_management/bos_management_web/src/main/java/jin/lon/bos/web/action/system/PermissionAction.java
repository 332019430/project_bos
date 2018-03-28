package jin.lon.bos.web.action.system;

import java.io.IOException;
import java.util.List;

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
import jin.lon.bos.bean.system.Permission;
import jin.lon.bos.bean.system.Role;
import jin.lon.bos.service.system.PermissionService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:18:41 <br/>
 * Author:   郑云龙 
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission>{

    public PermissionAction() {
        super(Permission.class);  
    }
    @Autowired
    private PermissionService permissionService;
    
    @Action(value = "permissionAction_pageQuery")
    public String permissionAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu"});
        page2json(page, jsonConfig);
        return NONE;
    }
    
    @Action(value = "permissionAction_save",
            results = {
                    @Result(name = "success", location = "/pages/system/menu.html",
                            type = "redirect")})
    public String permissionAction_save()  {
        permissionService.save(model);
        return SUCCESS;
    }
    
    @Action(value = "permissionAction_findAll")
    public String permissionAction_findAll() throws IOException {
        
        Page<Permission> page = permissionService.findAll(null);
        List<Permission> list = page.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        list2json(list, jsonConfig);
        return NONE;
    }
}
  
