package com.weshareholdings.approval.po;

import lombok.Data;

import java.util.List;

@Data
public class Menu {

    private int id;

    private String name;

    private String ico;

    private int sort;

    private String url;

    private List<MenuChild> child;
}
