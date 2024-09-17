package in.nic.sb_ecom.service.impl;

import in.nic.sb_ecom.exceptions.ApiException;
import in.nic.sb_ecom.exceptions.ResourceNotFoundException;
import in.nic.sb_ecom.model.Category;
import in.nic.sb_ecom.model.Product;
import in.nic.sb_ecom.payload.ProductDTO;
import in.nic.sb_ecom.payload.ProductResponse;
import in.nic.sb_ecom.repositories.CategoryRepository;
import in.nic.sb_ecom.repositories.ProductRepository;
import in.nic.sb_ecom.service.FileService;
import in.nic.sb_ecom.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("{project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductName().equals(product.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if(isProductNotPresent) {
            product.setCategory(category);
            product.setImage("default");
            double specialPrice = (1 - (product.getDiscount() / 100)) * product.getPrice();
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);

            return modelMapper.map(product, ProductDTO.class);
        } else {
            throw new ApiException("Product already exist!!");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equals("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        if(products.isEmpty()) {
            throw new ApiException("Product list is empty!!");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setSpecialPrice(product.getSpecialPrice());
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String fileName = fileService.uploadImage(path, image);
        existingProduct.setImage(fileName);

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }


}
