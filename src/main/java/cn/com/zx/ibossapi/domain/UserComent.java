package cn.com.zx.ibossapi.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserComent {

    private Date time;
    private String userCode;
    private String imgUrl;
    private String nikeName;

    private String coment;
    private Integer replyNUm;

    private Integer apprNum;
    private Integer oppoNum;
    private List<Map<String,String>> replyList;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public Integer getReplyNUm() {
        return replyNUm;
    }

    public void setReplyNUm(Integer replyNUm) {
        this.replyNUm = replyNUm;
    }

    public Integer getApprNum() {
        return apprNum;
    }

    public void setApprNum(Integer apprNum) {
        this.apprNum = apprNum;
    }

    public Integer getOppoNum() {
        return oppoNum;
    }

    public void setOppoNum(Integer oppoNum) {
        this.oppoNum = oppoNum;
    }

    public List<Map<String, String>> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Map<String, String>> replyList) {
        this.replyList = replyList;
    }
}
