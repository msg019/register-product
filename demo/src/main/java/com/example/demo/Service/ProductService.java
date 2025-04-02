package com.example.demo.Service;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Classes.Product;
import com.example.demo.Classes.ProductDTO;
import com.example.demo.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
    
    private final ProductRepository repository;

    //Auto init a variable
    @Autowired
    private DataSource dataSource;

    public ProductService(ProductRepository repository){
        this.repository=repository;
    }
    
    public void createProduct(ProductDTO productDTO) throws IOException, SQLException{
        //Update product
        Product product= new Product();
        product.generateId();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageBase64(productDTO.getImage());

        
        //Convert Image base64 to Blob
        product.Base64ToBlob();

        repository.save(product);

    }

    @Transactional
    public List<ProductDTO> getAllProduct() throws SQLException, IOException{

        List<ProductDTO> productDTOs= new ArrayList<>();

        try(Connection connection= dataSource.getConnection()){

            connection.setAutoCommit(false);

            List<Product> products= repository.findAll();

            for (Product product : products) {

                product.blobToBase64();
                
                productDTOs.add(new ProductDTO(product.getId(),product.getName(),product.getPrice(),product.getImageBase64()));
    
            }

            return productDTOs;
        }
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO) throws SQLException{
        Optional<Product> existingProduct =repository.findById(productDTO.getId());

        //Update existing product
        try(Connection connection= dataSource.getConnection()){

            connection.setAutoCommit(false);

            existingProduct.get().setId(productDTO.getId());
            existingProduct.get().setName(productDTO.getName());
            existingProduct.get().setPrice(productDTO.getPrice());
            existingProduct.get().setImageBase64(productDTO.getImage());
            
            //Convert Image base64 to Blob
            existingProduct.get().Base64ToBlob();
    
    
            repository.save(existingProduct.get());
        }
        

        
    }
    
    public void deleteProduct(String id){
    
        repository.deleteById(id);
       
    }
}
