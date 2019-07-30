package com.lix.pushmessage.domain;

import javax.validation.constraints.NotNull;

public class NewMessage implements Message{
    @NotNull
    private String target;
    @NotNull
    private String type_name;
    @NotNull
    private Integer num;
    @NotNull
    private String level;
    // private long update_time;
    @Override
    public String messageContent(String account) {
        String content = String.format("您的账号%s,在客户识别智能平台上关注的公司【%s】,在消息明细的%s中新增了%s条%s新消息,详情请<a href=\"http://218.77.58.156:10000/#/Homepage/Dynamic?status=0\">点击此处查看新增消息</a>",account,target,level,num,type_name);
        return content;
    }

    @Override
    public String toString() {
        return "NewMessage{" +
                "target='" + target + '\'' +
                ", type_name='" + type_name + '\'' +
                ", num=" + num +
                ", level='" + level + '\'' +
                '}';
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
