<script setup lang="ts">
      import  type {Product}  from '../log/data';
      import { fileToBase64 } from '../log/function';
      import EditProduct from './EditProduct.vue';
      import InfoProduct from './InfoProduct.vue';

      const { productList, getProducts, product } =defineProps({
            productList:{
                  type: Array,
                  required:true
            },
            getProducts:{
                  type:Function,
                  required:true
            },
            product:{
                  type: Object,
                  required:true
            }
      })

      const editProduct=async(e:Event, product:Product)=>{
              e.preventDefault();
              
              const { elements } = e.currentTarget as HTMLFormElement
              
              const inputs= elements.namedItem('data') as HTMLInputElement

              const inputList:string[]= []
              const files= elements.namedItem("file") as HTMLInputElement

              let imageBase64;

              if(!files || !files.files){
                     imageBase64=product.image.replace("data:image/jpeg;base64,","")
              }else{
                     const image=files.files[0]
                     imageBase64=(await (fileToBase64(image))).replace("data:image/jpeg;base64,","")
              }

              Object.values(inputs).map(input=>{
                     inputList.push(input.value)
              })

              const updateProduct:Product={
                     id: product.id,
                     name: inputList[0],
                     price: +inputList[1],
                     image: imageBase64
              }

              const res= await fetch('http://localhost:8080/products',{
                     method:"PUT",
                     headers:{
                     "Content-type":"application/json"
                     },body:JSON.stringify(updateProduct)
              })

              const data= await res.json()

              console.log(data.message)
              
              product.edit=false

              await getProducts()
       }

       

</script>

<template>
     <form v-if="product.edit" @submit="(e)=>editProduct(e,product as Product)">
            <EditProduct 
                  :product="product"
            />

      </form>
                   
      <div v-else >
            <InfoProduct 
                  :product-list="productList"
                  :product="product"
            />
      </div>


</template>