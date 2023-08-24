package com.cg.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderUpChangeToTableReqDTO {
    private Long tableIdEmpty;
    private Long tableIdBusy;
}
