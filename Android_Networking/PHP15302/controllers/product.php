<?php

    include_once '../services/product.php';    

class ProductController {

        private $product_service;

        public function __construct()
        {
            $this->product_service = new ProductService();
        }

        public function getAllProducts(){
            return $this->product_service->getAllProducts();
        }
    }

?>