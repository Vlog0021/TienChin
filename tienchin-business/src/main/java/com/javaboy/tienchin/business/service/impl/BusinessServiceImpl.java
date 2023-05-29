package com.javaboy.tienchin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.assignment.domain.Assignment;
import com.javaboy.tienchin.assignment.service.IAssignmentService;
import com.javaboy.tienchin.business.domain.Business;
import com.javaboy.tienchin.business.domain.vo.BusinessFollow;
import com.javaboy.tienchin.business.domain.vo.BusinessSummary;
import com.javaboy.tienchin.business.mapper.BusinessMapper;
import com.javaboy.tienchin.business.service.IBusinessService;
import com.javaboy.tienchin.common.constant.TienChinConstants;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import com.javaboy.tienchin.follow.domain.FollowRecord;
import com.javaboy.tienchin.follow.service.IFollowRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-25
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IFollowRecordService followRecordService;
    @Override
    public List<BusinessSummary> selectBusinessList() {
        return businessMapper.selectBusinessList();
    }

    @Override
    @Transactional
    public AjaxResult addBusiness(Business business) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        // 按照手机号码查询这个客户， 如果这个客户的信息已经录入了，则停止录入
        queryWrapper.lambda().eq(Business::getPhone, business.getPhone());
        Business one = getOne(queryWrapper);
        if (one != null) {
            return AjaxResult.error("客户手机号码重复, 添加失败");
        }
        try {
            business.setStatus(TienChinConstants.BUSINESS_ALLOCATED);
            business.setCreateTime(LocalDateTime.now());
            business.setCreateBy(SecurityUtils.getUsername());
            business.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME));
            save(business);

            // 任务分配
            Assignment assignment = new Assignment();
            assignment.setAssignId(business.getBusinessId());
            assignment.setLatest(true);
            assignment.setType(TienChinConstants.BUSINESS_TYPE);
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setUserName(SecurityUtils.getUsername());

            assignmentService.save(assignment);
            return AjaxResult.success("商机录入成功");
        } catch (Exception e) {
            return AjaxResult.error("商机录入失败 : " + e.getMessage());
        }
    }

    @Override
    public AjaxResult getBusinessById(Integer id) {

        return AjaxResult.success(getById(id));
    }

    @Override
    @Transactional
    public AjaxResult follow(BusinessFollow businessFollow) {
        try {
            // 1. 线索本身的完善
            Business business = new Business();
            BeanUtils.copyProperties(businessFollow, business);
            business.setUpdateTime(LocalDateTime.now());
            business.setUpdateBy(SecurityUtils.getUsername());

            updateById(business);
            // 2. 添加一个跟踪记录
            FollowRecord followRecord = new FollowRecord();
            followRecord.setInfo(businessFollow.getInfo());
            followRecord.setType(TienChinConstants.BUSINESS_TYPE);
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setAssignId(businessFollow.getBusinessId());
            followRecordService.save(followRecord);

            return AjaxResult.error("商机跟进成功");
        } catch (BeansException e) {
            return AjaxResult.error("商机跟进失败");
        }
    }
}
