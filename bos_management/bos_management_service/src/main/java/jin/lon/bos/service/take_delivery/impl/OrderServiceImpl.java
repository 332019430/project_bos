package jin.lon.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.base.Customer;
import jin.lon.bos.bean.base.Area;
import jin.lon.bos.bean.base.Courier;
import jin.lon.bos.bean.base.FixedArea;
import jin.lon.bos.bean.base.SubArea;
import jin.lon.bos.bean.take_delivery.Order;
import jin.lon.bos.bean.take_delivery.WorkBill;
import jin.lon.bos.dao.base.AreaDao;
import jin.lon.bos.dao.base.FixedAreaDao;
import jin.lon.bos.dao.take_delivery.OrderRepository;
import jin.lon.bos.dao.take_delivery.WorkBillRepository;
import jin.lon.bos.service.take_delivery.OrderService;
import jin.lon.utils.UUIDUtile;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午3:29:51 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AreaDao areadao;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FixedAreaDao fixedAreaDao;
    @Autowired
    private WorkBillRepository workBillRepository;

    @Override
    public void saveOrder(Order order) {
        // 获得有省市区的区域--发送
        Area sendArea = order.getSendArea();
        if (sendArea != null) {
            // 通过只有省市区的局域区寻找一个完整的区域
            Area sendAreaComplete = areadao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),
                    sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(sendAreaComplete);
        }
        // 获得有省市区的区域--接收
        Area recArea = order.getRecArea();
        if (recArea != null) {
            // 通过只有省市区的局域区寻找一个完整的区域
            Area recAreaComplete = areadao.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
            order.setSendArea(recAreaComplete);
        }
        // 保存订单
        order.setOrderNum(UUIDUtile.UUIDD());
        order.setOrderTime(new Date());
        orderRepository.save(order);

        // 先从具体地址去分配快递员
        String sendAddress = order.getSendAddress();
        if (StringUtils.isNotEmpty(sendAddress)) {
            String fixedAreaId = WebClient
                    .create("http://localhost:8282/crm/webService/customerService/findFixedAreaIdBySendAddress")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .query("address", sendAddress)
                    .get(String.class);
            if (StringUtils.isNotEmpty(fixedAreaId)) {
                //得到发送定区
                FixedArea sendFixedArea = fixedAreaDao.findOne(Long.parseLong(fixedAreaId));
                if (sendFixedArea != null) {
                    Set<Courier> couriers = sendFixedArea.getCouriers();
                    if (!couriers.isEmpty()) {
                        Courier courier = couriers.iterator().next();
                        // 订单信息
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setSmsNumber("111");
                        workBill.setType("新");

                        workBillRepository.save(workBill);
                        //发送一条信息
                        order.setOrderType("自动分单");
                        return;
                    }
                }
            }
        } else {
            // 订单的完整发送区域
            Area sendArea2 = order.getSendArea();
            if (sendArea2 != null) {
                Set<SubArea> subareas = sendArea2.getSubareas();
                if (subareas != null) {
                    for (SubArea subArea : subareas) {
                        String keyWords = subArea.getKeyWords();
                        String assistKeyWords = subArea.getAssistKeyWords();
                        if (sendAddress.contains(assistKeyWords)
                                || sendAddress.contains(keyWords)) {
                            //得到发送定区
                            FixedArea sendFixedArea = subArea.getFixedArea();
                            if (sendFixedArea!=null) {
                                Set<Courier> couriers = sendFixedArea.getCouriers();
                                if (couriers!=null) {
                                    Courier courier = couriers.iterator().next();
                                    
                                    WorkBill workBill = new WorkBill();
                                    workBill.setAttachbilltimes(0);
                                    workBill.setBuildtime(new Date());
                                    workBill.setCourier(courier);
                                    workBill.setOrder(order);
                                    workBill.setPickstate("新单");
                                    workBill.setRemark(order.getRemark());
                                    workBill.setSmsNumber("111");
                                    workBill.setType("新");

                                    workBillRepository.save(workBill);
                                    //发送一条信息
                                    order.setOrderType("自动分单");
                                    return;
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        order.setOrderType("人工分单");
    }
}
