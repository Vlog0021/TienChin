package com.javaboy.tienchin.follow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.follow.domain.FollowRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
public interface IFollowRecordService extends IService<FollowRecord> {

    AjaxResult getFollowRecordByClueId(Integer clueId);
}
