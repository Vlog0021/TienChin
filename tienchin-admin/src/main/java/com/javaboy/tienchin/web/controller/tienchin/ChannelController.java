package com.javaboy.tienchin.web.controller.tienchin;

import com.javaboy.tienchin.channel.domain.Channel;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shmily
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/tienchin/channel")
public class ChannelController extends BaseController {

    @Autowired
    private IChannelService channelService;

    /**
     * 分页查询
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChannelVo channelVo) {
        startPage();
        List<Channel> list = channelService.selectChannelList(channelVo);
        return getDataTable(list);
    }

    /**
     * 添加渠道
     * @param channelVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:create')")
    @Log(title = "渠道管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ChannelVo channelVo) {

        return channelService.addChannel(channelVo);
    }

    /**
     * 更新渠道 修改保存渠道
     * @param channelVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:edit')")
    @Log(title = "渠道管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ChannelVo channelVo) {

        return channelService.updateChannel(channelVo);
    }

    /**
     * 根据渠道 ID 查询一个具体的渠道
     * @param channelId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:edit')")
    @GetMapping("/{channelId}")
    public AjaxResult getInfo(@PathVariable Long channelId) {

        return AjaxResult.success(channelService.getById(channelId));
    }

    /**
     * 删除渠道
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:remove')")
    @Log(title = "渠道管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{channelIds}")
    public AjaxResult remove(@PathVariable Long[] channelIds) {
        return toAjax(channelService.deleteChannelByIds(channelIds));
    }

    /**
     * 渠道导出
     * @param response
     * @param channelVo
     */
    @Log(title = "渠道管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:channel:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChannelVo channelVo) {
        List<Channel> list = channelService.selectChannelList(channelVo);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.exportExcel(response, list, "渠道数据");
    }

    /**
     * 导出的模板
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.importTemplateExcel(response, "渠道数据");
    }

    /**
     * 导入时的选项框是更新还是插入
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "渠道管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:channel:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        List<Channel> channelList = util.importExcel(file.getInputStream());
        return AjaxResult.success(channelService.importChannel(channelList, updateSupport));
    }
}
