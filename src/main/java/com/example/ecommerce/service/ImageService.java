package com.example.ecommerce.service;

import com.example.ecommerce.entity.Image;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void save(Image img) {
        imageRepository.save(img);
    }

    public List<String> getListImageOfUser(long userId){
        List<String> images = imageRepository.getListImageOfUser(userId);

        return images;
    }

    @Transactional(rollbackFor = InternalServerException.class)
    public void deleteImage(String uploadDir, String filename) {
        String link = "/media/static/" + filename;
        Image img = imageRepository.findByLink(link);
        if (img == null) {
            throw new BadRequestException("File không tồn tại");
        }

        Integer inUse = imageRepository.checkImgInUse(link);
        if (inUse != null) {
            throw new BadRequestException("Ảnh đã được sử dụng không thể xóa");
        }

        imageRepository.delete(img);

        File file = new File(uploadDir + "/" + filename);
        if (file.exists()) {
            if (!file.delete()) {
                throw new InternalServerException("Lỗi khi xóa ảnh");
            }
        }
    }
}
