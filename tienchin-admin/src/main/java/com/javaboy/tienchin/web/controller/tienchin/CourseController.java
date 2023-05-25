package com.javaboy.tienchin.web.controller.tienchin;

import com.javaboy.tienchin.common.annotation.Log;
import com.javaboy.tienchin.common.core.controller.BaseController;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.core.page.TableDataInfo;
import com.javaboy.tienchin.common.enums.BusinessType;
import com.javaboy.tienchin.common.utils.poi.ExcelUtil;
import com.javaboy.tienchin.common.validator.EditGroup;
import com.javaboy.tienchin.course.domain.Course;
import com.javaboy.tienchin.course.service.ICourseService;
import com.javaboy.tienchin.course.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shmily
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/tienchin/course")
public class CourseController extends BaseController {

    @Autowired
    private ICourseService courseService;

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseVo courseVo) {
        startPage();
        List<Course> list = courseService.selectCourseList(courseVo);
        return getDataTable(list);
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:create')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Course course) {

        return courseService.addCourse(course);
    }

    /**
     * 修改/更新课程
     * @param course
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Course course) {

        return courseService.updateCourse(course);
    }

    /**
     * 删除课程
     * @param courseIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds) {

        return toAjax(courseService.deleteCourseByIds(courseIds));
    }

    /**
     * 导出课程
     * @param response
     * @param courseVo
     */
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:course:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseVo courseVo) {
        List<Course> list = courseService.selectCourseList(courseVo);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程数据");
    }

    /**
     * 根据活动 ID 查询一个具体的课程
     * @param courseId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    @GetMapping("/{courseId}")
    public AjaxResult getInfo(@PathVariable Long courseId) {

        return AjaxResult.success(courseService.getById(courseId));
    }
}
