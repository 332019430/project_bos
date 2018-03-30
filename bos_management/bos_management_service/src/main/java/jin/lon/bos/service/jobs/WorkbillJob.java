package jin.lon.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jin.lon.bos.bean.take_delivery.WorkBill;
import jin.lon.bos.dao.base.WorkbillRepository;
import jin.lon.utils.SendMailUtils;

/**
 * ClassName:WorkbillJob <br/>
 * Function: <br/>
 * Date: 2018年3月29日 下午8:19:05 <br/>
 * Author: 郑云龙
 */
@Component
public class WorkbillJob {

    @Autowired
    private WorkbillRepository workbillRepository;

    public void sendMail() {
        /*List<WorkBill> list = workbillRepository.findAll();

        String emailBody = "编号\t取件状态\t快递员\t时间<br/>";
        for (WorkBill workBill : list) {
            emailBody += workBill.getId() + "\t" 
                    + workBill.getPickstate() + "\t"
                    + workBill.getCourier().getName() + "\t"
                    + workBill.getBuildtime().toLocaleString() + "<br/>";
        }
        SendMailUtils.sendMail("工单信息", emailBody, null);*/
        System.out.println("发送邮件成功");
    }
}
