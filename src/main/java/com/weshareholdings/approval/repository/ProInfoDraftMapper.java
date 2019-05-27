package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.vo.ProDraftVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author liling
 */
@Component
@Mapper
public interface ProInfoDraftMapper {
    /**
     * 根据主键删除
     * @param key
     * @return
     */
    int deleteByPrimaryKey(String key);

    /**
     * 插入项目草稿表
     * @param record
     * @return
     */
    int insert(ProInfoDraft record);

    /**
     * 插入项目草稿表(排除空值)
     * @param record
     * @return
     */
    int insertSelective(ProInfoDraft record);

    int insertProInfoDraftSelective(Map proInfo);

    /**
     * 根据主键查询项目草稿信息
     * @param key
     * @return
     */
    ProInfoDraft selectByPrimaryKey(String key);

    /**
     * 根据主键更新项目草稿信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProInfoDraft record);

    int updateProInfoDraftSelective(Map proInfoDraft);

    /**
     * 根据主键更新项目草稿信息(排除空值)
     * @param record
     * @return
     */
    int updateByPrimaryKey(ProInfoDraft record);

    /**
     *查询全部草稿
     * @return
     */
    List<ProDraftVo> selectProDraft(@Param("userId") String userId, @Param("proName") String proName, @Param("proTypes") String... proTypes);

    /**
     * 插入项目附件草稿
     * @param attach
     * @return
     */
    int insertAttachDraft(Attach attach);

    int insertAttachDraftSelective(Map attach);

    /**
     * 更新项目附件草稿
     * @param attach
     * @return
     */
    int updateAttachDraftByPrimaryKeySelective(Map attach);

    /**
     * 根据项目ID查询附件信息
     * @param proId
     * @return
     */
    List<Attach> selectAttachDraftByProId(@Param("proId") int proId, @Param("proAttachType") String proAttachType);

    /**
     * g根据附件id查询附件
     * @param id
     * @return
     */
    Attach queryAttachByFileId(@Param("id")String id);

    /**
     * 根据项目ID删除分层信息
     * @param proId
     */
    void deleteAttachDraftByProId(@Param("proId") int proId);

    /**
     * 根据项目ID查询分层信息
     * @param proId
     * @return
     */
    List<ProLayer> selectProLayerDraftByProId(@Param("proId") int proId);

    /**
     * 插入分层结构草稿
     * @param layer
     * @return
     */
    int insertLayerDraftSelective(Map layer);

    /**
     * 更新分层结构草稿
     * @param layer
     * @return
     */
    int updateLayerDraftByPrimaryKeySelective(Map layer);

    /**
     * 根据项目ID删除分层信息
     * @param proId
     */
    void deleteLayerDraftByProId(@Param("proId") int proId);

    /**
     * 根据项目ID查询项目团队信息
     * @param proId
     * @return
     */
    List<Team> selectTeamDraftByProId(@Param("proId") int proId);

    /**
     * 根据项目ID查询项目业务模式关系草稿
     * @param proId
     * @return
     */
    List<ProModel> queryProModelDetailByProId(@Param("proId") int proId);

    /**
     * 插入分层结构草稿
     * @param layer
     * @return
     */
    int insertTeamDraftSelective(Map layer);

    /**
     * 插入项目业务模式关系草稿
     * @param layer
     * @return
     */
    int insertProModelDraftSelective(Map layer);

    /**
     * 更新分层结构草稿
     * @param layer
     * @return
     */
    int updateTeamDraftByPrimaryKeySelective(Map layer);

    /**
     * 根据项目ID删除分层信息
     * @param proId
     */
    void deleteTeamDraftByProId(@Param("proId") int proId);

    /**
     * 根据项目ID删除项目业务模式关系草稿信息
     * @param proId
     */
    void deleteProModelDraftByProId(@Param("proId") int proId);

    /**
     * 往交易结构草稿表中插入一条数据
     * @param proTradeStructureDraft
     * @return
     */
    Integer insertProTradeStructureDraft(Map proTradeStructureDraft);

    /**
     * 根据id修改项目交易结构草稿
     * @param proTradeStructureDraft
     * @return
     */
    Integer updateProTradeStructureDraft(Map proTradeStructureDraft);

    /**
     * 根据项目id查询项目交易结构草稿
     * @param proId
     * @return
     */
    ProInfoTradeStructure queryProInfoTradeStructureDraftByProId(@Param("proId") String proId);

    /**
     * 根据项目id个附件类型查询项目附件草稿
     *
     * @param proId
     * @return
     */
    List<Attach> queryAttactDraftByProId(@Param("proId")String proId,@Param("proAttachType")String proAttachType);

}