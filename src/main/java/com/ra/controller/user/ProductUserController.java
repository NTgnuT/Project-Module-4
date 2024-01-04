package com.ra.controller.user;

import com.ra.model.entity.*;
import com.ra.model.service.cart.ICartService;
import com.ra.model.service.cartItem.ICartItemService;
import com.ra.model.service.category.ICategoryService;
import com.ra.model.service.image.ImageService;
import com.ra.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//@RequestMapping("/user/product")
public class ProductUserController {
    @Autowired
    IProductService productService;
    @Autowired
    ImageService imageService;
    @Autowired
    HttpSession session;
    @Autowired
    ICartService cartService;
    @Autowired
    ICartItemService cartItemService;
    @Autowired
    ICategoryService categoryService;
    @RequestMapping("/product")
    public String product(Model model) {
        List<Product> productList = productService.findAll();
        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
        return "client/product-user";
    }

    @GetMapping("/detail/{id}")
    public String productDetail (@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        List<Image> imageList = imageService.findListImage(id);
        List<Product> productList = productService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("imageList", imageList);
        model.addAttribute("productList", productList);
        return "client/product-detail";
    }

    @PostMapping("/addCart")
    public String addCart (@RequestParam("quantity") Integer qty, @RequestParam("productId") Integer productId) {
        User user = (User) session.getAttribute("userLogin");
        if (user == null) {
            return "redirect:/login";
        }

        Product product = productService.findById(productId);

        // Kiểm tra user có tồn tại giỏ hàng không
        Cart cart = cartService.findCartByUserId(user.getUserId());
        if (cart.getCartId() == 0) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartService.saveCart(newCart);
        }

        // Lấy danh sách những sản phẩm trong giỏ hàng của user đang đăng nhập
        List<CartItem> cartItemList = cartItemService.findCartItemByCartId(cartService.getCartId(user.getUserId()));
        boolean checkProduct = checkProductExist(product, cartItemList);

        if (checkProduct) {
            CartItem cartItem = findCartItemByProductId(product, cartItemList);
            assert cartItem != null;
            cartItem.setQuantity(cartItem.getQuantity() + qty);
            cartItemService.updateCartItem(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cartService.findById(cartService.getCartId(user.getUserId())));
            cartItem.setProduct(product);
            cartItem.setQuantity(qty);
            cartItemService.addCartItem(cartItem);
        }
        return "redirect:/product";
    }

    @PostMapping("/addCartSinger")
    public String addCartSingerPro (@RequestParam("prId") Integer productId) {
        User user = (User) session.getAttribute("userLogin");
        if (user == null) {
            return "redirect:/login";
        }

        Product product = productService.findById(productId);

        // Kiểm tra user có tồn tại giỏ hàng không
        Cart cart = cartService.findCartByUserId(user.getUserId());
        if (cart.getCartId() == 0) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartService.saveCart(newCart);
        }

        // Lấy danh sách những sản phẩm trong giỏ hàng của user đang đăng nhập
        List<CartItem> cartItemList = cartItemService.findCartItemByCartId(cartService.getCartId(user.getUserId()));
        boolean checkProduct = checkProductExist(product, cartItemList);

        if (checkProduct) {
            CartItem cartItem = findCartItemByProductId(product, cartItemList);
            assert cartItem != null;
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemService.updateCartItem(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cartService.findById(cartService.getCartId(user.getUserId())));
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItemService.addCartItem(cartItem);
        }
        return "redirect:/product";
    }

    private CartItem findCartItemByProductId(Product product, List<CartItem> cartItemList){
        for (CartItem item:cartItemList) {
            if (item.getProduct().getProductId()==product.getProductId()){
                return item;
            }
        }
        return null;
    }

    private boolean checkProductExist(Product product, List<CartItem> cartItemList) {
        for (CartItem item : cartItemList) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                return true;
            }
        }
        return false;
    }
}
