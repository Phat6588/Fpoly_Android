<?php
    include_once '../configs/database_config.php';
    include_once '../models/product.php';


    class ProductService {
        private $connection;
        private $tblCategories = "tblCategories";
        private $tblProducts = "tblProducts";

        public function __construct()
        {
            $this->connection = (new Database())->getConnection();
        }

        public function getAllProducts(){
            try {
                $q = "SELECT id, name, price, quantity, image_url, category_id
                     from " . $this->tblProducts . " ";
                $stmt = $this->connection->prepare($q);                
                
                $stmt->execute();

                if ($stmt->rowCount() > 0) {
                    $products = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $pro = array(
                            "id" =>$id,
                            "name" =>$name,
                            "price" =>$price,
                            "quantity" =>$quantity,
                            "image_url" =>$image_url,
                            "category_id" =>$category_id,
                        );
                        array_push($products, $pro);
                    };                    
                    return $products;
                }
            } catch (Exception $e) {
                echo $e->getMessage();
            }
            return null;
        }

       
    }

?>