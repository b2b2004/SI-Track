package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public String register_product(ProductDto productDto){
        System.out.println(productDto);
        UserAccount userAccount = userAccountRepository.findByUserId(productDto.userAccountdto().userId());
        Product product = productDto.toEntity(userAccount);
        productRepository.save(product);
        return "상품 등록 완료";
    }

    public String update_product(Long productId, ProductDto dto){
        // productId --> 기존 데이터베이스 코드
        // dto --> 업데이트 dto
        // 오류 메시지가 있으면 Response로 보내주고 싶음
        // 메시지를 쌓아서 만들면 되지 않을까? 어떻게 쌓아 줄까
        try {
            Product product = productRepository.getReferenceById(productId);
            String userIdCk = product.getUserAccount().getUserId();

            if(userIdCk.equals(dto.userAccountdto().userId())){
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

                productRepository.save(product);
            }else{
                return "권한이 없습니다.";
                // 상품 등록자와 상품 업데이트자가 다를때
                // 추후 권한 관련 코드로 업데이트 하겠음
            }

        }catch (EntityNotFoundException e){
            log.info("수정에 필요한 정보가 없습니다. - {}",  e.getLocalizedMessage());
        }

        return "상품 업데이트 완료";
    }

    public String delete_product(long prouductId, String userId){

        Product product = productRepository.getReferenceById(prouductId);

        if(product.getUserAccount().getUserId().equals(userId)){
            productRepository.deleteById(prouductId);
            productRepository.flush();
            return "상품 삭제 완료";
        }else{
            return "등록자와 같지 않습니다.";
        }
    }

    public ProductResponse findbyId_product_one(Long productId){
        ProductDto productDto = productRepository.findById(productId)
                                            .map(ProductDto::from)
                                            .orElseThrow(()-> new EntityNotFoundException("게시글이 없습니다 - productId: " + productId));
        return ProductResponse.from(productDto);
    }

    public List<ProductResponse> findbyId_product_all(){
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        for(Product product : products){
            ProductResponse productResponse = ProductResponse.from(ProductDto.from(product));
            productResponses.add(productResponse);
        }

        return productResponses;
    }
}
