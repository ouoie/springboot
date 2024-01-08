package com.greathealth.greathealth.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AjaxPageResult<T> {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("返回内容")
    private String msg;

    @ApiModelProperty("数据")
    private List<T> rows;

    @ApiModelProperty("总数")
    private Long total;
}
