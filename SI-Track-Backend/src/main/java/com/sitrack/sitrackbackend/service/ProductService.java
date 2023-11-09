package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;
    private final ImageService imageService;

    public String register_product(ProductDto productDto, List<MultipartFile> images){
        try {
            UserAccount user = userAccountRepository.findByUserId(productDto.userAccountdto().userId())
                    .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + productDto.userAccountdto().userId()));

            if(user.getRoleType() != RoleType.ADMIN && user.getRoleType() != RoleType.MANAGER) {
                return "권한이 없습니다.";
            }

            Product product = productDto.toEntity(user);

            List<ProductImageDto> productImageDtos = imageService.parseImageFile(images);
            List<ProductImage> productImages = new ArrayList<>();

            for(ProductImageDto image : productImageDtos){
                ProductImage productImage = image.toEntity(product, image);
                productImages.add(productImage);
            }
            product.addproductImages(productImages);

            productRepository.save(product);
            imageService.save(product, productImageDtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "상품 등록 완료";
    }

    public String update_product(Long productId, ProductDto dto, List<MultipartFile> productImages){
        try {
            Product product = productRepository.getReferenceById(productId);
            UserAccount user = userAccountRepository.findByUserId(dto.userAccountdto().userId())
                    .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + dto.userAccountdto().userId()));

            // ADMIN, MANAGER은 상품 수정 가능
            if(user.getRoleType() == RoleType.ADMIN || user.getRoleType() == RoleType.MANAGER){
                if(dto.categoryId() != null){
                    product.setCategoryId(dto.categoryId());
                }
                if(dto.supplierCode() != null){
                    product.setSupplierCode(dto.supplierCode());
                }
                if(dto.productName() != null){
                    product.setProductName(dto.productName());
                }
                if(dto.productDetail() != null){
                    product.setProductDetail(dto.productDetail());
                }

                imageService.delete_By_product_id(productId);
                productRepository.save(product);

                List<ProductImageDto> productImageDtos = imageService.parseImageFile(productImages);
                imageService.save(product, productImageDtos);
            }else{
                return "권한이 없습니다.";
            }
        }catch (EntityNotFoundException e){
            log.info("수정에 필요한 정보가 없습니다. - {}",  e.getLocalizedMessage());
        }catch (IOException e) {
            log.info("이미지 오류 - {}", e.getLocalizedMessage());
        }

        return "상품 업데이트 완료";
    }

    public String delete_product(long prouductId, UserAccount userAccount){
        UserAccount user = userAccountRepository.findByUserId(userAccount.getUserId())
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + userAccount.getUserId()));

        if(user.getRoleType() == RoleType.ADMIN || user.getRoleType() == RoleType.MANAGER){
            productRepository.deleteById(prouductId);
            productRepository.flush();
            return "상품 삭제 완료";
        }else{
            return "권한이 없습니다.";
        }
    }

    public ProductResponse findbyId_product_one(Long productId){
        ProductDto productDto = productRepository.findById(productId)
                                            .map(ProductDto::from)
                                            .orElseThrow(()-> new EntityNotFoundException("상품이 없습니다 - productId: " + productId));
        return ProductResponse.from(productDto);
    }

    public Page<ProductDto> findbyId_product_all_and_search(Pageable pageable, String searchValue){

        // 검색어가 없을때 처리
        if(searchValue == null || searchValue.isBlank()){
            return productRepository.findAll(pageable).map(ProductDto::from);
        }

        // 검색어가 있을 경우
        return productRepository.findByproductNameContaining(searchValue, pageable).map(ProductDto::from);
    }
}
