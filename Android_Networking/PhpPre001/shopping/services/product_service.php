<?php
    include_once '../config/database_config.php';
    include_once '../models/product.php';

    class ProductService{        
        private $conn;
        private $db_table = "tblProducts";

        public function __construct(){
            $this->conn = (new Database())->getConnection();
        }

        public function getById($id){
            try {
                $sqlQuery = "SELECT id, name, price, created, category_id, image_url FROM " . $this->db_table . " WHERE id = ? LIMIT 0,1";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->bindParam(1, $id);
                $stmt->execute();
                if ($stmt->rowCount()>0) {
                    $row = $stmt->fetch(PDO::FETCH_ASSOC);  
                    extract($row); 
                    $product = array(
                        "id" => $id,
                        "name" => $name,
                        "price" => $price,
                        "created" => $created,
                        "category_id" => $category_id,
                        "image_url" => $image_url
                    );
                    return $product;
                }                
            } catch (Exception $e) {           
            }
            return null;
        }

        public function get(){
            try {
                $sqlQuery = "SELECT id, name, price, created, category_id, image_url FROM " . $this->db_table . "";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->execute();
                if ($stmt->rowCount()>0) {
                    $products = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $product = array(
                            "id" => $id,
                            "name" => $name,
                            "price" => $price,
                            "created" => $created,
                            "category_id" => $category_id,
                            "image_url" => $image_url
                        );
                        array_push($products, $product);
                    }                    
                    return $products;
                }                
            } catch (Exception $e) {           
            }
            return null;
        }

        public function insert($product){
            try {
                $sqlQuery = "INSERT INTO
                            ". $this->db_table ."
                            SET
                                name = :name, 
                                price = :price, 
                                category_id = :category_id, 
                                image_url = :image_url";
                $stmt = $this->conn->prepare($sqlQuery);
                // sanitize
                $name = htmlspecialchars(strip_tags($product->getName()));
                $price = htmlspecialchars(strip_tags($product->getPrice()));
                $category_id = htmlspecialchars(strip_tags($product->getCategoryId()));
                $image_url = htmlspecialchars(strip_tags($product->getImageUrl()));
            
                // bind data
                $stmt->bindParam(":name", $name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":category_id", $category_id);
                $stmt->bindParam(":image_url", $image_url);
            
                $this->conn->beginTransaction();
                if($stmt->execute()){
                    $this->conn->commit();
                    return true;
                } else {
                    $this->conn->rollBack();  
                    return false;          
                }
            } catch (Exception $e) {
                $this->conn->rollBack();                
            }
            return false;
        }

        public function update($product){
            try {
                $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                        name = :name, 
                        price = :price, 
                        category_id = :category_id, 
                        image_url = :image_url
                    WHERE 
                        id = :id";
        
                $stmt = $this->conn->prepare($sqlQuery);
            
                $name = htmlspecialchars(strip_tags($product->getName()));
                $price = htmlspecialchars(strip_tags($product->getPrice()));
                $category_id = htmlspecialchars(strip_tags($product->getCategoryId()));
                $image_url = htmlspecialchars(strip_tags($product->getImageUrl()));
                $id = htmlspecialchars(strip_tags($product->getId()));        
            
                $stmt->bindParam(":name", $name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":category_id", $category_id);
                $stmt->bindParam(":image_url", $image_url);
                $stmt->bindParam(":id", $id);
            
                $this->conn->beginTransaction();
                if($stmt->execute()){
                    $this->conn->commit();
                    return true;
                } else {
                    $this->conn->rollBack();  
                    return false;          
                }
            } catch (\Throwable $th) {
                $this->conn->rollBack();                
            }
            return false;            
        }

        public function delete($id){
            try {
                $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE id = ?";
                $stmt = $this->conn->prepare($sqlQuery);
            
                $id=htmlspecialchars(strip_tags($id));
            
                $stmt->bindParam(1, $id);
            
                $this->conn->beginTransaction();
                if($stmt->execute()){
                    $this->conn->commit();
                    return true;
                } else {
                    $this->conn->rollBack();  
                    return false;          
                }
            } catch (\Throwable $th) {
                $this->conn->rollBack();     
            }
            return false;     
        }

    }
?>
