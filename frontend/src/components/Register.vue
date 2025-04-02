<script  setup lang="ts">
       import { onMounted, reactive } from 'vue'
       import ProductCard from './ProductCard.vue' 
       import Form from './Form.vue'
       import type {Product}  from "./../log/data"
       
      
       const productList= reactive<Array<Product>>([])     

       //Show the products when page loads
       onMounted(async()=>{
              await getProducts()
       })

       

       const getProducts=async()=>{

              const res= await fetch("http://localhost:8080/products")
              
              //Object.assign(productList, await res.json())
              productList.length=0
              productList.push(... await res.json())
              productList.map(product=>{
                     product.image="data:image/jpeg;base64,"+product.image
                     product.edit=false
                     product.loadImage=true
              })
       }

       

</script>  

<template>
       <Form 
              :getProducts="getProducts" 
       />
     
       <ul>
             <li v-if="productList.length>0"  v-for="product in productList" :key="product.id">       
                     <ProductCard 
                            :getProducts="getProducts"
                            :productList="productList"
                            :product="product"
                     />
              
             </li>
       </ul>
</template>

<style>

      
       
       ul{
              list-style: none;
       }

       li{
              border: 1px white solid;
              border-radius: 12px;
              height: 120px;
              display: flex;
              justify-content: space-around;
              align-items: center;
              position: relative;

              margin-top: 0.5rem;
       }


       

</style>