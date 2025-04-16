<script setup lang="ts">
      import type { Product } from '../log/data'

      const { product,productList }=defineProps({
            productList:{
                  type: Array,
                  required: true
            },
            product:{
                  type: Object,
                  required:true
            }
      })

      const deleteProduct=(id:String)=>{

            fetch("http://localhost:8080/products",{
                  method: "DELETE",
                  headers:{
                        "Content-type":"application/json"
                  },
                  body:JSON.stringify({id:id})
            })
            .then(res=>res.json())
            .then(data=>console.log(data.message))
            .catch(err=> console.error(err))

            const filterProduct= (productList as Product[]).filter((product)=>product.id!=id)

            productList.length=0
            productList.push(...filterProduct)
      }
</script>
<template>

      <div>
            <img 
                  class="product-image"
                  alt="Product"
                  :src= "product.image"
            />
            </div>
            <span class="left-container">
                  <p>{{ product.name }}</p>
                  <p>{{ product.price }}€</p>
            </span>
            <span class="right-container" >
                  <button 
                        @click="()=>{product.edit=true}"
                  >     Edit
                  </button>
                  <button 
                        @click="()=>deleteProduct(product.id)"
                  >     Delete
                  </button>
            </span>

</template>

<style>

      .product-image{
              border-radius: 12px;
              position: absolute;
              top:0;
              left: 0;

              width: 120px;
              height: 120px;
       }

      .left-container{
            position: absolute; 
            top:10%; 
            left: 20%; 
            
            display: flex; 
            flex-direction: column;
       }

      .right-container{
            position: absolute; 
            top:35%; 
            right: 15%; 
            
            display: flex; 
            gap:0.5rem
      }

</style>