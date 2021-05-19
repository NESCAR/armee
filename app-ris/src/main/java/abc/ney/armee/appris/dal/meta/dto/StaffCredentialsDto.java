package abc.ney.armee.appris.dal.meta.dto;

import abc.ney.armee.appris.dal.meta.po.Staff;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
/**
 * 职工查询信息
 */
@Setter
@Getter
@ToString
public class StaffCredentialsDto implements Serializable {
    private static final long serialVersionUID = 1003738692285975338L;
    /**
     * staff 的gid
     */
    private Long staffGid;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 工号
     */
    private String no;
    /**
     * IC卡，如果有的话
     */
    private String icCode;
    /**
     * 职位
     */
    private String position;
    /**
     * 区号如+86
     */
    private String telArea;
    /**
     * 电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 权限
     * @see abc.ney.armee.appris.dal.meta.po.AuthorityRole
     */
    private String authority;
    /**
     * 转化为Staff
     * @return Staff
     */
    public Staff toStaff() {
        Staff staff = new Staff();
        staff.setTel(this.getTel());staff.setTelArea(this.getTelArea());
        staff.setRealName(this.getRealName());staff.setIcCode(this.getIcCode());
        staff.setPosition(this.getPosition());staff.setNo(this.getNo());
        staff.setGid(this.getStaffGid());staff.setEmail(this.getEmail());
        System.out.println(staff.toString());
        return staff;
    }
}
