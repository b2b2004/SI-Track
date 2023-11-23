package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Category;

public record CategoryDto(
        Long categoryId,
        String categoryName
) {

    public static CategoryDto of(Long categoryId, String categoryName){
        return new CategoryDto(categoryId, categoryName);
    }

    public static CategoryDto from(Category entity){
       return new CategoryDto(
               entity.getId(),
               entity.getCategoryName()
       );
    }

    public Category toEntity(){
        return Category.of(
                categoryId,
                categoryName
        );
    }
}
