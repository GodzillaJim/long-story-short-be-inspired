package com.godzillajim.betterprogramming.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BlogBody {
    @NotNull
    @NotEmpty
    @NotBlank
    String title;
    @NotEmpty
    String content;
    String summary;
    String prompt;
    Set<TagBody> tags;
}
