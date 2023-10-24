package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.repository.ProductImageRepository;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Subnail;
import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Thumbnail;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ProductImageRepository productImageRepository;

    public List<ProductImageDto> parseImageFile(List<MultipartFile> multipartFiles) throws IOException {
        List<ProductImageDto> productImageDtos= new ArrayList<>();
        String imgSavePath = ""; // 미정
        ProductImageType productImageType = Subnail;

        int count = 0;
        for(MultipartFile img: multipartFiles) {
            if(!img.isEmpty()) {
                String[] contentTypes = img.getContentType().split("/");
                if(contentTypes[0].equals("image")) {
                    String fileName = "product_" + System.currentTimeMillis() + "_" + ((int)(Math.random()*10000)) + "."+contentTypes[1];
                    Path path = Paths.get(imgSavePath+"/"+fileName);
                    img.transferTo(path);

                    if(count == 0){
                        productImageType = Thumbnail;
                    }

                    ProductImageDto productImageDto = ProductImageDto.of(img.getOriginalFilename(), fileName, imgSavePath, productImageType);
                    productImageDtos.add(productImageDto);
                    count++;
                }
            }
        }
        return productImageDtos;
    }

    public void save(Product product, List<ProductImageDto> images){
        for(ProductImageDto image : images){
            ProductImage productImage = image.toEntity(product, image);
            productImageRepository.save(productImage);
        }
    }
}