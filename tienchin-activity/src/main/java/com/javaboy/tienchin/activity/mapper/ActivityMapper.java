package com.javaboy.tienchin.activity.mapper;

import com.javaboy.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaboy.tienchin.activity.domain.vo.ActivityVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shmily
 * @since 2023-05-08
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    List<ActivityVo> selectActivityList(ActivityVo activityVo);
}
