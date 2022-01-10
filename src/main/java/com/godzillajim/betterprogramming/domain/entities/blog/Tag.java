package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(unique = true)
    String tagName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blod_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Blog blog;
}
