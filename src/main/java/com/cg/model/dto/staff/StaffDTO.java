package com.cg.model.dto.staff;

import com.cg.model.LocationRegion;
import com.cg.model.Order;
import com.cg.model.StaffAvatar;
import com.cg.model.User;
import com.cg.model.dto.locationRegion.LocationRegionResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffDTO {
    private Long id;
    private String title;
    private String phone;
    private LocationRegionResDTO locationRegion;
    private StaffAvatar staffAvatar;
    private User user;

    public StaffDTO(Long id, String title, String phone, LocationRegion locationRegion, StaffAvatar staffAvatar, User user) {
        this.id = id;
        this.title = title;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionResDTO();
        this.staffAvatar = staffAvatar;
        this.user = user;
    }
}
