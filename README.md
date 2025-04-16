# REGISTER PRODUCT

## Description
Create and manage a simple product


## Technologies Used
- **Backend:** Java Spring Boot, Maven
- **Frontend:** Vue, Vite


## Prerequisites 
- Java: 21
- JDK: 21.0.7
- Spring Boot: 3.4.4
- Maven: 3.9.6
- Vue: 3.5.13
- Vite: 6.2.0
- Docker 27.3.1
- postgres:17.4-alpine  

## Instalation  

### clone repository  
git clone https://github.com/msg019/register-product.git  
cd register-product  

### set up Database  
docker compose up -d  

### set up Backend  
cd demo  
mvn spring-boot:run  

### set up Frontend  
cd frontend  
npm install  
npm run dev  





