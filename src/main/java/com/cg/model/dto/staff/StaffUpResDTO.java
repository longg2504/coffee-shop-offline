package com.cg.model.dto.staff;

import com.cg.model.StaffAvatar;
import com.cg.model.dto.locationRegion.LocationRegionUpResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffUpResDTO {
    private Long id;
    private String title;
    private String phone;
    private LocationRegionUpResDTO locationRegion;
    private StaffAvatar staffAvatar;
}
