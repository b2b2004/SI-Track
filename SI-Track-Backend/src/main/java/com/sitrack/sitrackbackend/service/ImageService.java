package com.sitrack.sitrackbackend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Subnail;
import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Thumbnail;

@RequiredArgsConstructor
@Service
public class ImageService {

    // AWS S3 저장으로 변경 예정
    private final ProductImageRepository productImageRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * @param multipartFiles
     * @return
     * @throws IOException
     * local에 image 저장
     */
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

    /**
     * @param multipartFiles
     * @return
     * @throws IOException
     * aws S3 저장 방식
     */
    public List<ProductImageDto> awsS3ImageSave(List<MultipartFile> multipartFiles) throws IOException {
        List<ProductImageDto> productImageDtos = new ArrayList<>();

        ProductImageType productImageType = Subnail;

        int count = 0;
        for(MultipartFile img: multipartFiles) {
            if(!img.isEmpty()) {
                String[] contentTypes = img.getContentType().split("/");
                if(contentTypes[0].equals("image")) {

                    String fileName = "product_" + System.currentTimeMillis() + "_" + ((int)(Math.random()*10000)) + "." + contentTypes[1];
                    if(count == 0){
                        productImageType = Thumbnail;
                    }else{
                        productImageType = Subnail;
                    }

                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(img.getSize());
                    metadata.setContentType(img.getContentType());
                    amazonS3.putObject(bucket, fileName, img.getInputStream(), metadata);

                    // path 설정
                    String imgSavePath = amazonS3.getUrl(bucket, fileName).toString();
                    String path = imgSavePath.substring(0, imgSavePath.length() - fileName.length());

                    ProductImageDto productImageDto = ProductImageDto.of(img.getOriginalFilename(), fileName, path, productImageType);
                    productImageDtos.add(productImageDto);
                    count++;
                }
            }
        }
        return productImageDtos;
    }

//    public void delete_By_product_id(Long productId){
//        productImageRepository.deleteByProductId(productId);
//    }

    public void delete_By_product_id_awsS3(Long productId){
        List<ProductImageDto> productImageDtos = productImageRepository.findByProductId(productId)
                .stream().map(ProductImageDto::from)
                .collect(Collectors.toList());

        for(ProductImageDto image : productImageDtos){
            amazonS3.deleteObject(bucket, image.saveName());
        }
        productImageRepository.deleteByProductId(productId);
    }
}
