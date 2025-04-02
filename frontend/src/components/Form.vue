<script setup lang="ts">
      import { ref } from 'vue'
      import type { Product } from '../log/data'
      import { fileToBase64 } from '../log/function'
   
      const { getProducts }=defineProps({
            getProducts:{
                  type:Function,
                  required:true
            }
      })

      //Pre-View of upload image
      const preview=ref<string | null>(null)
      
      const createProduct=async(e:Event)=>{
              e.preventDefault()

              const { elements }=e.currentTarget as HTMLFormElement

              const inputs= elements.namedItem("data") as HTMLInputElement
              const files= elements.namedItem("file") as HTMLInputElement
       
              if(!files || !files.files){return}
              const image=files.files[0]

              const imageBase64= (await (fileToBase64(image))).replace("data:image/jpeg;base64,","")

              const inputList:string[]=[]

              Object.values(inputs).map(input=>{
                     inputList.push(input.value)
              })

              //Send a empty id so backend will create the id
              const product:Product={
                     id: "",
                     name: inputList[0],
                     price: +inputList[1],
                     image: imageBase64
              }


              const res= await fetch("http://localhost:8080/products",{
                            method: "POST",
                            headers:{
                                   "Content-type":"application/json"
                            },
                            body: JSON.stringify(product)
                      })
              const data= await res.json()
              console.log(data.message)

              //Clean inputs
              Object.values(inputs).map(input=>{
                     input.value=""
              })
              files.value=""
              preview.value=null

              //Update the list of products with the new product
              await getProducts()
       }

       //Pre-load of the image
       const showPreview=async(e:Event)=>{
              
              const files=e.currentTarget as HTMLInputElement

              if(!files || !files.files){return}
              const image=files.files[0]
       
              preview.value= await fileToBase64(image)
       }


</script>


<template>
      <form class="form-container" @submit="createProduct">
            <label>Name</label>
            <input 
                  name="data"
                  type="text"
                  required
            />
            <label>Price (€)</label>
            <input 
                  name="data"
                  type="number"
                  required
                  min="0.1"
                  step="0.01"
            />
            <label>Image</label>
            <input 
                  @change="showPreview"
                  name="file"
                  type="file"
                  accept="image/jpeg,image/jpg,image/png"
                  required
            />
            <span v-if="preview">
                  <img
                        class="preview-image"
                        alt="preview"
                        :src=preview
                  />
            </span>
            <span v-else>
                  <img 
                        class="preview-image"
                  />
            </span>

            <button> Register Product</button>
      </form>

</template>
<style>
      .form-container{
            margin-top: 0.5rem;

            display: flex;
            justify-content: center;
            align-items: center;

            &>input{
                  margin-right: 1rem;
            }

            &>label{
                  margin-right: 1rem;
            }

            &>input[type="number"], &>input[type="file"]{
                  width: 4.2rem;
            }

           

      }

      .image-container{
            display: flex;
            align-items: center;
            justify-content: center;
      }

      .preview-image{
            margin-right: 1rem;
            width: 60px;
            height: 60px;
      }

</style>
