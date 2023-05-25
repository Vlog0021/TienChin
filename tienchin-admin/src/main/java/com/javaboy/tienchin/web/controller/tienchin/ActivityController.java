package com.javaboy.tienchin.web.controller.tienchin;

import com.javaboy.tienchin.activity.domain.vo.ActivityVo;
import com.javaboy.tienchin.activity.service.IActivityService;
import com.javaboy.tienchin.common.validator.CreateGroup;
import com.javaboy.tienchin.common.validator.EditGroup;
import com.javaboy.tienchin.channel.service.IChannelService;
import com.javaboy.tienchin.channel.vo.ChannelVo;
import com.javaboy.tienchin.common.annotation.Log;
import com.javaboy.tienchin.common.core.controller.BaseController;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.core.page.TableDataInfo;
import com.javaboy.tienchin.common.enums.BusinessType;
import com.javaboy.tienchin.common.utils.poi.ExcelUtil;
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
 * @since 2023-05-08
 */
@RestController
@RequestMapping("/tienchin/activity")
public class ActivityController extends BaseController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IChannelService channelService;

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityVo activityVo) {
        startPage();
        List<ActivityVo> list = activityService.selectActivityList(activityVo);
        return getDataTable(list);
    }

    /**
     * 添加活动
     * @param activityVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:create')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody ActivityVo activityVo) {

        return activityService.addActivity(activityVo);
    }

    /**
     * 修改/更新活动
     * @param activityVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody ActivityVo activityVo) {

        return activityService.updateActivity(activityVo);
    }

    /**
     * 删除活动
     * @param activityIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds) {

        return toAjax(activityService.deleteActivityByIds(activityIds));
    }

    /**
     * 导出活动
     * @param response
     * @param activityVo
     */
    @Log(title = "活动管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:avtivity:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityVo activityVo) {
        List<ActivityVo> list = activityService.selectActivityList(activityVo);
        ExcelUtil<ActivityVo> util = new ExcelUtil<ActivityVo>(ActivityVo.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 添加活动的渠道列表查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:create')")
    @GetMapping("/channel/list")
    public AjaxResult channelList() {

        return AjaxResult.success(channelService.selectChannelList(new ChannelVo()));
    }

    /**
     * 根据活动 ID 查询一个具体的活动
     * @param activityId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    @GetMapping("/{activityId}")
    public AjaxResult getInfo(@PathVariable Long activityId) {

        return AjaxResult.success(activityService.getActivityById(activityId));
    }
}
