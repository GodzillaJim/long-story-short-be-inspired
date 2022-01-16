package com.godzillajim.betterprogramming.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBody {
    private Long id;
    private String name;
    private Boolean archived;
    private Date createdOn;
}
