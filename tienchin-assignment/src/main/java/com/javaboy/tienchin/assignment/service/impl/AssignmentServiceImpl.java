package com.javaboy.tienchin.assignment.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.assignment.domain.Assignment;
import com.javaboy.tienchin.assignment.mapper.AssignmentMapper;
import com.javaboy.tienchin.assignment.service.IAssignmentService;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements IAssignmentService {

    @Override
    @Transactional
    public AjaxResult assignClue(Assignment assignment) {
        try {
            // 1. 先将一个线索的所有分配记录中的 latest 属性设置为 false
            UpdateWrapper<Assignment> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(Assignment::getLatest, false).eq(Assignment::getAssignId, assignment.getAssignId());
            update(updateWrapper);

            // 2. 分配线索
            // assignment.setType(TienChinConstants.CLUE_TYPE);
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setLatest(true);
            save(assignment);

            return AjaxResult.success("线索分配成功");
        } catch (Exception e) {
            return AjaxResult.error("线索分配失败" + e.getMessage());
        }
    }
}
