package com.example.ecommerce.service;

import com.example.ecommerce.dto.BrandInfo;
import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.BrandRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.BrandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Brand> getListBrand() {
        return brandRepository.findAll();
    }

    public List<BrandInfo> getListBrandAndProductCount() {
        return brandRepository.getListBrandAndProductCount();
    }

    public Brand createBrand(BrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setThumbnail(request.getThumbnail());

        brandRepository.save(brand);

        return brand;
    }

    public void updateBrand(int id, BrandRequest request) {

        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }

        Brand brand = brandOptional.get();
        brand.setName(request.getName());
        brand.setThumbnail(request.getThumbnail());

        try {
            brandRepository.save(brand);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi chỉnh sửa nhãn hiệu");
        }
    }

    public void deleteBrand(int id) {
        Optional<Brand> rs = brandRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }

        int count = productRepository.countByBrandId(id);
        if (count > 0) {
            throw new BadRequestException("Có sản phẩm thuộc nhãn hiệu không thể xóa");
        }

        Brand brand = rs.get();

        try {
            brandRepository.delete(brand);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa nhãn hiệu");
        }
    }
}
