package com.lcx.cms.entity.sys.vo;

import lombok.Data;

import java.util.List;

@Data
public class LayTreeMenuVO {

    private List<LayTreeMenuVO> children;

    private String title;

    private String id;

    private boolean checked;

}
