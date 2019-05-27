package com.weshareholdings.approval.util;

import com.weshareholdings.approval.po.SysSeq;
import com.weshareholdings.approval.repository.SysSeqMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author liling
 * @Date 2018/11/2
 **/
@Service
public class SeqUtil {
    private static Logger logger = LoggerFactory.getLogger(SeqUtil.class);
    @Autowired
    private SysSeqMapper sysSeqMapper;

    // 文件序列名
    public static final String FILE = "FILE";

    /**
     * 获取序列号
     *
     * @return
     */
    public synchronized String getSeqValue(String seqName) {
        String result = sysSeqMapper.getSeqValue(seqName);
        if (StringUtils.isBlank(result)) {
            logger.info("未查询到序列名：" + seqName + ",新增数据。。。");
            SysSeq sysSeq = new SysSeq();
            sysSeq.setSeqName(seqName);
            sysSeq.setSeqValue(0);
            sysSeqMapper.insert(sysSeq);
            result = "0";
        } else {
            sysSeqMapper.updateSeqValue(seqName, Integer.parseInt(result));
        }
        return result;
    }
}
