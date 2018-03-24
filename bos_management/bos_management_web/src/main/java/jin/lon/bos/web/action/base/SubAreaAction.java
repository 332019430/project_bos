package jin.lon.bos.web.action.base;

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

import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.SubArea;
import jin.lon.bos.service.base.SubAreaService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**
 * ClassName:SubAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月17日 下午10:40:17 <br/>
 * Author: 郑云龙
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class SubAreaAction extends CommonAction<SubArea> {

    public SubAreaAction() {
        super(SubArea.class);
    }

    @Autowired
    private SubAreaService subAreaService;

    @Action(value = "subareaAction_save", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {
        System.out.println(getModel());
        subAreaService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value = "sub_area_pageQuery")
    public String sub_areaFindAll() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page=subAreaService.pageQuery(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        page2json(page, jsonConfig);
        
        
        return NONE;
    }
    
    @Action("fixedAreaAction_findSub_AraesUnAssociated")
    public String fixedAreaAction_findSub_AraesUnAssociated() throws IOException{
        List<SubArea> list=subAreaService.findFixedAreaIsNull();
        JsonConfig jsonConfig = new JsonConfig();
        
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    @Action("fixedAreaAction_findSub_AraesAssociated")
    public String fixedAreaAction_findSub_AraesAssociated() throws IOException{
        List<SubArea> list=subAreaService.findByFixedArea(model.getId());
        JsonConfig jsonConfig = new JsonConfig();
        
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    private Long[] sub_Arae2Ids;
    public void setSub_Arae2Ids(Long[] sub_Arae2Ids) {
        this.sub_Arae2Ids = sub_Arae2Ids;
    }
    @Action(value="fixedAreaAction_assignsub_Arae2s2FixedArea",
            results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String fixedAreaAction_assignsub_Arae2s2FixedArea(){
        if (sub_Arae2Ids!=null && sub_Arae2Ids.length>0) {
            subAreaService.sub_Arae2s2FixedArea(model.getId(),sub_Arae2Ids);
        }else{
            subAreaService.sub_AreaSetFixedAreaNull(model.getId());
        }
        return SUCCESS;
    }

}
