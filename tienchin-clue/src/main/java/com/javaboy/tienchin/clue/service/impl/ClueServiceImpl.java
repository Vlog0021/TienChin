package com.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.javaboy.tienchin.clue.domain.Assignment;
import com.javaboy.tienchin.clue.domain.Clue;
import com.javaboy.tienchin.clue.domain.FollowRecord;
import com.javaboy.tienchin.clue.domain.vo.ClueDetails;
import com.javaboy.tienchin.clue.domain.vo.ClueSummary;
import com.javaboy.tienchin.clue.domain.vo.ClueVo;
import com.javaboy.tienchin.clue.mapper.ClueMapper;
import com.javaboy.tienchin.clue.service.IAssignmentService;
import com.javaboy.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.clue.service.IFollowRecordService;
import com.javaboy.tienchin.common.constant.TienChinConstants;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private IFollowRecordService followRecordService;

    @Override
    @Transactional
    public AjaxResult addClue(Clue clue) {

        QueryWrapper<Clue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Clue::getPhone, clue.getPhone());
        Clue one = getOne(queryWrapper);

        if (one != null) {
            return AjaxResult.error("手机号码重复，线索录入失败");
        }
        clue.setCreateBy(SecurityUtils.getUsername());
        clue.setCreateTime(LocalDateTime.now());
        clue.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME)); // 设置下次跟进时间

        try {
            // 添加线索
            save(clue);
            // 添加线索默认的分配人
            Assignment assignment = new Assignment();
            assignment.setAssignId(clue.getClueId());
            assignment.setLatest(true);
            assignment.setType(TienChinConstants.CLUE_TYPE);
            assignment.setUserName(SecurityUtils.getUsername());
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignmentService.save(assignment);
            return AjaxResult.success("线索录入成功");
        } catch (Exception e) {
            return AjaxResult.error("线索录入失败");
        }

    }

    @Override
    public List<ClueSummary> selectClueList(ClueVo clueVo) {

        return clueMapper.selectClueList(clueVo);
    }

    @Override
    public AjaxResult getClueDetailsByClueId(Integer clueId) {
        ClueDetails clueDetails = clueMapper.getClueDetailsByClueId(clueId);
        return AjaxResult.success(clueDetails);
    }

    @Override
    @Transactional
    public AjaxResult clueFollow(ClueDetails clueDetails) {
        try {
            // 1. 更新 clue 表
            Clue clue = new Clue();
            // 将clueDetails中的属性拷贝到clue 属性不全无所谓
            BeanUtils.copyProperties(clueDetails, clue);
            clue.setStatus(TienChinConstants.CLUE_FOLLOING);

            updateById(clue);
            // 2. 更新 clue_follow 表
            FollowRecord followRecord = new FollowRecord();
            followRecord.setAssignId(clueDetails.getClueId());
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setType(TienChinConstants.CLUE_TYPE);
            followRecord.setInfo(clueDetails.getRecord());

            followRecordService.save(followRecord);

            return AjaxResult.success("线索跟进成功");
        } catch (BeansException e) {
            return AjaxResult.error("线索跟进失败 : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AjaxResult invalidClueFollow(ClueDetails clueDetails) {
        try {
            // 如果已经失败三次， 则这条线索就变成了伪线索
            Clue id = getById(clueDetails.getClueId());
            if (id.getFailCount() == 3) {
                // 无效线索已达上限
                UpdateWrapper<Clue> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().set(Clue::getStatus, TienChinConstants.CLUE_INVALIDATE).eq(Clue::getClueId, clueDetails.getClueId());
                update(updateWrapper);

                return AjaxResult.success("无效线索设置成功");
            }

            // 1. 设置线索表中的 fail_count 字段 +1
            UpdateWrapper<Clue> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().setSql("fail_count = fail_count + 1").eq(Clue::getClueId, clueDetails.getClueId());
            update(updateWrapper);
            // 2. 在线索记录表中添加一条记录
            FollowRecord followRecord = new FollowRecord();
            followRecord.setInfo(clueDetails.getRecord());
            followRecord.setType(TienChinConstants.CLUE_TYPE);
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecord.setAssignId(clueDetails.getClueId());

            followRecordService.save(followRecord);

            return AjaxResult.success("无效线索设置成功");
        } catch (Exception e) {
            return AjaxResult.error("无效线索设置失败" + e.getMessage());
        }
    }

    @Override
    public AjaxResult getClueSummaryByClueId(Integer clueId) {
        Clue clue = getById(clueId);

        return AjaxResult.success(clue);
    }

    @Override
    public AjaxResult updateClue(Clue clue) {

        return updateById(clue) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public AjaxResult deleteClueById(Integer[] clueIds) {
        UpdateWrapper<Clue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(Clue::getDelFlag, 1).in(Clue::getClueId, clueIds);

        return update(updateWrapper) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }
}
