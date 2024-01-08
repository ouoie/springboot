package com.greathealth.greathealth.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class IdDto {

    @ApiModelProperty("唯一id")
    private String id;
}
