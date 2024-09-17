package in.nic.sb_ecom.controller;

import in.nic.sb_ecom.model.Product;
import in.nic.sb_ecom.payload.ProductDTO;
import in.nic.sb_ecom.payload.ProductResponse;
import in.nic.sb_ecom.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody Product product,
                                                 @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name="pageNumber", defaultValue = "0") Integer pageNumber,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = "productId") String sortBy,
                                                          @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsById(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{keyword}/products")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody Product product,
                                                    @PathVariable Long productId) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);

    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("Image")MultipartFile image) throws IOException {
        ProductDTO productDTO = productService.updateImage(productId, image);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);

    }
}
