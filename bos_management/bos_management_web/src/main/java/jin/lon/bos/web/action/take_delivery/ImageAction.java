package jin.lon.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import jin.lon.utils.UUIDUtile;
import net.sf.json.JSONObject;

/**
 * ClassName:ImageAction <br/>
 * Function: <br/>
 * Date: 2018年3月26日 上午8:19:57 <br/>
 * Author: 郑云龙
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class ImageAction extends ActionSupport {
    private File imgFile;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    private String imgFileFileName;

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    @Action("imageAction_upload")
    public String imageAction_upload() throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            String uploadPath = "/upload";
            ServletContext servletContext = ServletActionContext.getServletContext();
            String realPath = servletContext.getRealPath(uploadPath);

            String sufixx = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            String destName = UUIDUtile.UUIDD();
            destName = destName + sufixx;
            File destFile = new File(realPath + "/" + destName);

            FileUtils.copyFile(imgFile, destFile);

            String contextPath = servletContext.getContextPath();
            map.put("error", 0);
            map.put("url", contextPath + "/upload/" + destName);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", e.getMessage());
        }

        String json = JSONObject.fromObject(map).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
