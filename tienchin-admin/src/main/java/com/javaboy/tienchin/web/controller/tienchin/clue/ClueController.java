package com.javaboy.tienchin.web.controller.tienchin.clue;

import com.javaboy.tienchin.activity.service.IActivityService;
import com.javaboy.tienchin.channel.service.IChannelService;
import com.javaboy.tienchin.clue.domain.Clue;
import com.javaboy.tienchin.clue.domain.vo.ClueDetails;
import com.javaboy.tienchin.clue.domain.vo.ClueSummary;
import com.javaboy.tienchin.clue.domain.vo.ClueVo;
import com.javaboy.tienchin.clue.service.IClueService;
import com.javaboy.tienchin.common.annotation.Log;
import com.javaboy.tienchin.common.core.controller.BaseController;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.core.domain.entity.SysDept;
import com.javaboy.tienchin.common.core.page.TableDataInfo;
import com.javaboy.tienchin.common.enums.BusinessType;
import com.javaboy.tienchin.common.validator.CreateGroup;
import com.javaboy.tienchin.common.validator.EditGroup;
import com.javaboy.tienchin.system.service.ISysDeptService;
import com.javaboy.tienchin.system.service.ISysUserService;
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
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/tienchin/clue")
public class ClueController extends BaseController {

    @Autowired
    private IClueService clueService;

    @Autowired
    private IChannelService channelService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    /**
     * 添加线索
     * @param clue
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @Log(title = "线索管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Clue clue) {

        return clueService.addClue(clue);
    }

    /**
     * 获取所有渠道
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {

        return AjaxResult.success(channelService.list());
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @GetMapping("/activity/{channelId}")
    public AjaxResult getActivityByChannelId(@PathVariable Integer channelId) {
        return activityService.selectActivityByChannelId(channelId);
    }

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClueVo clueVo) {
        startPage();
        List<ClueSummary> list = clueService.selectClueList(clueVo);
        return getDataTable(list);
    }

    /**
     * 获取部门列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    @GetMapping("/users/{deptId}")
    @PreAuthorize("@ss.hasPermi('tienchin:clue:assignment')")
    public AjaxResult getUsersByDeptId(@PathVariable Long deptId) {
        return userService.getUserByDeptId(deptId);
    }

    /**
     * 根据线索 id 查询信息
     * @param clueId
     * @return
     */
    @GetMapping("/{clueId}")
    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    public AjaxResult getClueDetailsByClueId(@PathVariable Integer clueId) {
        return clueService.getClueDetailsByClueId(clueId);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @Log(title = "线索跟进", businessType = BusinessType.UPDATE)
    @PostMapping("/follow")
    public AjaxResult clueFollow(@RequestBody ClueDetails clueDetails) {

        return clueService.clueFollow(clueDetails);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @Log(title = "线索跟进", businessType = BusinessType.UPDATE)
    @PostMapping("/invalid")
    public AjaxResult invalidClueFollow(@RequestBody ClueDetails clueDetails) {

        return clueService.invalidClueFollow(clueDetails);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @Log(title = "线索转商机", businessType = BusinessType.UPDATE)
    @PostMapping("/to_business/{clueId}")
    public AjaxResult clueToBusiness(@PathVariable Integer clueId) {

        return clueService.clueToBusiness(clueId);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:edit')")
    @GetMapping("/summary/{clueId}")
    public AjaxResult getClueSummaryByClueId(@PathVariable Integer clueId) {

        return clueService.getClueSummaryByClueId(clueId);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:edit')")
    @Log(title = "线索修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateClue(@Validated(EditGroup.class) @RequestBody Clue clue) {
        return clueService.updateClue(clue);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:remove')")
    @Log(title = "线索删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clueIds}")
    public AjaxResult deleteClueById(@PathVariable Integer[] clueIds) {
        return clueService.deleteClueById(clueIds);
    }
}
