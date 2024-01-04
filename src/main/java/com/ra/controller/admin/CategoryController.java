package com.ra.controller.admin;

import com.ra.model.dao.category.CategoryDAO_Impl;
import com.ra.model.entity.Category;
import com.ra.model.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ra.model.dao.category.CategoryDAO_Impl.totalPage;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping({"", "/{i}"})
    public String index(@PathVariable(value = "i", required = false) Integer i, @RequestParam(value = "search", required = false) String search ,Model model) {
        int currentPage = (i != null && i>= 1 ? i : 1);
        if (search == null || search.isEmpty()) {
            List<Category> categories = categoryService.pagination(4, currentPage);
            model.addAttribute("list", categories);
            model.addAttribute("totalPage", totalPage);

        } else {
            int totalPage = categoryService.paginationTotal(search, 4, currentPage);
            List<Category> categories = categoryService.paginationBySearch(search, 4, currentPage);

            model.addAttribute("list", categories);
            model.addAttribute("totalPage", totalPage);
        }

        model.addAttribute("currentPage", currentPage);
        return "admin/category/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
//        List<Category> categories = categoryService.findListByParentId();
//        model.addAttribute("listByParent", categories);
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/add-cat";
    }

    @PostMapping("/add")
    public String create_cat(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model  model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit-cat";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/change/{id}")
    public String changeStatus(@PathVariable("id") Integer id) {
        categoryService.changeStatus(id);
        return "redirect:/admin/category";
    }



}
