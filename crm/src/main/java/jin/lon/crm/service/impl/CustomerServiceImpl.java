package jin.lon.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.crm.bean.Customer;
import jin.lon.crm.dao.CustomerDao;
import jin.lon.crm.service.CustomerService;

/**
 * ClassName:CustomerServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:06:21 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao dao;

    @Override
    public List<Customer> findByfixedAreaIdIsNull() {
        return dao.findByfixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findByFixedAreaId(String fixedAreaId) {
        Customer customer = new Customer();
        customer.setFixedAreaId(fixedAreaId);
        
        
        
        Example<Customer> of = Example.of(customer);
        return dao.findAll(of);
        /*return dao.findByfixedAreaId(fixedAreaId);*/
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {

        dao.unbindCustomerByFixedAreaId(fixedAreaId);

        if (customerIds.length > 0 && fixedAreaId.length() > 0) {
            for (Long long1 : customerIds) {
                dao.bingCustomerByFixedArae(fixedAreaId, long1);
            }
        }
    }

    @Override
    public void assignCustomers2null(String fixedAreaId) {
        dao.unbindCustomerByFixedAreaId(fixedAreaId);

    }

    @Override
    public void save(Customer customer) {

        dao.save(customer);

    }

    @Override
    public void active(String telephone) {
        dao.activeByTelephone(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
        Customer customer = dao.findByTelephoneAndPassword(telephone, password);
        
        return customer;
    }

    @Override
    public String findFixedAreaIdBySendAddress(String address) {
          
        
        return dao.findFixedAreaIdBySendAddress(address);
    }

}
