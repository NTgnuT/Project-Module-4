package com.ra.model.entity;

public class Image {
    private int imageId;
    private String imageUrl;
    private int productId;

    public Image() {
    }

    public Image(int imageId, String imageUrl, int productId) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
