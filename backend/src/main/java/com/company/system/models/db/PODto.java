package com.company.system.models.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PODto {
    private Long id;
    private String refId;
    private boolean isActive;

    public void copy(PO po){
        this.setId(po.getId());
        this.setRefId(po.getRefId());
        this.setActive(po.isActive());
    }
}
