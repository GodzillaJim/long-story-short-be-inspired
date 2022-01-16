package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "tags")
public class Tag extends BaseEntity {
    @Column(name = "owner")
    String user;
    @Column(unique = true)
    String tagName;
}
