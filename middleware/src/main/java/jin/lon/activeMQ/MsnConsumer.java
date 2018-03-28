package jin.lon.activeMQ;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import jin.lon.utils.MSNUtils;

/**
 * ClassName:MSNConsumer <br/>
 * Function: <br/>
 * Date: 2018年3月25日 下午5:41:29 <br/>
 * Author: 郑云龙
 */
@Component
//实现消费者借口，生成对心消费者
public class MsnConsumer implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            MapMessage mapMessage = (MapMessage) msg;
            String tel = mapMessage.getString("tel");
            String number = mapMessage.getString("number");
            System.out.println(tel+"====="+number);
            // 发送短信
            MSNUtils.sendSms(tel, number);
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
