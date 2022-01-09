package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Tag extends BaseEntity {
    @Column(name = "owner")
    String user;
    String tagName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    Blog blog;
}
