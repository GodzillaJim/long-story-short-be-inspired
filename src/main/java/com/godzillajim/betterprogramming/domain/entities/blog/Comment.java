package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Comment extends BaseEntity {
    String content;
    String firstName;
    String lastName;
    @CreationTimestamp
    Date timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ToString.Exclude
    Blog blog;
}
