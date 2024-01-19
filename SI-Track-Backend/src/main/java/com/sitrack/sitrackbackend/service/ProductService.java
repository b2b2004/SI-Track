package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.dto.request.ProductRequest;
import com.sitrack.sitrackbackend.dto.request.ProductUpdateRequest;
import com.sitrack.sitrackbackend.dto.response.ProductOne;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.dto.response.ProductUpdateResponse;
import com.sitrack.sitrackbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final UserAccountRepository userAccountRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ImageService imageService;

    public String register_product(ProductRequest productRequest, UserAccount userAccount, List<MultipartFile> images){
        try {
            UserAccount user = userAccountRepository.findByUserId(userAccount.getUserId())
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

            if(user.getRoleType() != RoleType.ADMIN && user.getRoleType() != RoleType.MANAGER) {
                return "권한이 없습니다.";
            }

            Category category = categoryRepository.findByCategoryName(productRequest.categoryName())
                    .orElseThrow(()-> new CustomException(CATEGORY_NOT_FOUND));
            Supplier supplier = supplierRepository.findBySupplierCode(productRequest.supplierCode())
                    .orElseThrow(()-> new CustomException(SUPPLIER_NOT_FOUND));
            Product product = productRequest.toEntity(user, category, supplier);
            List<ProductImageDto> productImageDtos = imageService.awsS3ImageSave(images);
            List<ProductImage> productImages = new ArrayList<>();

            for(ProductImageDto image : productImageDtos){
                ProductImage productImage = image.toEntity(product, image);
                productImages.add(productImage);
            }

            product.addproductImages(productImages);

            productRepository.save(product);
//            imageService.save(product, productImageDtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "상품 등록 완료";
    }

    //
    public String update_product(Long productId, ProductUpdateRequest dto, UserAccount userAccount, List<MultipartFile> images){
        try {
            Product product = productRepository.getReferenceById(productId);
            UserAccount user = userAccountRepository.findByUserId(userAccount.getUserId())
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

            // ADMIN, MANAGER은 상품 수정 가능
            if(user.getRoleType() == RoleType.ADMIN || user.getRoleType() == RoleType.MANAGER){
                if(dto.categoryName() != null){
                    Category category = categoryRepository.findByCategoryName(dto.categoryName()).orElseThrow();
                    product.setCategory(category);
                }
                if(dto.supplierCode() != null){
                    Supplier supplier = supplierRepository.findBySupplierCode(dto.supplierCode()).orElseThrow();
                    product.setSupplier(supplier);
                }
                if(dto.productName() != null){
                    product.setProductName(dto.productName());
                }
                if(dto.productDetail() != null){
                    product.setProductDetail(dto.productDetail());
                }

                imageService.delete_By_product_id_awsS3(productId);

                List<ProductImageDto> productImageDtos = imageService.awsS3ImageSave(images);
                List<ProductImage> productImages = new ArrayList<>();

                for(ProductImageDto image : productImageDtos){
                    ProductImage productImage = image.toEntity(product, image);
                    productImages.add(productImage);
                }

                product.addproductImages(productImages);
                productRepository.save(product);

                // imageService.save(product, productImageDtos);
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
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(user.getRoleType() == RoleType.ADMIN || user.getRoleType() == RoleType.MANAGER){
            imageService.delete_By_product_id_awsS3(prouductId);
            productRepository.deleteById(prouductId);
            productRepository.flush();
            return "상품 삭제 완료";
        }else{
            return "권한이 없습니다.";
        }
    }

    public ProductOne findbyId_product_one(Long productId){
        ProductDto productDto = productRepository.findById(productId)
                                            .map(ProductDto::from)
                                            .orElseThrow(()-> new CustomException(PRODUCT_NOT_FOUND));
        return ProductOne.from(productDto);
    }

    public ProductUpdateResponse findbyId_UpdateProduct_one(Long productId){
        ProductDto productDto = productRepository.findById(productId)
                .map(ProductDto::from)
                .orElseThrow(()-> new CustomException(PRODUCT_NOT_FOUND));
        return ProductUpdateResponse.from(productDto);
    }

    public Page<ProductResponse> findbyId_product_all_and_search(Pageable pageable,String searchType, String searchValue){
        return productRepositoryCustom.searchMainPageComplex(searchType, searchValue, pageable);
    }

}
