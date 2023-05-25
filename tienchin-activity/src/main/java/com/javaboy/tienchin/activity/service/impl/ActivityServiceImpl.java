package com.javaboy.tienchin.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.activity.domain.Activity;
import com.javaboy.tienchin.activity.domain.vo.ActivityVo;
import com.javaboy.tienchin.activity.mapper.ActivityMapper;
import com.javaboy.tienchin.activity.service.IActivityService;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-08
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<ActivityVo> selectActivityList(ActivityVo activityVo) {
        expireActivity();
        return activityMapper.selectActivityList(activityVo);
    }

    @Override
    public AjaxResult addActivity(ActivityVo activityVo) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVo, activity);
        activity.setCreateTime(LocalDateTime.now());
        activity.setCreateBy(SecurityUtils.getUsername());
        activity.setDelFlag(0);
        // 设置活动未过期， 新活动默认都是未过期
        activity.setStatus(1);

        return save(activity) ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateActivity(ActivityVo activityVo) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVo, activity);
        activity.setUpdateBy(SecurityUtils.getUsername());
        activity.setUpdateTime(LocalDateTime.now());
        // 修改的时候不修改为 null 的字段
        activity.setCreateBy(null);
        activity.setCreateTime(null);
        activity.setDelFlag(null);

        return updateById(activity) ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }

    @Override
    public ActivityVo getActivityById(Long activityId) {
        Activity activity = getById(activityId);
        ActivityVo activityVo = new ActivityVo();
        BeanUtils.copyProperties(activity, activityVo);

        return activityVo;
    }

    @Override
    public boolean deleteActivityByIds(Long[] activityIds) {
        UpdateWrapper<Activity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(Activity::getDelFlag, 1).in(Activity::getActivityId,activityIds);

        return update(updateWrapper);
    }

    @Override
    public AjaxResult selectActivityByChannelId(Integer channelId) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Activity::getChannelId, channelId);
        return AjaxResult.success(list(queryWrapper));
    }

    private boolean expireActivity() {

        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        // 将原本状态为 1 并且 endTime 小于当前时间的活动，设置为国过期
        uw.lambda().set(Activity::getStatus, 0).eq(Activity::getStatus, 1).lt(Activity::getEndTime, LocalDateTime.now());
        return update(uw);
    }
}
