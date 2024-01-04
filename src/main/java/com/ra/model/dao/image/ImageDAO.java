package com.ra.model.dao.image;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.Image;

import java.util.List;

public interface ImageDAO extends IGenericDAO<Image, Integer> {
    List<Image> findListImage (Integer id);
    int saveImage (Image image);
    int updateImage(Image image);
    void deleteImage(Integer id);
}
