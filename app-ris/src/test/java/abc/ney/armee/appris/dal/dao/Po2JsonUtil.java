package abc.ney.armee.appris.dal.dao;

import abc.ney.armee.appris.dal.meta.po.Authority;
import abc.ney.armee.appris.dal.meta.po.Staff;

/**
 * po类转Json
 * @author neyzoter
 */
public class Po2JsonUtil {
    public static void main(String[] args) {
        Staff staff = new Staff();
        staff.setEmail("sonechaochao@gmail.com");
        staff.setNo("abc");
        staff.setPosition("管理员");
        staff.setRealName("scc");
        staff.setTel("15655559089");
        Po2JsonUtil.printPo(staff);
    }
    public static void printPo(Object o) {
        System.out.println(o.toString());
    }
}
