package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${product.image.upload.dir}")
    private String path;

    public ProductServiceImpl(CategoryService categoryService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO addProductToCategory(ProductDTO productDTO, Long categoryId) {
        Category category = findCategoryById(categoryId);

        Product product = modelMapper.map(productDTO, Product.class);
        Product productWithSameName = productRepository.findByProductNameAndCategory(product.getProductName(), category);
        existProductByName(productWithSameName, product);
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
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productsPage = productRepository.findAll(pageable);
        List<ProductDTO> productDTOList = productsPage.getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if(productDTOList.isEmpty()) throw new APIException("No Products Present!");
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        productResponse.setPageNumber(productsPage.getNumber());
        productResponse.setPageSize(productsPage.getSize());
        productResponse.setTotalElements(productsPage.getTotalElements());
        productResponse.setTotalPages(productsPage.getTotalPages());
        productResponse.setLastPage(productsPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = findCategoryById(categoryId);
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productsPage = productRepository.findByCategory(category, pageable);
        List<ProductDTO> productDTOList = productsPage.getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if (productDTOList.isEmpty()) throw new APIException("No Products Present in Category : " + category.getCategoryName());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        productResponse.setPageNumber(productsPage.getNumber());
        productResponse.setPageSize(productsPage.getSize());
        productResponse.setTotalElements(productsPage.getTotalElements());
        productResponse.setTotalPages(productsPage.getTotalPages());
        productResponse.setLastPage(productsPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productsPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%', pageable);
        List<ProductDTO> productDTOList = productsPage.getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if (productDTOList.isEmpty()) throw new APIException("No Products Present with keyword : " + keyword);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        productResponse.setPageNumber(productsPage.getNumber());
        productResponse.setPageSize(productsPage.getSize());
        productResponse.setTotalElements(productsPage.getTotalElements());
        productResponse.setTotalPages(productsPage.getTotalPages());
        productResponse.setLastPage(productsPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productDb = findProductById(productId);
        Product productWithSameName = productRepository.findByProductNameAndCategory(productDTO.getProductName(), productDb.getCategory());
        existProductByName(productWithSameName, productDb);

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

        String fileName = fileService.uploadProductImage(path,file);
        // set the file name to product
        productFromDb.setImage(fileName);
        // update the product in db
        Product updatedProduct = productRepository.save(productFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    public void existProductByName(Product savedProduct, Product product) {
        if(savedProduct != null) throw new APIException("Product with productName : " + product.getProductName() + " already exists!");
    }


}
