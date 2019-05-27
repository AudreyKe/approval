package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.vo.FileApprovalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProjectMapper {

    /**
     * 根据流程实例id查询项目详情
     *
     * @param id 项目id
     * @return
     */
    ProInfo queryProById(String id);


    @Select("SELECT  wt.variable FROM wf_hist_task wt\n" +
            "WHERE wt.order_Id = #{orderId} ORDER BY create_Time ASC")
    List<String> queryOrderFlowById(String orderId);

    /**
     * 添加项目
     *
     * @param proInfo 项目详情
     * @return
     */
    int insertProInfo(Map proInfo);

    /**
     * 添加项目
     *
     * @param proInfo 项目详情
     * @return
     */
    int insertProInfoPro(ProInfo proInfo);

    /**
     * 根据流程ID查询项目团队
     *
     * @return
     */
    String queryProTeamByOrderId(@Param("orderId") String orderId);

    /**
     * 更新项目信息
     *
     * @param proInfo
     */
    void updateProInfo(Map proInfo);

    /**
     * 往项目分层表中记录一条数据
     *
     * @param proLayer
     * @return
     */
    int insertProLayer(Map proLayer);

    /**
     * 往项目分层表中记录一条数据
     *
     * @param proLayer
     * @return
     */
    int insertProLayerClass(ProLayer proLayer);

    /**
     * 往项目团队成员表中记录一条数据
     *
     * @param team
     * @return
     */
    int insertProTeam(Map team);

    /**
     * 往项目团队成员历史表中记录一条数据
     *
     * @param team
     * @return
     */
    int insertProTeamHis(Map team);


    /**
     * 往项目业务模式关系表中记录一条数据
     *
     * @param proModel
     * @return
     */
    int insertProModelRelation(Map proModel);

    /**
     * 往项目业务模式关系历史表中记录一条数据
     *
     * @param proModel
     * @return
     */
    int insertProModelRelationHis(Map proModel);

    /**
     * 往项目业务模式表中记录一条数据
     *
     * @param proModel
     * @return
     */
    Integer insertProModel(Map proModel);

    /**
     * 往交易结构表中插入一条数据
     *
     * @param proModel
     * @return
     */
    int insertProInfoTradeStructure(Map proModel);

    /**
     * 往交易结构历史表中插入一条数据
     *
     * @param proModel
     * @return
     */
    int insertProInfoTradeStructureCopy(Map proModel);

    /**
     * 根据主键删除分层信息
     *
     * @param id 分层主键
     */
    void deleteProLayerByPrimaryKey(int id);

    /**
     * 根据主键删除团队信息
     *
     * @param id 分层主键
     */
    void deleteProTeamByPrimaryKey(int id);

    /**
     * 根据主键删除项目业务模式关系信息
     *
     * @param id 分层主键
     */
    void deleteProModelByPrimaryKey(int id);

    /**
     * 插入分层信息进备份表
     *
     * @param proLayer
     */
    void insertProLayerCopy(Map proLayer);

    /**
     * 根据项目id查询项目分层详情
     *
     * @param id 项目id
     * @return
     */
    List<ProLayer> queryProLayerByProId(String id);


    /**
     * 根据项目id个附件类型查询项目附件
     *
     * @param proId
     * @return
     */
    List<Attach> queryAttactByProId(@Param("proId")String proId,@Param("proAttachType")String proAttachType);

    /**
     * 根据项目id查询项目团队
     *
     * @param proId
     * @return
     */
    List<Team> queryTeamByProId(String proId);

    /**
     * 根据项目id查询项目业务模式
     *
     * @param proId
     * @return
     */
    List<ProModel> queryProModelDetailByProId(String proId);

    /**
     * 根据项目id查询项目业务模式关系
     *
     * @param proId
     * @return
     */
    List<String> queryProModelByProId(String proId);

    /**
     * 根据流程id查询项目团队
     * @param orderId
     * @return
     */
    List<Team> queryTeamByOrderId(String orderId);

    /**
     * 往附件表中记录一条数据
     *
     * @param attach 附件详情
     * @return
     */
    int insertAttachClass(Attach attach);

    /**
     * 根据附件id修改附件信息
     *
     * @param attach 附件详情
     * @return
     */
    int updateAttachByAttachId(Attach attach);

    /**
     * 根据附件id查询附件信息
     *
     * @param id
     * @return
     */
    Attach queryPathByFileId(String id);

    /**
     * 根据用户账号查询用户信息
     *
     * @param userName
     * @return
     */
    User queryUserByUserName(String userName);

    /**
     * 根据部门查询上会人员
     *
     * @param department 部门code
     * @return
     */
    List<User> queryMeetPersonByProType(String department);

    /**
     * 根据保存文件名查询文件信息
     *
     * @param fileName
     * @return
     */
    List<Attach> queryAttachByFileName(String fileName);

    /**
     * 查询附件列表
     *
     * @param attachIds 附件ID集合
     * @return
     */
    List<Attach> selectAttachList(@Param("attachIds") List<String> attachIds);

    /**
     * 批量插入附件信息
     *
     * @param attaches
     */
    void batchInsertAttachCopy(@Param("attaches") List<Map> attaches);

    /**
     * 插入附件copy 表
     *
     * @param attach 附件详情
     * @return
     */
    int insertAttachCopyClass(Attach attach);

    /**
     * 删除附件信息
     *
     * @param id
     */
    void deleteAttachByPrimaryKey(@Param("id") String id);

    /**
     * 根据项目id删除附件信息
     *
     * @param proId
     */
    void deleteAttachByProId(@Param("proId") String proId);

    /**
     * 根据附件id更新附件copy表信息
     *
     * @param attach 附件详情
     * @return
     */
    int updateAttachCopyByAttachId(Attach attach);

    /**
     * 批量插入附件信息
     *
     * @param attaches
     */
    void batchInsertAttach(@Param("attaches") List<Map> attaches);

    /**
     * 查询审批中的项目
     * @param baseProTypes 当前用户可访问项目类型
     * @param proName 项目名称
     * @return
     */
    List<FileApprovalVo> selectApprovalPros( @Param("proName") String proName,@Param("baseProTypes") List<String> baseProTypes, @Param("proType") String proType);

    /**
     * 根据项目id查询项目内容
     * @param attachId
     * @return
     */
    ProInfo selectProInfoByAttachId(@Param("attachId") String attachId);

    /**
     * 根据id修改项目交易结构
     * @param proTradeStructureDraft
     * @return
     */
    Integer updateProTradeStructure(Map proTradeStructureDraft);
}
