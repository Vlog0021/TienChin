package com.javaboy.tienchin.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import com.javaboy.tienchin.course.domain.Course;
import com.javaboy.tienchin.course.mapper.CourseMapper;
import com.javaboy.tienchin.course.service.ICourseService;
import com.javaboy.tienchin.course.vo.CourseVo;
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
 * @since 2023-05-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> selectCourseList(CourseVo courseVo) {

        return courseMapper.selectCourseList(courseVo);
    }

    @Override
    public AjaxResult addCourse(Course course) {
        course.setCreateBy(SecurityUtils.getUsername());
        course.setCreateTime(LocalDateTime.now());
        course.setDelFlag(0);

        return save(course) ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateCourse(Course course) {
        course.setCreateBy(null);
        course.setCreateTime(null);
        course.setDelFlag(null);
        course.setUpdateBy(SecurityUtils.getUsername());
        course.setUpdateTime(LocalDateTime.now());

        return updateById(course) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public boolean deleteCourseByIds(Long[] courseIds) {
        UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
        courseUpdateWrapper.lambda().set(Course::getDelFlag, 1).in(Course::getCourseId, courseIds);

        return update(courseUpdateWrapper);
    }
}
