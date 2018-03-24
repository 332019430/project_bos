package jin.lon.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import jin.lon.bos.bean.base.Area;
import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.SubArea;
import jin.lon.bos.dao.base.AreaDao;
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
    @Autowired
    private AreaDao areaDao;
    
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value="subAreaAction_importXLS", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String subAreaAction_importXLS() throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        List<SubArea> list=new ArrayList<>();
        for (Row row : sheetAt) {
            if (row.getRowNum()==0) {
                continue;
            }
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            district=district.substring(0, district.length()-1);
            Area area = areaDao.findByProvinceAndCityAndDistrict(province, city, district);
            
            
            String keyWords = row.getCell(4).getStringCellValue();
            String startNum = row.getCell(5).getStringCellValue();
            String endNum = row.getCell(6).getStringCellValue();
            String single = row.getCell(7).getStringCellValue();
            String assistKeyWords = row.getCell(8).getStringCellValue();
            SubArea subArea = new SubArea();
            subArea.setArea(area);
            subArea.setKeyWords(keyWords);
            subArea.setStartNum(startNum);
            subArea.setEndNum(endNum);
            subArea.setSingle(single.charAt(0));
            subArea.setAssistKeyWords(assistKeyWords);
            
            list.add(subArea);
        }
        subAreaService.save(list);
        return SUCCESS;
    }
    
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
