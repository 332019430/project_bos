package jin.lon.crm.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jin.lon.crm.bean.Customer;

/**
 * ClassName:CustomerDao <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:03:52 <br/>
 * Author: 郑云龙
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {
    List<Customer> findByfixedAreaIdIsNull();

    List<Customer> findByfixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = ? where id=? ")
    void bingCustomerByFixedArae(String fixedAreaId, Long customerIds);

    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId=?")
    void unbindCustomerByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set type =" + 1 + " where telephone=?")
    void activeByTelephone(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

    @Query("select fixedAreaId from Customer where address=?")
    String findFixedAreaIdBySendAddress(String address);

}
