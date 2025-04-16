export interface Product{
      id: string,
      name: string,
      price: number,
      image: string,

      //It will allow edit the product
      edit?: boolean,
      //It will allow add an preview of the new image
      preview?: string,
      //It will show the image and delete image button or  add new image button
      loadImage?: boolean
}

