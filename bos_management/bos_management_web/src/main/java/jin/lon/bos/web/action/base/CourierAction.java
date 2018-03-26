package jin.lon.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import jin.lon.bos.bean.base.Courier;
import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.service.base.CourierService;
import jin.lon.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午5:55:26 <br/>
 * Author: 郑云龙
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {

    /*
     * private Courier model;
     * 
     * @Override public Courier getModel() { if (model == null) { model = new Courier(); } return
     * model; }
     */

    public CourierAction() {

        super(Courier.class);
        

    }
    
    @Autowired
    private CourierService courierService;
    

    @Action(value = "courier_save", results = {
            @Result(name = "success", location = "pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(getModel());
        return SUCCESS;
    }

    /*
     * private int page;
     * 
     * public void setPage(int page) { this.page = page; }
     * 
     * private int rows;
     * 
     * public void setRows(int rows) { this.rows = rows; }
     */

    @Action(value = "courier_pageQuery")
    public String pageQuery() throws IOException {
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                if (model == null) {
                    return null;
                }
                final String courierNum = model.getCourierNum();
                final String type = model.getType();
                final String company = model.getCompany();
                final Standard standard = model.getStandard();

                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if (StringUtils.isNotEmpty(type)) {
                    Predicate p2 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p2);
                }
                if (StringUtils.isNotEmpty(company)) {
                    Predicate p3 =
                            cb.like(root.get("company").as(String.class), "%" + company + "%");
                    list.add(p3);
                }
                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                
                return cb.and(arr);
            }

        };

        Pageable pageable = new PageRequest(page - 1, rows);
        // Page<Courier> page = service.findAll(pageable);
        System.out.println(courierService);
        Page<Courier> page = courierService.findAll(specification, pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        page2json(page, jsonConfig);
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }
    
    
    @Action(value = "courier_delete", results = {
            @Result(name = "success", location = "pages/base/courier.html", type = "redirect")})
    public String delete() {

        courierService.delete(ids);
        return SUCCESS;
    }

    @Action(value = "courierAction_listajax")
    public String courierAction_listajax() throws IOException {

        List<Courier> list = courierService.findByDeltagIsNotNull();
        System.out.println("list:" + list);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas"});
        list2json(list, jsonConfig);
        return NONE;
    }

}
