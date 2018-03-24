package jin.lon.bos.web.action.base;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.service.base.StandardService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2018年3月12日 下午9:49:14 <br/>
 * Author: 郑云龙
 */

@Namespace("/") // 等价于以前struts.xml中<package>节点的namespace属性
@ParentPackage("struts-default") // 等价于以前struts.xml中<package>节点的extends属性
@Scope("prototype") // 等价于以前applicationContext.xml中<bean>节点的scope属性
@Controller // 代表本类是一个控制器,即web层
public class StandardAction extends CommonAction<Standard> {
    public StandardAction() {
          
        super(Standard.class);  
        // TODO Auto-generated constructor stub  
        
    }

    private static final long serialVersionUID = 2L;
   
    @Autowired
    private StandardService service;
    
    /*private Standard standard = new Standard();
    @Override
    public Standard getModel() {
        return standard;
    }*/

    // 保存派送标准
    // Action中的value等价于以前struts.xml中<action>节点的name
    // Result中的name等价于以前struts.xml中<result>节点的name
    // Result中的location等价于以前struts.xml中<result>节点之间的内容
    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect")})
    public String save() {
        service.save(model);
        return SUCCESS;
    }

    /*private int page;

    public void setPage(int page) {
        this.page = page;
    }

    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }*/

    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> page=service.findByPage(pageable);
        page2json(page, null);
        return NONE;
        /*long total = page.getTotalElements();
        List<Standard> list = page.getContent();
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        String json = JSONObject.fromObject(map).toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;*/
    }
    
    @Action(value = "standard_findAll")
    public String findAll() throws IOException {
        
        Page<Standard> page=service.findByPage(null);
        List<Standard> list = page.getContent();
        list2json(list, null);
        return NONE;
    }
}
