package jin.lon.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.bos.bean.base.Standard;

/**
 * ClassName:StandardReposity <br/>
 * Function: <br/>
 * Date: 2018年3月12日 下午5:58:11 <br/>
 * Author: 郑云龙
 */
@Component
public interface StandardReposity extends JpaRepository<Standard, Long> {
    @Modifying
    @Transactional
    @Query("delete from Standard where name = ?")
    void deleteByName(String string);
}
