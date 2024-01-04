package com.ra.controller.admin;

import com.ra.model.dto.ProductDTO;
import com.ra.model.entity.Category;
import com.ra.model.entity.Image;
import com.ra.model.entity.Product;
import com.ra.model.service.category.ICategoryService;
import com.ra.model.service.image.ImageService;
import com.ra.model.service.product.IProductService;
import jdk.internal.classfile.impl.BufferedCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/product")
@PropertySource("classpath:config.properties")
public class ProductController {
    @Value("C:\\Module 4\\Project Module4\\src\\main\\webapp\\uploads\\product\\")
    private String pathPro;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    IProductService productService;

    @RequestMapping("")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        List<Category> categories = categoryService.findAll();
        System.out.println(products);
        System.out.println(categories);
        model.addAttribute("listProduct", products);
        model.addAttribute("listCategory", categories);
        return "admin/product/index-product";
    }

    @GetMapping("/add")
    public String add(Model model) {
        ProductDTO productDTO = new ProductDTO();
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("listProduct", productDTO);
        model.addAttribute("listCategory", categoryList);
        return "admin/product/add-pro";
    }

    @PostMapping("/add")
    public String createPro(@Valid @ModelAttribute("listProduct") ProductDTO productDTO, BindingResult result, RedirectAttributes attributes,
                            @RequestParam("images") MultipartFile[] files) {
        if (result.hasErrors()) {
            return "admin/product/add-pro";
        } else {
            try {
                MultipartFile file = productDTO.getFile();
                String fileName = file.getOriginalFilename();
                File destination = new File(pathPro + fileName);

                file.transferTo(destination);
                productDTO.setFile(file);
                int productId = productService.addPro(productDTO);

                for (MultipartFile multipartFile : files) {
                    String fileNames = multipartFile.getOriginalFilename();
                    File destinations = new File(pathPro + fileNames);
                    multipartFile.transferTo(destinations);
                    Image image = new Image();
                    image.setProductId(productId);
                    image.setImageUrl(fileNames);
                    imageService.saveOrUpdate(image);
                }
                attributes.addFlashAttribute("success-pro", "Thêm mới sản phẩm thành công");
                return "redirect:/admin/product";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePro(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        List<Category> categoryList = categoryService.findAll();
        List<Image> imageList = imageService.findListImage(id);
        model.addAttribute("product", product);
        model.addAttribute("imageList", imageList);
        model.addAttribute("categoryList", categoryList);

        return "/admin/product/edit-pro";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("avata") MultipartFile file,
                                @RequestParam("newImages") MultipartFile[] newImages) {
        Product updatePro = productService.findById(product.getProductId());
        List<Image> imageList = imageService.findListImage(product.getProductId());
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File destination = new File(pathPro + fileName);

                file.transferTo(destination);
                product.setImage(fileName);
            } else {
                product.setImage(updatePro.getImage());
            }
            productService.updatePro(product);
            System.out.println(newImages.length);
            if (!Objects.equals(newImages[0].getOriginalFilename(), "")) {
                System.out.println("ok");
                imageService.deleteImage(product.getProductId());
                for (MultipartFile multipartFile : newImages) {
                    System.out.println(multipartFile.getOriginalFilename());
                    if (!multipartFile.isEmpty()) {
                        String fileNames = multipartFile.getOriginalFilename();
                        File destinations = new File(pathPro + fileNames);
                        multipartFile.transferTo(destinations);

                        Image image = new Image();
                        image.setProductId(product.getProductId());
                        image.setImageUrl(fileNames);
                        imageService.saveImage(image);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

}
