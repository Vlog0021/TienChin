package com.javaboy.tienchin.clue.service;

import com.javaboy.tienchin.clue.domain.FollowRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.common.core.domain.AjaxResult;

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
