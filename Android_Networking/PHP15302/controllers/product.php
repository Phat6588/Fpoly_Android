<?php

    include_once '../services/product.php';    

class ProductController {

        private $product_service;

        public function __construct()
        {
            $this->product_service = new ProductService();
        }

        public function getById($id){
            return $this->product_service->getById($id);
        }

        public function getAllProducts(){
            return $this->product_service->getAllProducts();
        }

        public function getAllCategories(){
            return $this->product_service->getAllCategories();
        }

        public function insert($name, $price, $quantity, $image_url, $category_id){
            // validation
            return $this->product_service->insert($name, $price, $quantity, $image_url, $category_id);
        }

        public function update($id, $name, $price, $quantity, $image_url, $category_id){
            // validation
            return $this->product_service->update($id, $name, $price, $quantity, $image_url, $category_id);
        }
    }

?>