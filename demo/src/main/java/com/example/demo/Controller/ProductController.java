package com.example.demo.Controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Classes.ProductDTO;
import com.example.demo.Service.ProductService;

import jakarta.validation.Valid;


@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/products")
public class ProductController {
    

    private final ProductService service;

    public ProductController(ProductService service){
        this.service=service;
    }  


   @PostMapping
   public HashMap<String,String> createProduct(@Valid @RequestBody ProductDTO productDTO) throws IOException, SQLException{
        
        service.createProduct(productDTO);

        HashMap<String,String> msg= new HashMap<>(){{
            put("message", "Product created");
        }};
        
        return msg;
       
    }

    @GetMapping
   public List<ProductDTO> getAllProducts() throws SQLException, IOException{
        
        
        return service.getAllProduct();
       
   }

   @PutMapping
   public HashMap<String,String> updateProduct(@RequestBody ProductDTO productDTO) throws SQLException{
    
    service.updateProduct(productDTO);
    
    HashMap<String,String> msg= new HashMap<>(){{put("message","Product updated");}};

    return msg;
   }


   @DeleteMapping
   public HashMap<String,String> deleteProduct( @RequestBody HashMap<String,String> id){

    service.deleteProduct(id.get("id"));

    HashMap<String,String> msg= new HashMap<>(){{put("message","Product deleted");}};

    return msg;
   }
}
