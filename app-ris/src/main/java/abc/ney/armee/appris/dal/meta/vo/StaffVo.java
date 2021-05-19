package abc.ney.armee.appris.dal.meta.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 员工信息VO
 * 存活在业务层的数据分层
 * 主要对应页面显示的数据对象
 * vo value object
 * @author neyzoter
 */
@Setter
@Getter
@ToString
public class StaffVo {
    /**
     * 凭证id
     */
    private Long credentialId;
    /**
     * 凭证是否使能
     */
    private Boolean credentialEnable;
    /**
     * 凭证用户名（用于登陆）
     */
    private String credentialName;
    /**
     * 员工id
     */
    private Long staffGid;
    /**
     * 员工真实姓名
     */
    private String staffRealName;
    /**
     * 员工工号
     */
    private String staffNo;
    /**
     * 员工职位
     */
    private String staffPosition;
    /**
     * 员工手机号区号
     */
    private String staffTelArea;
    /**
     * 员工手机号
     */
    private String staffTel;
    /**
     * 员工邮箱
     */
    private String staffEmail;
}
