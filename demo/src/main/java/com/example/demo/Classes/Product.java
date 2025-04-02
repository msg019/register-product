package com.example.demo.Classes;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import javax.sql.rowset.serial.SerialBlob;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//Database
@Entity
@Table(name="Products")
public class Product {
    @Id
    private String id;
    
    @NotNull
    @Size(min=5, max=50)
    private String name;


    @NotNull
    @Positive
    private float price;

    
   
    @Transient
    private String imageBase64;

    private Blob imageData;

    public Product(){}

    public Product(String id,String name, float price, String imageBase64, Blob imageData){
        this.id=id;
        this.name=name;
        this.price=price;
        this.imageBase64=imageBase64;
        this.imageData=imageData;
    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public float getPrice(){
        return price;
    }

    public String getImageBase64(){
        return imageBase64;
    }

    public Blob getImageData(){
        return imageData;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }
    
    public void setPrice(float price){
        this.price=price;
    }

    public void setImageBase64(String imageBase64){
        this.imageBase64=imageBase64;
    }

    public void setImageData(Blob imageData){
        this.imageData=imageData;
    }

    public void generateId(){
        int leftLimit=48; //number 0
        int rightLimit=122; //letter z
        int len=10;
        Random random= new Random();

        this.id= random.ints(leftLimit, rightLimit+1)
            .filter(i->(i<=57 || i>=65) && (i<=90 || i>=97))  //Filter special characters like @,/,#,...
            .limit(len)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public void Base64ToBlob() throws SQLException{
        byte[] imageBytes= Base64.getDecoder().decode(this.imageBase64);
        
        this.imageData= new SerialBlob(imageBytes);
    }
      

    public void blobToBase64() throws SQLException{
        long size = this.imageData.length();

        byte[] imageBytes= this.imageData.getBytes(1, (int) size);

        this.imageBase64=Base64.getEncoder().withoutPadding().encodeToString(imageBytes);

    }



}
