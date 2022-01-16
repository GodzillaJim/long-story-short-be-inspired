package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "categories")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Category extends BaseEntity {
    @Column(name = "category_name")
    private String name;
    private Boolean archived = false;
}
