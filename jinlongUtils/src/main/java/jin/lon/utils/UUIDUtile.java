package jin.lon.utils;

import java.util.UUID;

/**  
 * ClassName:UUIDUtile <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:35:19 <br/>
 * Author:   郑云龙 
 */
public class UUIDUtile {
    public static String UUIDD(){
       return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
  
