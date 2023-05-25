package com.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaboy.tienchin.clue.domain.FollowRecord;
import com.javaboy.tienchin.clue.mapper.FollowRecordMapper;
import com.javaboy.tienchin.clue.service.IFollowRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.common.constant.TienChinConstants;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
@Service
public class FollowRecordServiceImpl extends ServiceImpl<FollowRecordMapper, FollowRecord> implements IFollowRecordService {

    @Override
    public AjaxResult getFollowRecordByClueId(Integer clueId) {

        QueryWrapper<FollowRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FollowRecord::getType, TienChinConstants.CLUE_TYPE).eq(FollowRecord::getAssignId, clueId).orderByDesc(FollowRecord::getCreateTime);
        return AjaxResult.success(list(queryWrapper));
    }
}
