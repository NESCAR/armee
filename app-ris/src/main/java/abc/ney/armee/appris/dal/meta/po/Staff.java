package abc.ney.armee.appris.dal.meta.po;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;

import java.io.Serializable;
import java.util.Date;

public class Staff implements Serializable {
    private Long gid;

    private String realName;

    private String no;

    private String position;

    private String tel;

    private String email;

    private Date gmtCreate;

    private Date gmtUpdate;

    private String telArea;

    private static final long serialVersionUID = 1L;

    public Staff() {

    }

    public Staff(StaffCredentialsDto staffCredentialsDto) {
        realName = staffCredentialsDto.getRealName();
        no = staffCredentialsDto.getNo();
        position = staffCredentialsDto.getPosition();
        telArea = staffCredentialsDto.getTelArea();
        tel = staffCredentialsDto.getTel();
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getTelArea() {
        return telArea;
    }

    public void setTelArea(String telArea) {
        this.telArea = telArea == null ? null : telArea.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gid=").append(gid);
        sb.append(", realName=").append(realName);
        sb.append(", no=").append(no);
        sb.append(", position=").append(position);
        sb.append(", tel=").append(tel);
        sb.append(", email=").append(email);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", telArea=").append(telArea);
        sb.append("]");
        return sb.toString();
    }
}