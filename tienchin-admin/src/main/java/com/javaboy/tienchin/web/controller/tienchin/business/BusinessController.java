package com.javaboy.tienchin.web.controller.tienchin.business;

import com.javaboy.tienchin.activity.service.IActivityService;
import com.javaboy.tienchin.business.domain.Business;
import com.javaboy.tienchin.business.domain.vo.BusinessFollow;
import com.javaboy.tienchin.business.domain.vo.BusinessSummary;
import com.javaboy.tienchin.business.service.IBusinessService;
import com.javaboy.tienchin.channel.service.IChannelService;
import com.javaboy.tienchin.common.annotation.Log;
import com.javaboy.tienchin.common.core.controller.BaseController;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.core.page.TableDataInfo;
import com.javaboy.tienchin.common.enums.BusinessType;
import com.javaboy.tienchin.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shmily
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/tienchin/business")
public class BusinessController extends BaseController {

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IChannelService channelService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ICourseService courseService;

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<BusinessSummary> list = businessService.selectBusinessList();
        return getDataTable(list);
    }

    /**
     * 新增商机
     * @param business
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @Log(title = "商机管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Business business) {

        return businessService.addBusiness(business);
    }

    /**
     * 获取所有渠道
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:business:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {

        return AjaxResult.success(channelService.list());
    }

    /**
     * 根据渠道id查询活动
     * @param channelId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:business:create')")
    @GetMapping("/activity/{channelId}")
    public AjaxResult getActivityByChannelId(@PathVariable Integer channelId) {
        return activityService.selectActivityByChannelId(channelId);
    }

    /**
     * 根据渠道id查询活动
     * @param type
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:business:list')")
    @GetMapping("/course/{type}")
    public AjaxResult getCourseByCourseType(@PathVariable Integer type) {
        return courseService.getCourseByCourseType(type);
    }

    /**
     * 根据商机 id 查询信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    public AjaxResult getBusinessById(@PathVariable Integer id) {
        return businessService.getBusinessById(id);
    }

    /**
     * 查询所有课程
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    @GetMapping("/allCourse")
    public AjaxResult getAllCourse() {
        return AjaxResult.success(courseService.list());
    }

    /**
     * 商机详情页提交保存
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @PostMapping("/follow")
    public AjaxResult follow(@Validated @RequestBody BusinessFollow businessFollow) {
        return businessService.follow(businessFollow);
    }
}
