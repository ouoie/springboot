package com.greathealth.greathealth.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单id响应类")
public class IdVo {
    /**
     * 订单主键id
     */
    @ApiModelProperty("订单主键id")
    private String id;
}
