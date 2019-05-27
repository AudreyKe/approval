package com.weshareholdings.approval.po;

import lombok.Data;

@Data
public class MenuAction {

    /**
     * id
     */
    private int id;

    private String code;

    private String name;

    private String ico;

    private String remark;

    private int sort;

    private int permId;

    private int valid;

    private String url;

    private int fid;

    private int type;

    private String createTime;

    private String updateTime;
}
