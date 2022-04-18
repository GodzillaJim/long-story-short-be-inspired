package com.godzillajim.betterprogramming.domain.payloads;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomepageResponse {
    private List<Category> latestCategories;
    private List<Tag> latestTags;
    private List<Blog> latestBlogs;
    private List<Blog> popularBlogs;
}
