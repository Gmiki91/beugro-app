package com.smartshop.beugro.product;

import com.smartshop.beugro.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping("/saveProduct")
    public ResponseMessage saveProduct(@RequestBody Product product){
        if(product.getName().trim().length()<3){
            return new ResponseMessage("The product name has to be at least 3 characters long");
        }else{
            product.setUp();
            productDao.saveProduct(product);
            return new ResponseMessage(product.getName() + " has been saved");
        }
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts(){
        return productDao.listProducts();
    }
    @PutMapping("/updateProduct")
    public ResponseMessage updateProduct(@RequestBody Product product){
        productDao.updateProduct(product);
        return new ResponseMessage("ok");
    }
}
