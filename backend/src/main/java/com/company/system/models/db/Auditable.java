package com.company.system.models.db;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class Auditable {

    @CreatedDate
    private LocalDateTime created;

    @CreatedBy
    @Column(name = "createdBy")
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updated;

    @LastModifiedBy
    @Column(name = "updatedBy")
    private String updatedBy;

    private LocalDateTime deleted;

    @Column(name = "deletedBy")
    private String deletedBy;

}
