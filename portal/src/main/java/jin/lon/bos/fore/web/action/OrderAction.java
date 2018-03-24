package jin.lon.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Area;
import jin.lon.bos.bean.take_delivery.Order;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午12:10:42 <br/>
 * Author: 郑云龙
 */
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
@Controller
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model = new Order();

    @Override
    public Order getModel() {
        return model;
    }

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setrecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "orderAction_add",
            results = {@Result(name = "success", location = "/index.html", type = "redirect")})
    public String orderAction_add() {
        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            //把接受到的省市区分开
            String[] split = sendAreaInfo.split("/");
            String province = split[0];
            String city = split[1];
            String district = split[2];
            //封装一个包含省市区的区域
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setSendArea(area);
        }
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            //把接受到的省市区分开
            String[] split = recAreaInfo.split("/");
            String province = split[0];
            String city = split[1];
            String district = split[2];
            //封装一个包含省市区的区域
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setRecArea(area);
        }
        
        //把订单传送到订单service
        WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .post(model);
        return SUCCESS;
    }
}
