package com.company.system.models.db;

import com.company.system.security.SecurityContextHelper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class PO extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String refId;

    @Column(name = "active")
    private boolean isActive = true;

    @PrePersist
    void onPrePersist() {
        if(isNull(getRefId())) setRefId(UUID.randomUUID().toString());
        if (isNull(getCreated())) setCreated(LocalDateTime.now(ZoneId.systemDefault()));
        if (isNull(getCreatedBy()))
            this.setCreatedBy(Optional.ofNullable(SecurityContextHelper.getGlobalAuthUserIdentity())
                    .orElse("ANONYMOUS"));
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdated(LocalDateTime.now(ZoneId.systemDefault()));
        this.setUpdatedBy(Optional.ofNullable(SecurityContextHelper.getGlobalAuthUserIdentity())
                .orElse("ANONYMOUS"));
    }
}
