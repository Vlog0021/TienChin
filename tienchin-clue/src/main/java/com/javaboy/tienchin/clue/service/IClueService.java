package com.javaboy.tienchin.clue.service;

import com.javaboy.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.clue.domain.vo.ClueDetails;
import com.javaboy.tienchin.clue.domain.vo.ClueSummary;
import com.javaboy.tienchin.clue.domain.vo.ClueVo;
import com.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
public interface IClueService extends IService<Clue> {

    AjaxResult addClue(Clue clue);

    List<ClueSummary> selectClueList(ClueVo clueVo);

    AjaxResult getClueDetailsByClueId(Integer clueId);

    AjaxResult clueFollow(ClueDetails clueDetails);

    AjaxResult invalidClueFollow(ClueDetails clueDetails);

    AjaxResult getClueSummaryByClueId(Integer clueId);

    AjaxResult updateClue(Clue clue);

    AjaxResult deleteClueById(Integer[] clueIds);

    AjaxResult clueToBusiness(Integer clueId);
}
