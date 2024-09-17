package in.nic.sb_ecom.service;

import in.nic.sb_ecom.model.Product;
import in.nic.sb_ecom.payload.ProductDTO;
import in.nic.sb_ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, Product product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;
}
