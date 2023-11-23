package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.ProductRequest;
import com.sitrack.sitrackbackend.dto.request.ProductUpdateRequest;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.dto.response.ProductUpdateResponse;
import com.sitrack.sitrackbackend.service.PaginationService;
import com.sitrack.sitrackbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;
    private final PaginationService paginationService;

    @PostMapping("/admin/register")
    public ResponseEntity<?> register_product(@RequestPart ProductRequest productRequest,
                                              @RequestPart List<MultipartFile> productImages,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = productService.register_product(productRequest, user, productImages);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/admin/update/{productId}")
    public ResponseEntity<?> update_form(@PathVariable Long productId){
        ProductUpdateResponse product = productService.findbyId_UpdateProduct_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/admin/update/{productId}")
    public ResponseEntity<?> update_product(@RequestPart ProductUpdateRequest productUpdateRequest,
                                            @RequestPart List<MultipartFile> productImages,
                                            @PathVariable Long productId,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = productService.update_product(productId, productUpdateRequest, user, productImages);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{productId}")
    public ResponseEntity<?> delete_product(@PathVariable Long productId,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.delete_product(productId, principalDetails.getUser());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> find_all_and_search(@PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) String searchValue){
        Map<String, Object> result = new HashMap<>();
        Page<ProductResponse> products = productService.findbyId_product_all_and_search(pageable, searchValue).map(ProductResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), products.getTotalPages());
        result.put("products" , products);
        result.put("barNumbers", barNumbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> find_one(@PathVariable Long productId){
        ProductResponse product = productService.findbyId_product_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
