package com.godzillajim.betterprogramming.domain.entities.users;

import com.godzillajim.betterprogramming.domain.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ERole roleName;
}
