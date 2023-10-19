package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> register_product(@RequestBody ProductDto productDto){
        String msg = productService.register_product(productDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<?> update_product(@RequestBody ProductDto productDto, @PathVariable Long productId){
        String msg = productService.update_product(productId,productDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> delete_product(@PathVariable Long productId, String userId){
        String msg = productService.delete_product(productId, userId); // userId 추후 SpringSecurity로 수정하겠음.
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> find_all(){
        List<Product> products = productService.findbyId_product_all();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> find_all(@PathVariable Long productId){
        Optional<Product> product = productService.findbyId_product_one(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
