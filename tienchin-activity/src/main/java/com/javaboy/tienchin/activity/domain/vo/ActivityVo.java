package com.javaboy.tienchin.activity.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaboy.tienchin.common.validator.CreateGroup;
import com.javaboy.tienchin.common.validator.EditGroup;
import com.javaboy.tienchin.common.annotation.Excel;
import com.javaboy.tienchin.common.core.domain.BaseEntity;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * @author : shmily
 * @GitHub : https://github.com/shmily0021
 * @Gitee : https://gitee.com/shmily0213
 * @createDate: 2023/5/8 20:11
 */
public class ActivityVo extends BaseEntity {
    /**
     * 活动 ID
     */
    @NotNull(message = "{activity.id.notnull}", groups = EditGroup.class)
    @Excel(name = "活动编号")
    private Integer activityId;

    /**
     * 活动名称
     */
    @NotBlank(message = "{activity.name.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(min = 0, max = 20, message = "{activity.name.size}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动名称")
    private String name;

    /**
     * 渠道 ID
     */
    @NotNull(message = "{activity.channelId.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "渠道编号")
    private Integer channelId;

    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 活动简介
     */
    @NotBlank(message = "{activity.info.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @Size(min = 0, max = 255, message = "{activity.info.size}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动简介")
    private String info;

    /**
     * 活动类型 1 折扣券 2 代金券
     */
    @NotNull(message = "{activity.type.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动类型", readConverterExp = "1=折扣券,2=代金券")
    private Integer type;

    /**
     * 折扣券
     */
    @Min(value = 0, message = "{activity.discount.invalid}", groups = {CreateGroup.class, EditGroup.class})
    @Max(value = 10, message = "{activity.discount.invalid}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动折扣")
    private Double discount;

    /**
     * 代金券
     */
    @Min(value = 0, message = "{activity.voucher.invalid}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "代金券")
    private Double voucher;

    /**
     * 活动状态 0 表示禁用 1 表示正常
     */
    @Min(value = 0, message = "{activity.status.invalid}", groups = {CreateGroup.class, EditGroup.class})
    @Max(value = 1, message = "{activity.status.invalid}", groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动状态", readConverterExp = "0=过期,1=正常")
    private Integer status;

    /**
     * 活动开始时间
     */
    @NotNull(message = "{activity.beginTime.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Excel(name = "活动开始时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @NotNull(message = "{activity.endTime.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Excel(name = "活动结束时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 活动备注信息
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 是否删除，逻辑删除
     */
    private Integer delFlag;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVoucher() {
        return voucher;
    }

    public void setVoucher(Double voucher) {
        this.voucher = voucher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
