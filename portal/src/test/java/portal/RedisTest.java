package portal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:RedisTest <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午4:09:24 <br/>
 * Author: 郑云龙
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForValue().set("name", "郑云龙");
    }
}
