package com.example.demo.Classes;

//Client Product

public class ProductDTO {
    private String id;
    private String name;
    private float price;
    private String image;


    public ProductDTO(String id,String name, float price, String image){
        this.id=id;
        this.name=name;
        this.price=price;
        this.image=image;
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public float getPrice(){
        return this.price;
    }

    public String getImage(){
        return this.image;
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

    public void setImage(String image){
        this.image=image;
    }

}


