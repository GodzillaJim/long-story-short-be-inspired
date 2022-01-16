package com.godzillajim.betterprogramming.domain.mappers;

import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.payloads.CategoryRequest;

public class CategoryMapper {
    public static Category mapCategoryRequestToCategory(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }
    public static CategoryBody mapCategoryToCategoryBody(Category category){
        CategoryBody categoryBody = new CategoryBody();
        categoryBody.setArchived(category.getArchived());
        categoryBody.setCreatedOn(category.getCreatedDate());
        categoryBody.setId(category.getId());
        return categoryBody;
    }
}
