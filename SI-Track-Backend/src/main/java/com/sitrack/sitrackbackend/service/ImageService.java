package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // AWS S3 저장으로 변경 예정
    private final ProductImageRepository productImageRepository;

    public List<ProductImageDto> parseImageFile(List<MultipartFile> multipartFiles) throws IOException {
        List<ProductImageDto> productImageDtos = new ArrayList<>();
        String imgSavePath = "C:\\workspace\\springbootwork\\SI-Track\\Si-Track-Frontend\\public\\img";
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
                    }else{
                        productImageType = Subnail;
                    }

                    ProductImageDto productImageDto = ProductImageDto.of(img.getOriginalFilename(), fileName, imgSavePath, productImageType);
                    productImageDtos.add(productImageDto);
                    count++;
                }
            }
        }
        return productImageDtos;
    }

    public void delete_By_product_id(Long productId){
        productImageRepository.deleteByProductId(productId);
    }
}
