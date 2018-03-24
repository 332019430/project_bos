package portal;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bore.bean.Customer;

/**
 * ClassName:AaveTest <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午4:42:47 <br/>
 * Author: 郑云龙
 */

public class SaveTest {
    @Test
    public void test() {
        Customer model = new Customer();
        model.setTelephone("18924401817");
        WebClient.create("http://localhost:8282/crm/webService/customerService/save")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(model);
        
    }
}
