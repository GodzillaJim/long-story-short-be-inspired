package com.godzillajim.betterprogramming.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CommentBody {
    Long id;
    @NotNull(message = "firstName cannot be null")
    String firstName;
    @NotNull(message = "lastName cannot be null")
    String lastName;
    @NotNull(message = "content cannot be null")
    String content;
    Date createdOn = new Date();
}
