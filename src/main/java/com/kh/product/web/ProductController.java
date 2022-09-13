package com.kh.product.web;

import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.form.AddForm;
import com.kh.product.web.form.EditForm;
import com.kh.product.web.form.ProductForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductSVC productSVC;

    //상품등록 양식
    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", new AddForm());
        return "/product/addForm";
    }

    //상품등록 처리
    @PostMapping("/add")
    public String add(@ModelAttribute("form") AddForm addForm,

                      RedirectAttributes redirectAttributes){
        Product product = new Product();

        product.setPname(addForm.getPname());
        product.setCount(addForm.getCount());
        product.setPrice(addForm.getPrice());

        Product savedProduct = productSVC.add(product);

        Long id = savedProduct.getPid();
        redirectAttributes.addAttribute("id",id);

        return "redirect:/products/{id}";
    }

    //조회
    @GetMapping("/{id}")
    public String findByPid(@PathVariable("id") Long id, Model model) {

        Product findedProduct = productSVC.findByPid(id);

        ProductForm productForm = new ProductForm();
        productForm.setPId(findedProduct.getPid());
        productForm.setPname(findedProduct.getPname());
        productForm.setCount(findedProduct.getCount());
        productForm.setPrice(findedProduct.getPrice());

        model.addAttribute("form",productForm);

        return "product/productForm";
    }

    //수정화면
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model){

        Product findedProduct = productSVC.findByPid(id);

        EditForm editForm = new EditForm();
        editForm.setPname(findedProduct.getPname());
        editForm.setCount(findedProduct.getCount());
        editForm.setPrice(findedProduct.getPrice());

        model.addAttribute("form",editForm);
        return "product/editForm";

    }

    //수정처리
    @PostMapping("/{id}/edit")
    public String edit (@PathVariable("id") Long id,
                        @ModelAttribute("form") EditForm editForm
                        ){
        Product product = new Product();
        product.setPname(editForm.getPname());
        product.setCount(editForm.getCount());
        product.setPrice(editForm.getPrice());

        int updateRow = productSVC.update(id,product);
        if(updateRow == 0) {
            return "product/editForm";
        }
        return "redirect:/products/{id}"; //회원 상세화면
    }

    //삭제처리
    @GetMapping("/{id}/del")
    public String del(@PathVariable("id") Long id){
        int deleteRow = productSVC.delete(id);
        if(deleteRow == 0){
            return "redirect:/products/"+id;
        }

        return "redirect:/products/all";
    }

    //목록화면
    @GetMapping("/all")
    public String all(Model model){

        List<Product> products = productSVC.productAll();
        List<ProductForm> list = new ArrayList<>();

        products.stream().forEach(product->{
            ProductForm productForm = new ProductForm();
            BeanUtils.copyProperties(product,productForm);
            list.add(productForm);
        });

        model.addAttribute("list",list);

        return "product/all";

    }
}
