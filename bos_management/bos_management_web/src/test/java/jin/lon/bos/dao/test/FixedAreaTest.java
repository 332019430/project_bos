package jin.lon.bos.dao.test;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jin.lon.bos.base.Customer;
import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.dao.base.FixedAreaDao;

/**
 * ClassName:FixedAreaTest <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午10:00:38 <br/>
 * Author: 郑云龙
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FixedAreaTest {
    @Autowired
    private FixedAreaDao dao;

    @Test
    public void testFindAll() {
        List<FixedArea> findAll = dao.findAll();
        for (FixedArea fixedArea : findAll) {
            System.out.println(fixedArea.getId());

        }
    }

    @Test
    public void testFindPage() {
        List<FixedArea> findAll = dao.findAll();
        for (FixedArea fixedArea : findAll) {
            System.out.println(fixedArea.getId());

        }
        Pageable pageable = new PageRequest(0, 30);

    }

    @Test
    public void findAll() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8080/bos_management_web/webService/customerService/findAll")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        System.out.println(list);

    }
}
