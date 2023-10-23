package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.ProductRequest;
import com.sitrack.sitrackbackend.dto.request.ProductUpdateRequest;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> register_product(@RequestBody ProductRequest productRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.register_product(productRequest.toDto(principalDetails.todto()), productRequest.productImages());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<?> update_product(@RequestBody ProductUpdateRequest productUpdateRequest, @PathVariable Long productId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.update_product(productId, productUpdateRequest.toDto(principalDetails.todto()));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> delete_product(@PathVariable Long productId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String msg = productService.delete_product(productId, principalDetails.getUser().getUserId());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> find_all(){
        List<ProductResponse> products = productService.findbyId_product_all();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> find_one(@PathVariable Long productId){
        ProductResponse product = productService.findbyId_product_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
