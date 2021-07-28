<?php

    include_once '../services/product_service.php';

    class ProductController {

        private $product_service;
        public function __construct()
        {
            $this->product_service = new ProductService();        
        }

        public function getAllProducts()
        {
            return $this->product_service->getAllProducts();            
        }

        public function getAllCategories()
        {
            return $this->product_service->getAllCategories();            
        }

        public function insert($product)
        {
            return $this->product_service->insert($product);            
        }

      
        
    }
?>