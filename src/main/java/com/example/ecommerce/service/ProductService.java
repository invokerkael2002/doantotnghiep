package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductInfoDto;
import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.ProductRequest;
import com.example.ecommerce.util.PageUtil;
import com.github.slugify.Slugify;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Random rd;

    public List<Product> getListProductLimit() {
        return productRepository.getListProductLimit(5);
    }

    public PageUtil<Product> getAll(int page, int size) {
        Page<Product> pageInfo = productRepository.findAll(PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    //lấy sản phẩm theo thể loại có phân trang
    public PageUtil<Product> getAllByCategories_Id(int id, int page, int size) {
        Page<Product> pageInfo = productRepository.getAllByCategories_Id(id, PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    public PageUtil<Product> getAllByBrand_Id(int id, int page, int size) {
        Page<Product> pageInfo = productRepository.getAllByBrand_Id(id, PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    //lấy tìm kiếm sản phẩm có phân trang
    public PageUtil<Product> searchProducts(int page, int size, String keyword) {
        Page<Product> pageInfo = productRepository.findProductByNameContaining(keyword, PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }


    //lấy sản phẩm theo giá có phân trang
    public PageUtil<Product> getProductsList1(int page, int size) {
        Page<Product> pageInfo = productRepository.getProductsList1(PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }
    public PageUtil<Product> getProductsList2(int page, int size) {
        Page<Product> pageInfo = productRepository.getProductsList2(PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }
    public PageUtil<Product> getProductsList3(int page, int size) {
        Page<Product> pageInfo = productRepository.getProductsList3(PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }
    public PageUtil<Product> getProductsList4(int page, int size) {
        Page<Product> pageInfo = productRepository.getProductsList4(PageRequest.of(page - 1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    public ProductInfoDto getDetailProductById(long id) {
        // Get product info
        Optional<Product> result = productRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        Product product = result.get();

        if (!product.isAvailable()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        ProductInfoDto productInfoDto = modelMapper.map(product, ProductInfoDto.class);

        return productInfoDto;
    }

    public Long createProduct(ProductRequest request) {
        // Validate info
        if (request.getCategoryIds().size() == 0) {
            throw new BadRequestException("Danh mục trỗng");
        }
        if (request.getProductImages().size() == 0) {
            throw new BadRequestException("Danh sách ảnh trống");
        }

        Product product = modelMapper.map(request, Product.class);
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        product.setTotalSold(0);

        // Gen slug
        Slugify slg = new Slugify();
        product.setSlug(slg.slugify(request.getName()));

        // Set brand
        Brand brand = new Brand();
        brand.setId(request.getBrandId());
        product.setBrand(brand);

        // Set category
        ArrayList<Category> categories = new ArrayList<>();
        for (Integer ids: request.getCategoryIds()) {
            Category category = new Category();
            category.setId(ids);
            categories.add(category);
        }
        product.setCategories(categories);

        // Gen id
        Long productId = rd.nextLong(10000000);
        product.setId(productId);

        try {
            productRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi thêm sản phẩm");
        }

        return productId;
    }

    public Product getProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        return productOptional.get();
    }

    public void updateProduct(long id, ProductRequest request) {

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        if (request.getCategoryIds().size() == 0) {
            throw new BadRequestException("Danh mục trỗng");
        }
        if (request.getProductImages().size() == 0) {
            throw new BadRequestException("Danh sách ảnh trống");
        }

        Product product = ProductMapper.toProduct(request);
        product.setId(id);

        try {
            productRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật thông tin sản phẩm");
        }
    }

    public void deleteProduct(long id) {
        // Check product exist
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        try {
            productRepository.deleteById(id);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new InternalServerException("Lỗi khi xóa sản phẩm");
        }
    }

    public List<Integer> getListAvailableProduct(long id) {
        return productRepository.findAllQuantityOfProduct(id);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.getAvailableProducts();
    }
}
