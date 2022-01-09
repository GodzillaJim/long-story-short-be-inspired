package com.godzillajim.betterprogramming.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Version
    private Integer version;
    @CreatedDate
    private Date createdDate;
    private String createdBy;
    @LastModifiedDate
    private Date lastModified;
    private String lastModifiedBy;
}
