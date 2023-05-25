package com.javaboy.tienchin.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaboy.tienchin.channel.domain.Channel;
import com.javaboy.tienchin.channel.mapper.ChannelMapper;
import com.javaboy.tienchin.channel.service.IChannelService;
import com.javaboy.tienchin.channel.vo.ChannelVo;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-05
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> selectChannelList(ChannelVo channelVo) {
        return channelMapper.selectChannelList(channelVo);
    }

    @Override
    public AjaxResult addChannel(ChannelVo channelVo) {

        QueryWrapper<Channel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Channel::getChannelName, channelVo.getChannelName()).eq(Channel::getDelFlag, 0);

        Channel c = getOne(queryWrapper);
        if (c != null) {
            // 说明存在同名的渠道， 并且没有删除
            return AjaxResult.error("存在同名渠道， 添加失败");
        }

        Channel channel = new Channel();
        BeanUtils.copyProperties(channelVo, channel);
        channel.setCreateBy(SecurityUtils.getUsername()); // 这条记录谁创建的
        channel.setCreateTime(LocalDateTime.now());  // 创建时间为当前时间
        channel.setDelFlag(0);

        return save(channel) ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateChannel(ChannelVo channelVo) {

        Channel channel = new Channel();
        BeanUtils.copyProperties(channelVo, channel);
        channel.setUpdateBy(SecurityUtils.getUsername());
        channel.setUpdateTime(LocalDateTime.now());
        // 防止前端修改这三个属性
        channel.setCreateTime(null);
        channel.setCreateBy(null);
        channel.setDelFlag(null);

        return updateById(channel) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public boolean deleteChannelByIds(Long[] channelIds) {
        UpdateWrapper<Channel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(Channel::getDelFlag, 1).in(Channel::getChannelId, channelIds);

        return update(updateWrapper);
    }

    @Override
    public boolean importChannel(List<Channel> channelList, boolean updateSupport) {
        if (updateSupport) {
            // 如果是更新 根据 id 去更新
            List<Channel> channels = channelList.stream().map(c -> {
                c.setCreateTime(LocalDateTime.now());
                c.setCreateBy(SecurityUtils.getUsername());
                return c;
            }).collect(Collectors.toList());
            return updateBatchById(channels);
        } else {
            // 插入
            List<Channel> channels = channelList.stream().map(c -> {
                c.setCreateTime(LocalDateTime.now());
                c.setCreateBy(SecurityUtils.getUsername());
                c.setChannelId(null);
                return c;
            }).collect(Collectors.toList());
            return saveBatch(channels);
        }
    }
}
