package jin.lon.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bore.bean.Customer;
import jin.lon.utils.MSNUtils;
import jin.lon.utils.Md5Util;
import jin.lon.utils.SendMailUtils;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2018年3月19日 下午5:40:37 <br/>
 * Author: 郑云龙
 */
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
@Controller
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    private Customer model = new Customer();

    @Override
    public Customer getModel() {
        return model;
    }
    @Autowired
    private JmsTemplate jmsTemplate;

    @Action("customer_sendMsn")
    public String sendMsn() {
        final String number = RandomStringUtils.randomNumeric(6);
        System.out.println(number);
        ServletActionContext.getRequest().getSession().setAttribute("number", number);

        jmsTemplate.send("msn", new MessageCreator() {
            
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("tel", model.getTelephone());
                mapMessage.setString("number", number);
                return mapMessage;
            }
        });
        /*try {
            MSNUtils.sendSms(model.getTelephone(), number);
        } catch (ClientException e) {
              
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }*/

        return NONE;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html", type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
    public String customerAction_regist() throws AddressException, MessagingException {

        String attribute =
                (String) ServletActionContext.getRequest().getSession().getAttribute("number");
        model.setPassword(Md5Util.encodePwd(model.getPassword()));
        if (StringUtils.isNotEmpty(attribute) && StringUtils.isNotEmpty(checkcode)
                && attribute.equals(checkcode)) {

            WebClient.create("http://localhost:8282/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .post(model);

            String activeCode = RandomStringUtils.randomNumeric(32);
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);

            String emailMsg =
                    "感谢您注册本网站的帐号，请在24小时之内点击<a href='http://localhost:8181/portal/CustomerAction_active.action?telephone="
                            + model.getTelephone() + "&activeCode=" + activeCode + "'>本链接</a>激活";
            //MailUtils.sendMail(model.getEmail(), "激活邮件", emailMsg);
            SendMailUtils.sendMail("激活邮件", emailMsg, model.getEmail());
            return SUCCESS;
        }
        return ERROR;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Action(value = "CustomerAction_active",
            results = {@Result(name = "success", location = "/login.html", type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
    public String customerAction_active() {

        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
        if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(activeCode)
                && serverCode.equals(activeCode)) {

            WebClient.create("http://localhost:8282/crm/webService/customerService/active")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone()).put(null);
            return SUCCESS;
        }

        return ERROR;
    }

    
    private String renNum;
    public void setRenNum(String renNum) {
        this.renNum = renNum;
    }
    @Action(value = "customerAction_login",
            results = {@Result(name = "success", location = "/index.html", type = "redirect"),
                    @Result(name = "error", location = "/login.html", type = "redirect")})
    public String customerAction_login() {
        String validateCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("validateCode");
        if (StringUtils.isNoneEmpty(checkcode) && StringUtils.isNoneEmpty(validateCode)
                && checkcode.equals(validateCode)) {
            
            String pwd = Md5Util.encodePwd(model.getPassword());
            Customer customer = WebClient.create("http://localhost:8282/crm/webService/customerService/login")
                            .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                            .query("telephone", model.getTelephone())
                            .query("password", pwd).get(Customer.class);
           
            new Cookie("telephone", model.getTelephone(), "/", "");
            if (customer!=null) {
                if (customer.getType()!=null&&customer.getType()==1) {
                    ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
                    return SUCCESS;
                }
                return "unactive";
            }
        }
        return ERROR;
    }
}
