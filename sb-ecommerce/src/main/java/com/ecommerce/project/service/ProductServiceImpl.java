package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(CategoryService categoryService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO addProductToCategory(ProductDTO productDTO, Long categoryId) {
        Category category = findCategoryById(categoryId);

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    private @NonNull Category findCategoryById(Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return category;
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = productList
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);

        List<Product> productList = productRepository.findByCategory(category);
        List<ProductDTO> productDTOList = productList
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword) {
        List<Product> productList = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<ProductDTO> productDTOList = productList
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productDb = findProductById(productId);

        productDb.setProductName(productDTO.getProductName());
        productDb.setDescription(productDTO.getDescription());
        productDb.setPrice(productDTO.getPrice());
        productDb.setQuantity(productDTO.getQuantity());
        productDb.setDiscount(productDTO.getDiscount());
        productDb.setSpecialPrice(productDb.getPrice() - (productDb.getPrice() * productDb.getDiscount() / 100));

        Product updatedProduct = productRepository.save(productDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private @NonNull Product findProductById(Long productId) {
        Product productDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        return productDb;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productDb = findProductById(productId);

        productRepository.deleteById(productId);
        return modelMapper.map(productDb, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile file) throws IOException {
        // get the product from db
        Product productFromDb = findProductById(productId);
        // upload the file to folder and get the file name
        String path = "images/products/";
        String fileName = uploadProductImage(path,file);
        // set the file name to product
        productFromDb.setImage(fileName);
        // update the product in db
        Product updatedProduct = productRepository.save(productFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private String uploadProductImage(String path, MultipartFile file) throws IOException {
        // File names of the current/ original file
        String originalFileName = file.getOriginalFilename();
        // generate a random file name
        String randomFileName = UUID.randomUUID().toString();
        String fileName = null;
        if (originalFileName != null) {
            fileName = randomFileName.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        }
        String fullPath = path + File.separator + fileName;
        // Check if the directory exists or not
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        // upload the file to the folder
        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return fileName;
    }
}
