<?php

    include_once '../services/product_service.php';

    include_once '../config/core.php';
    include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
    include_once '../../libs/php-jwt-master/src/ExpiredException.php';
    include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
    include_once '../../libs/php-jwt-master/src/JWT.php';

    use \Firebase\JWT\JWT;

    class ProductController{   
        
        public function getById($id){
           $product = (new ProductService())->getById($id);           
           return $product;
        }

        public function get(){
            $products = (new ProductService())->get();           
            return $products;
        }

         public function insert($product){
            $isInserted = (new ProductService())->insert($product);           
            return $isInserted;
        }

         public function update($product){
            $isUpdated = (new ProductService())->update($product);           
            return $isUpdated;
        }

         public function delete($id){
            $isDeleted = (new ProductService())->delete($id);           
            return $isDeleted;
        }
    }   
?>