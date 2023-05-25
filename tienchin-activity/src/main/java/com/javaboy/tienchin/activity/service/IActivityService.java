package com.javaboy.tienchin.activity.service;

import com.javaboy.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.activity.domain.vo.ActivityVo;
import com.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-08
 */
public interface IActivityService extends IService<Activity> {

    List<ActivityVo> selectActivityList(ActivityVo activityVo);

    AjaxResult addActivity(ActivityVo activityVo);

    AjaxResult updateActivity(ActivityVo activityVo);

    ActivityVo getActivityById(Long activityId);

    boolean deleteActivityByIds(Long[] activityIds);

    AjaxResult selectActivityByChannelId(Integer channelId);
}
