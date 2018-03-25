package jin.lon.bos.web.action.take_delivery;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.take_delivery.WayBill;
import jin.lon.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:52:51 <br/>
 * Author:   郑云龙 
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class WayBillAction extends ActionSupport implements ModelDriven<WayBill>{
    
    private WayBill model=new WayBill();
    
    @Autowired
    private WayBillService wayBillService;
    
    @Override
    public WayBill getModel() {
          
        return model;
    }
    
    @Action(value="waybillAction_save")
    public String waybillAction_save() throws IOException{
        String str="1";
        try {
            //int i=10/0;
            wayBillService.save(model);
        } catch (Exception e) {
            str="0";
            e.printStackTrace();  
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(str);
        return NONE;
    }
}
  
