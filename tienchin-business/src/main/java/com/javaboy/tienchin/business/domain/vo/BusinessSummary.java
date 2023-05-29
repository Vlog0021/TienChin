package com.javaboy.tienchin.business.domain.vo;

import java.time.LocalDateTime;

/**
 * 商机的摘要信息
 */
public class BusinessSummary {

    /**
     * 商机ID
     */
    private Integer businessId;

    /**
     * 客户名字
     */
    private String name;

    /**
     * 客户手机号
     */
    private String phone;

    /**
     * 商机归属人
     */
    private String owner;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    /**
     * 商机状态
     */
    private Integer status;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    public void setNextTime(LocalDateTime nextTime) {
        this.nextTime = nextTime;
    }
}
