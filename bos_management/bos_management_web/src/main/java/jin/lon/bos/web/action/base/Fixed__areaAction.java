package jin.lon.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import jin.lon.bos.base.Customer;
import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.service.base.FixedAreaService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**
 * ClassName:Fixed__areaAction <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:49:22 <br/>
 * Author: 郑云龙
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class Fixed__areaAction extends CommonAction<FixedArea> {

    public Fixed__areaAction() {

        super(FixedArea.class);

    }

    @Autowired
    private FixedAreaService service;

    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {

        service.save(getModel());
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_pageQuery")
    public String fixedAreaAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);

        Page<FixedArea> page = service.pageQuery(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});
        page2json(page, jsonConfig);
        return NONE;
    }
    /* ../../fixedAreaAction_findCustomersUnAssociated */
    //找到未绑定客户
    @Action(value = "fixedAreaAction_findCustomersUnAssociated")
    public String fixedAreaAction_findCustomersUnAssociated() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8282/crm/webService/customerService/findByfixedAreaIdIsNull")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }

    //找到绑定客户
    @Action(value = "fixedAreaAction_findCustomersAssociated")
    public String fixedAreaAction_findCustomersAssociated() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8282/crm/webService/customerService/findByFixedAreaId")
                .query("fixedAreaId", model.getId()).type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }

    private Long[] customerIds;

    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String fixedAreaAction_assignCustomers2FixedArea() throws IOException {
        if (customerIds != null && customerIds.length > 0) {
            WebClient
                    .create("http://localhost:8282/crm/webService/customerService/assignCustomers2FixedArea")
                    .query("fixedAreaId", model.getId()).query("customerIds", customerIds)
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(null);
        } else {
            WebClient
                    .create("http://localhost:8282/crm/webService/customerService/assignCustomers2null")
                    .query("fixedAreaId", model.getId()).type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).put(null);
        }

        return SUCCESS;
    }

    private Long courierId;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    private Long takeTimeId;

    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String fixedAreaAction_associationCourierToFixedArea() {
        service.fixedAreaAction_associationCourierToFixedAreaAddTime(getModel().getId(), courierId,
                takeTimeId);
        return SUCCESS;
    }
}
