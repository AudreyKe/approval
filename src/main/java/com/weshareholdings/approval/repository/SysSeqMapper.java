package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.SysSeq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SysSeqMapper {

    String getSeqValue(@Param("seqName") String seqName);

    Integer updateSeqValue(@Param("seqName") String seqName, @Param("seqValue") Integer seqValue);

    void insert(SysSeq sysSeq);

}
