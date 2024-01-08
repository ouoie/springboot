package com.greathealth.greathealth.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MapVo {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JsonProperty("rows")
        private List<RowsDTO> rows;

        @NoArgsConstructor
        @Data
        public static class RowsDTO {
            @JsonProperty("elements")
            private List<ElementsDTO> elements;

            @NoArgsConstructor
            @Data
            public static class ElementsDTO {
                @JsonProperty("distance")
                private Integer distance;
                @JsonProperty("duration")
                private Integer duration;
            }
        }
    }
}
