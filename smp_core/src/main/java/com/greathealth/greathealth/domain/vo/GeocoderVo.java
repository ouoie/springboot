package com.greathealth.greathealth.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GeocoderVo {
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
        @JsonProperty("location")
        private LocationDTO location;
        @JsonProperty("address")
        private String address;
        @JsonProperty("formatted_addresses")
        private FormattedAddressesDTO formattedAddresses;
        @JsonProperty("address_component")
        private AddressComponentDTO addressComponent;
        @JsonProperty("ad_info")
        private AdInfoDTO adInfo;
        @JsonProperty("address_reference")
        private AddressReferenceDTO addressReference;
        @JsonProperty("poi_count")
        private Integer poiCount;
        @JsonProperty("pois")
        private List<PoisDTO> pois;

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
        public static class FormattedAddressesDTO {
            @JsonProperty("recommend")
            private String recommend;
            @JsonProperty("rough")
            private String rough;
        }

        @NoArgsConstructor
        @Data
        public static class AddressComponentDTO {
            @JsonProperty("nation")
            private String nation;
            @JsonProperty("province")
            private String province;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;
            @JsonProperty("street")
            private String street;
            @JsonProperty("street_number")
            private String streetNumber;
        }

        @NoArgsConstructor
        @Data
        public static class AdInfoDTO {
            @JsonProperty("nation_code")
            private String nationCode;
            @JsonProperty("adcode")
            private String adcode;
            @JsonProperty("city_code")
            private String cityCode;
            @JsonProperty("name")
            private String name;
            @JsonProperty("location")
            private LocationDTO location;
            @JsonProperty("nation")
            private String nation;
            @JsonProperty("province")
            private String province;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;

            @NoArgsConstructor
            @Data
            public static class LocationDTO {
                @JsonProperty("lat")
                private Double lat;
                @JsonProperty("lng")
                private Double lng;
            }
        }

        @NoArgsConstructor
        @Data
        public static class AddressReferenceDTO {
            @JsonProperty("business_area")
            private BusinessAreaDTO businessArea;
            @JsonProperty("famous_area")
            private FamousAreaDTO famousArea;
            @JsonProperty("crossroad")
            private CrossroadDTO crossroad;
            @JsonProperty("town")
            private TownDTO town;
            @JsonProperty("street_number")
            private StreetNumberDTO streetNumber;
            @JsonProperty("street")
            private StreetDTO street;
            @JsonProperty("landmark_l2")
            private LandmarkL2DTO landmarkL2;

            @NoArgsConstructor
            @Data
            public static class BusinessAreaDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Integer distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class FamousAreaDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Integer distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class CrossroadDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Double distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class TownDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Integer distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class StreetNumberDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Double distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class StreetDTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Double distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class LandmarkL2DTO {
                @JsonProperty("id")
                private String id;
                @JsonProperty("title")
                private String title;
                @JsonProperty("location")
                private LocationDTO location;
                @JsonProperty("_distance")
                private Integer distance;
                @JsonProperty("_dir_desc")
                private String dirDesc;

                @NoArgsConstructor
                @Data
                public static class LocationDTO {
                    @JsonProperty("lat")
                    private Double lat;
                    @JsonProperty("lng")
                    private Double lng;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class PoisDTO {
            @JsonProperty("id")
            private String id;
            @JsonProperty("title")
            private String title;
            @JsonProperty("address")
            private String address;
            @JsonProperty("category")
            private String category;
            @JsonProperty("location")
            private LocationDTO location;
            @JsonProperty("ad_info")
            private AdInfoDTO adInfo;
            @JsonProperty("_distance")
            private Integer distance;
            @JsonProperty("_dir_desc")
            private String dirDesc;

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
                private String adcode;
                @JsonProperty("province")
                private String province;
                @JsonProperty("city")
                private String city;
                @JsonProperty("district")
                private String district;
            }
        }
    }
}
