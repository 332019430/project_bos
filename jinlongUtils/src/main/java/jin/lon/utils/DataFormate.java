package jin.lon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.crypto.Data;

import org.junit.Test;

/**
 * ClassName:DataFormate <br/>
 * Function: <br/>
 * Date: 2018年3月24日 下午4:31:10 <br/>
 * Author: 郑云龙
 */
public class DataFormate {
    @SuppressWarnings("deprecation")
    @Test
    public void formateDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        //System.out.println(format);
        Date date2 = new Date(format);
        System.out.println(date2+"=====");
    }
}
