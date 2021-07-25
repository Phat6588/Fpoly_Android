<?php
    include_once '../configs/database_config.php';
    include_once '../models/user.php';
    include_once '../models/reset.php';


    class ProductService {
        private $connection;
        private $tblproducts = "tblproducts";
        private $tblCategories = "tblCategories";

        public function __construct()
        {
            $this->connection = (new DatabaseConfig())->getConnection();
        }

        public function getAllProducts()
        {
            try {
                $query = "SELECT id, product_name, price, image_url, category_id
                                 FROM " . $this->tblproducts ."  ";
                $stmt = $this->connection->prepare($query);
                
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $products = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $product = array(
                            "id" => $id,
                            "product_name" => $product_name,
                            "price" => $price,
                            "image_url" => $image_url,
                            "category_id" => $category_id,
                        );
                        array_push($products, $product);
                    }
                    return $products;
                }                
            } catch (Exception $e) {                
            }
            return null;
        }




    }
?>