package com.godzillajim.betterprogramming.domain.entities.blog;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
public class Blog extends BaseEntity {
    private String title;
    private String content;
    private String summary;
    private String prompt;
    private boolean published;
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "blog")
    @ToString.Exclude
    private Set<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Blog blog = (Blog) o;
        return Objects.equals(getId(), blog.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
