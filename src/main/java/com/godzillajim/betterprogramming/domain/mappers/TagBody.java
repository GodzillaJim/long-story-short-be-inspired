package com.godzillajim.betterprogramming.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagBody {
    @NotNull(message = "tag cannot be null")
    String tag;
    @NotNull(message = "id cannot be null")
    Long id ;
}
