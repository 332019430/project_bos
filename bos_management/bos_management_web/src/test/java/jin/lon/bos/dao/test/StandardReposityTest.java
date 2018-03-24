package jin.lon.bos.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.dao.base.StandardReposity;

/**  
 * ClassName:StandardReposityTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午6:14:11 <br/>
 * Author:   郑云龙 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardReposityTest {
    @Autowired
    private StandardReposity standardReposity;
    
    @Test
    public void test01() {
        Standard standard = new Standard();
        standard.setMinLength(18);
        standard.setName("郑云龙");
        standardReposity.save(standard);
    }
    
    @Test
    public void test02() {
        List<Standard> list = standardReposity.findAll();
        for (Standard standard : list) {
            System.out.println(standard.getId()+"1");
        }
    }
    
    @Test
    public void test03() {
        standardReposity.delete(21L);
        
    }
    
    @Test
    public void test04() {
        standardReposity.deleteByName("郑云龙");
        
    }
}
  
