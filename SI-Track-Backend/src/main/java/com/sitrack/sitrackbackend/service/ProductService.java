package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

        try {
            String userIdCk = productRepository.getReferenceById(productId).getUserAccount().getUserId();
            if(userIdCk.equals(dto.userAccountdto().userId())){
                Product product = dto.toEntity(dto.userAccountdto().toEntity());
                productRepository.save(product);
            }else{
                return "권한이 없습니다.";
            }

        }catch (EntityNotFoundException e){
            System.out.println("수정에 필요한 정보가 없습니다.");
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

    public Optional<Product> findbyId_product_one(Long productId){
        return productRepository.findById(productId);
    }

    public List<Product> findbyId_product_all(){
        return productRepository.findAll();
    }
}
