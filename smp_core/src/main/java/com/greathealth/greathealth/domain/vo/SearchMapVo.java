package com.greathealth.greathealth.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SearchMapVo {
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("data")
    private List<DataDTO> data;
    @JsonProperty("region")
    private RegionDTO region;

    @NoArgsConstructor
    @Data
    public static class RegionDTO {
        @JsonProperty("title")
        private String title;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("address")
        private String address;
        @JsonProperty("tel")
        private String tel;
        @JsonProperty("category")
        private String category;
        @JsonProperty("type")
        private Integer type;
        @JsonProperty("location")
        private LocationDTO location;
        @JsonProperty("_distance")
        private Double distance;
        @JsonProperty("ad_info")
        private AdInfoDTO adInfo;

        @NoArgsConstructor
        @Data
        public static class LocationDTO {
            @JsonProperty("lat")
            private Double lat;
            @JsonProperty("lng")
            private Double lng;
        }

        @NoArgsConstructor
        @Data
        public static class AdInfoDTO {
            @JsonProperty("adcode")
            private Integer adcode;
            @JsonProperty("province")
            private String province;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;
        }
    }
}
