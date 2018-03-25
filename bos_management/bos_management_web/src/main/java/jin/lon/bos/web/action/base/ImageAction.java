package jin.lon.bos.web.action.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午8:01:33 <br/>
 * Author:   郑云龙 
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport {

    private static final long serialVersionUID = 2640985690637555354L;

    // 属性驱动获取文件
    private File imgFile;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    // 属性驱动获取文件名
    private String imgFileFileName;

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    @Action(value = "imageAction_upload")
    public String save() throws IOException {
        // 用于封装结果JSON的集合
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取保存文件的文件夹的绝对路径
            String saveDirPath = "upload";
            String saveDirRealPath = ServletActionContext.getServletContext()
                    .getRealPath(saveDirPath);

            // 获取后缀名
            String suffix =
                    imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            // 生成随机文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase() + suffix;
            // 保存文件
            FileUtils.copyFile(imgFile,
                    new File(saveDirRealPath + "/" + fileName));

            // 获取本项目的路径
            String contextPath =
                    ServletActionContext.getServletContext().getContextPath();
            // 返回的路径格式 : /bos_management_web/upload/xxx.jpg
            String relativePath =
                    contextPath + "/" + saveDirPath + "/" + fileName;

            map.put("error", 0);
            map.put("url", relativePath);
        } catch (Exception e) {

            e.printStackTrace();
            map.put("error", 1);
            map.put("message", e.getMessage());

        }

        // 向客户端写回数据
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        return NONE;
    }

    // 属性驱动获取文件类型
    private String imgFileContentType;

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

}

  
