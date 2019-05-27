package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.ProAttachTask;
import com.weshareholdings.approval.vo.AttachVo;
import com.weshareholdings.approval.vo.FileApprovalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProAttachTaskMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProAttachTask record);

    int insertSelective(ProAttachTask record);

    ProAttachTask selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProAttachTask record);

    int updateByPrimaryKey(ProAttachTask record);

    /**
     *  查询提交审核材料的项目列表
     * @param proName 项目名称
     * @param baseProTypes 可展示项目类型
     * @param proType 筛选项目类型
     * @return
     */
    List<FileApprovalVo> selectProsForFileApproval(@Param("proName") String proName, @Param("baseProTypes") List<String> baseProTypes, @Param("proType") String proType);
    List<FileApprovalVo> selectProsForFileApprovalByUser(@Param("proName") String proName, @Param("baseProTypes") List<String> baseProTypes, @Param("proType") String proType, @Param("userId") String userId);


    /**
     * 根据项目id查询附件审批详情
     * @param proId
     * @return
     */
    List<AttachVo> selectFileWithTask(@Param("proId") String proId);

    /**
     * 查询附件任务
     * @param proId
     * @param attachId
     * @param status
     * @return
     */
    List<ProAttachTask> selectAttachTask(@Param("proId") String proId, @Param("attachId") String attachId, @Param("status") String status);



}