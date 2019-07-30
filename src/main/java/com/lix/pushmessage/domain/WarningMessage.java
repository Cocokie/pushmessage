package com.lix.pushmessage.domain;

import javax.validation.constraints.NotNull;

public class WarningMessage implements Message {
    @NotNull
    String target;
    @NotNull
    String infoType;
    @NotNull
    String infolevel;
    @NotNull
    Integer infoNum;

    @Override
    public String messageContent(String account) {
        String content = String.format("您的账号%s,在客户识别智能平台上关注的公司【%s】,在风险指标中更新了%s条%s信息,详情请<a href=\"http://218.77.58.156:10000/#/Homepage/Dynamic?status=1\">点击此处查看更新消息</a>",account,target,infoNum,infolevel);
        return content;
    }

    public Integer getInfoNum() {
        return infoNum;
    }

    @Override
    public String toString() {
        return "WarningMessage{" +
                "target='" + target + '\'' +
                ", infoType='" + infoType + '\'' +
                ", infolevel='" + infolevel + '\'' +
                ", infoNum=" + infoNum +
                '}';
    }

    public void setInfoNum(Integer infoNum) {
        this.infoNum = infoNum;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfolevel() {
        return infolevel;
    }

    public void setInfolevel(String infolevel) {
        this.infolevel = infolevel;
    }


}
