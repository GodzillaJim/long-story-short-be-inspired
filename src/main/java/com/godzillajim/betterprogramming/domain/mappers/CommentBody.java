package com.godzillajim.betterprogramming.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CommentBody {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String content;
}
