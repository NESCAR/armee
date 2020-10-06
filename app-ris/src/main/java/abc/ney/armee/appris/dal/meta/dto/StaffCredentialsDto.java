package abc.ney.armee.appris.dal.meta.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class StaffCredentialsDto implements Serializable {
    private static final long serialVersionUID = 1003738692285975338L;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 工号
     */
    private String no;
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
}
