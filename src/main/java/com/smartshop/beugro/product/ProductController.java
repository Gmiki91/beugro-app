package com.smartshop.beugro.product;

import com.smartshop.beugro.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

import static javax.imageio.ImageIO.getCacheDirectory;

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
        product.checkStatus();
        productDao.updateProduct(product);
        return new ResponseMessage("ok");
    }
    @GetMapping("/exportProduct")
    public ResponseMessage exportProduct(@RequestParam long id){
       Product product = productDao.getProductById(id);
        try {

            File file = new File(getCacheDirectory(),"file.csv");
            JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(product, file);
            jaxbMarshaller.marshal(product, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new ResponseMessage("Export sikeres");

    }
}
