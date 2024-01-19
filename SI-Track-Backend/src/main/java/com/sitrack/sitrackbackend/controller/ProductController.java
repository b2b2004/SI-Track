package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.ProductRequest;
import com.sitrack.sitrackbackend.dto.request.ProductUpdateRequest;
import com.sitrack.sitrackbackend.dto.response.ProductOne;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.dto.response.ProductUpdateResponse;
import com.sitrack.sitrackbackend.service.PaginationService;
import com.sitrack.sitrackbackend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
@Api(tags = "Product Controller")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final PaginationService paginationService;

    @ApiOperation(value = "상품 등록", notes = "상품정보 및 다중 이미지 등록")
    @PostMapping("/admin/register")
    public ResponseEntity<?> register_product(@Valid @RequestPart(value = "productRequest") ProductRequest productRequest,
                                              @RequestPart(value = "productImages") List<MultipartFile> productImages,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.register_product(productRequest, principalDetails.getUser(), productImages);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 수정 페이지 정보 제공", notes = "해당 상품 정보 제공")
    @GetMapping("/admin/update/{productId}")
    public ResponseEntity<?> update_form(@PathVariable Long productId){
        ProductUpdateResponse product = productService.findbyId_UpdateProduct_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 수정", notes = "상품정보 및 다중 이미지 수정")
    @PostMapping("/admin/update/{productId}")
    public ResponseEntity<?> update_product(@Valid @RequestPart(value = "productUpdateRequest") ProductUpdateRequest productUpdateRequest,
                                            @RequestPart(value = "productImages") List<MultipartFile> productImages,
                                            @PathVariable Long productId,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = productService.update_product(productId, productUpdateRequest, user, productImages);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 삭제", notes = "상품 삭제")
    @DeleteMapping("/admin/{productId}")
    public ResponseEntity<?> delete_product(@PathVariable Long productId,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.delete_product(productId, principalDetails.getUser());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 리스트 조회(검색 및 페이징)", notes = "searchValue가 없을 시 전체 상품 조회")
    @GetMapping("/list")
    public ResponseEntity<?> find_all_and_search(@PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) String searchType,
                                                 @RequestParam(required = false) String searchValue){
        Map<String, Object> result = new HashMap<>();
        Page<ProductResponse> products = productService.findbyId_product_all_and_search(pageable, searchType, searchValue);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), products.getTotalPages());
        result.put("products" , products);
        result.put("barNumbers", barNumbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "해당 상품 조회", notes = "해당하는 상품 조회")
    @GetMapping("{productId}")
    public ResponseEntity<?> find_one(@PathVariable Long productId){
        ProductOne product = productService.findbyId_product_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
