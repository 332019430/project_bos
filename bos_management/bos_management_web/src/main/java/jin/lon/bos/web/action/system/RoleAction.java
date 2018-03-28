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

import jin.lon.bos.bean.system.Role;
import jin.lon.bos.service.system.RoleService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:45:10 <br/>
 * Author:   郑云龙 
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {
        super(Role.class);  
    }
    
    
    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Autowired
    private RoleService roleService;
    
    @Action(value = "roleAction_pageQuery")
    public String menuAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page = roleService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"menus","permissions","users"});
        page2json(page, jsonConfig);
        return NONE;
    }
    
    
    
    
    /*@Action(value = "roleAction_findAll")
    public String roleAction_findAll() throws IOException {
        
        Page<Role> page = roleService.findAll(null);
        List<Role> list = page.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"menus","permissions","users"});
        list2json(list, jsonConfig);
        return NONE;
    }*/
    
    private String menuIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    private long[] permissionIds;
    
    public void setPermissionIds(long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    @Action(value = "roleAction_save",
            results = {
                    @Result(name = "success", location = "/pages/system/role.html",
                            type = "redirect")})
    public String roleAction_save() throws IOException {
        roleService.save(model,permissionIds,menuIds);
        return SUCCESS;
    }
}
  
