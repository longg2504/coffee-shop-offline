package com.cg.model;

import com.cg.model.dto.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.dto.locationRegion.LocationRegionCreResDTO;
import com.cg.model.dto.locationRegion.LocationRegionResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "location_region")
public class LocationRegion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_id", nullable = false)
    private String provinceId;

    @Column(name = "province_name", nullable = false)
    private String provinceName;

    @Column(name = "district_id", nullable = false)
    private String districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @Column(name = "ward_id", nullable = false)
    private String wardId;

    @Column(name = "ward_name", nullable = false)
    private String wardName;

    private String address;
    public LocationRegionResDTO toLocationRegionResDTO(){
        return new LocationRegionResDTO()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);
    }
//
//    public LocationRegionCreResDTO toLocationRegionCreResDTO(){
//        return new LocationRegionCreResDTO()
//                .setProvinceName(provinceName)
//                .setDistrictName(districtName)
//                .setWardName(wardName)
//                .setAddress(address)
//                ;
//    }
//
//    public LocationRegionCreReqDTO toLocationRegion() {
//        return new LocationRegionCreReqDTO()
//                .setProvinceId(provinceId)
//                .setProvinceName(provinceName)
//                .setDistrictId(districtId)
//                .setDistrictName(districtName)
//                .setWardId(wardId)
//                .setWardName(wardName)
//                .setAddress(address);
//
//    }
}

