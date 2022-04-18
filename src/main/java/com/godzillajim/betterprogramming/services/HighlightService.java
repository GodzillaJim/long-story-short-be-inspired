package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.payloads.HomepageResponse;
import com.godzillajim.betterprogramming.domain.repositories.ICategoryRepository;
import com.godzillajim.betterprogramming.repositories.BlogRepository;
import com.godzillajim.betterprogramming.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HighlightService {
    final BlogRepository blogRepository;
    final EntityManager entityManager;
    final ICategoryRepository categoryRepository;
    final TagRepository tagRepository;

    public List<Blog> getLatestArticles () {
        return blogRepository.findTop5ByOrderByCreatedDateDesc();
    }

    public List<Blog> getPopularArticles () {
        return blogRepository.findTop5ByOrderByViewsDesc();
    }

    public List<Category> getLatestCategories () {
        return categoryRepository.findAll();
    }

    public List<Tag> getLatestTags () {
        return tagRepository.findTop20ByOrderByCreatedDateDesc();
    }

    public HomepageResponse loadHomePage(){
        try{
            return new HomepageResponse(
                    getLatestCategories(),
                    getLatestTags(),
                    getLatestArticles(),
                    getPopularArticles()
            );
        }catch (Exception e){
            throw new Error(e.getMessage());
        }
    }

}
