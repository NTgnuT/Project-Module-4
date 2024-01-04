package com.ra.model.service.image;

import com.ra.model.dao.image.ImageDAO;
import com.ra.model.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageServiceIMPL implements ImageService{
    @Autowired
    ImageDAO imageDAO;
    @Override
    public List<Image> findAll() {
        return imageDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(Image image) {
        return imageDAO.saveOrUpdate(image);
    }

    @Override
    public Image findById(Integer integer) {
        return imageDAO.findById(integer);
    }

    @Override
    public List<Image> findByName(String name) {
        return imageDAO.findByName(name);
    }

    @Override
    public List<Image> pagination(Integer limit, Integer currentPage) {
        return null;
    }

    @Override
    public List<Image> findListImage(Integer id) {
        return imageDAO.findListImage(id);
    }

    @Override
    public int saveImage(Image image) {
        return imageDAO.saveImage(image);
    }

    @Override
    public int updateImage(Image image) {
        return imageDAO.updateImage(image);
    }

    @Override
    public void deleteImage(Integer id) {
        imageDAO.deleteImage(id);
    }
}
