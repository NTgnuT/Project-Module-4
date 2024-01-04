package com.ra.model.service.image;

import com.ra.model.entity.Image;
import com.ra.model.service.IGenericService;

import java.util.List;

public interface ImageService extends IGenericService<Image, Integer> {
    List<Image> findListImage (Integer id);
    int saveImage(Image image);
    int updateImage(Image image);
    void deleteImage(Integer id);

}
