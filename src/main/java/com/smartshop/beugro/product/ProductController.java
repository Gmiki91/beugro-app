package com.smartshop.beugro.product;

import com.smartshop.beugro.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/saveProduct")
    public ResponseMessage saveProduct(@RequestBody Product product){
        if(product.getName().length()<3){
            return new ResponseMessage("The product name has to be at least 3 characters long");
        }else{
            productDao.saveProduct(product);
            return new ResponseMessage(product.getName() + " has been saved");
        }
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts(){
        return productDao.listProducts();
    }
}
