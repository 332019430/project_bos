package jin.lon.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Area;
import jin.lon.bos.service.base.AreaService;
import jin.lon.bos.web.action.CommonAction;
import jin.lon.utils.PinYin4jUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午8:07:07 <br/>
 * Author: 郑云龙
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

    public AreaAction() {
          
        super(Area.class);  
        // TODO Auto-generated constructor stub  
        
    }

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Autowired
    private AreaService areaService;

    @Action("areaAction_importXLS")
    public String importXLS() throws Exception {

        List<Area> list = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            // 截掉最后一个字符
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            // 获取城市编码和简码
            String citycode =
                    PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
            String[] headByString = PinYin4jUtils
                    .getHeadByString(province + city + district);
            String shortcode =
                    PinYin4jUtils.stringArrayToString(headByString);
            // 封装数据
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            area.setPostcode(postcode);
            area.setCitycode(citycode);
            area.setShortcode(shortcode);

            list.add(area);
        }

        areaService.save(list);
        workbook.close();
        return NONE;
    }

    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageRequest = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.findAll(pageRequest);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});

        page2json(page, jsonConfig);
        return NONE;
    }

    private String q;

    public void setQ(String q) {
        this.q = q;
    }

    @Action("area_findAll")
    public String findAll() throws IOException {
        List<Area> list;
        if (StringUtils.isNotEmpty(q)) {
            list = areaService.findByQ(q);
        } else {
            Page<Area> page = areaService.findAll(null);
            list = page.getContent();
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        list2json(list, jsonConfig);
        return NONE;
    }

}
