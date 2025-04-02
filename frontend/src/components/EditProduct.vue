<script setup lang="ts">
      import type{ Product } from '../log/data'
      import { fileToBase64 } from '../log/function'


      const { product }=defineProps({
            product:{
                  type: Object,
                  required:true
            }
      })

      const showPreviewProduct=async(e:Event,product:Product)=>{
            const files= e.currentTarget as HTMLInputElement
              
            if(!files || !files.files){return}

            const image= files.files[0]

            product.preview= await fileToBase64(image)
      }

      const cancelEditProduct=(product:Product)=>{
            product.edit=false
            product.loadImage=true
            product.preview=""
      }

</script>
<template>
      <div class="image-container" v-if="product.loadImage">
            <img 
                  class="product-image"
                  alt="Product"
                  :src= product.image
            />
            <input 
                  class="del-img-btn"
                  v-show="product.loadImage" 
                  type="button" 
                  @click="product.loadImage=false"
                  value="X"
            />
      </div>
      <div v-else>
            <img 
                  class="product-image"
                  v-if="product.preview"
                  alt="Product"
                  :src=product.preview
            />
            <input
                  class="select-image"
                  name="file"
                  type="file"
                  accept="image/jpeg,image/jpg,image/png"
                  required
                  @change="(e)=>{showPreviewProduct(e,product as Product)}"
            />
      </div>
     

      <span class="left-edit">
            <input 
                  name='data' 
                  type='text' 
                  :value=product.name 
            />
            <input 
                  name='data' 
                  type='number' 
                  min=0.1 
                  step=0.01 
                  :value=product.price 
            />
      </span>
      <span class="right-edit">
            <button 
                  type="submit"
            >     Confirm
            </button>
            <button 
                  type="button" 
                  @click="cancelEditProduct(product as Product)"
            >     Cancel
            </button> 
      </span> 


</template>

<style>

      .image-container{
            position: absolute;
            top:0;
            left: 0;            
      }

      .product-image{
            position: absolute;
            top:0;
            left: 0;

            height: 120px;
            width: 120px;
            border-radius: 12px;
      }

      .del-img-btn{
            position: absolute;
            top:2%;
            left: 1%;

            &:hover{
                  cursor: pointer;
                  color: red;
            }
      }

      .left-edit{
            position: absolute;
            top:20%;
            left:20%;

            display: flex;
            flex-direction: column;

            gap:0.5rem;

            &>input[type="number"]{
                  width: 4.2rem;
            }
      }

      .select-image{
            position: absolute;
            bottom:14%;
            left: 20%;
      }

      .right-edit{
            position: absolute;
            top:35%;
            right: 15%;

            display: flex;
            gap:0.5rem
      }
</style>