package com.weshareholdings.approval.vo;

import lombok.Data;

import java.util.List;

@Data
public class NodesDetail {

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * 参与者
     */
    private List<String> actors;

    /**
     * 上一节点
     */
    private String previousNode;

    /**
     * 下一节点
     */
    private String nextNode;

    /**
     * 下一节点
     */
    private String isComplete;

}
