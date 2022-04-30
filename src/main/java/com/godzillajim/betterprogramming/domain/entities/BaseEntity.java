package com.godzillajim.betterprogramming.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Version
    private Integer version;
    @CreatedDate
    @CreationTimestamp
    private Date createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    @UpdateTimestamp
    private Date lastModified;
    @LastModifiedBy
    private String lastModifiedBy;
}
