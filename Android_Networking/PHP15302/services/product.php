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

        public function getById($id){
            try {
                $q = "SELECT id, name, price, quantity, image_url, category_id
                     from " . $this->tblProducts . " where id=:id ";
                $stmt = $this->connection->prepare($q);                
                $stmt->bindParam(":id", $id);

                $stmt->execute();

                if ($stmt->rowCount() > 0) {
                    $row = $stmt->fetch(PDO::FETCH_ASSOC);
                    extract($row);
                    $pro = array(
                        "id" =>$id,
                        "name" =>$name,
                        "price" =>$price,
                        "quantity" =>$quantity,
                        "image_url" =>$image_url,
                        "category_id" =>$category_id,
                    );                                        
                    return $pro;
                }
            } catch (Exception $e) {
                echo $e->getMessage();
            }
            return null;
        }

        public function getAllProducts(){
            try {
                $q = "SELECT id, name, price, quantity, image_url, category_id
                     from " . $this->tblProducts . " order by id desc ";
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

        public function getAllCategories(){
            try {
                $q = "SELECT id, name
                     from " . $this->tblCategories . " ";
                $stmt = $this->connection->prepare($q);                
                
                $stmt->execute();

                if ($stmt->rowCount() > 0) {
                    $categories = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $cat = array(
                            "id" =>$id,
                            "name" =>$name
                        );
                        array_push($categories, $cat);
                    };                    
                    return $categories;
                }
            } catch (Exception $e) {
                echo $e->getMessage();
            }
            return null;
        }

        public function insert($name, $price, $quantity, $image_url, $category_id)
        {
            try {
                $q = "insert into " . $this->tblProducts . "
                        set name=:name,
                        price=:price,
                        quantity=:quantity,
                        image_url=:image_url,
                        category_id=:category_id
                ";
                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":name", $name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":quantity", $quantity);
                $stmt->bindParam(":image_url", $image_url);
                $stmt->bindParam(":category_id", $category_id);
                
                $this->connection->beginTransaction();

                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {
                //throw $th;
                echo $e;
            }
            return false;
        }

        public function update($id, $name, $price, $quantity, $image_url, $category_id)
        {
            try {
                $q = "update " . $this->tblProducts . "
                        set name=:name,
                        price=:price,
                        quantity=:quantity,
                        image_url=:image_url,
                        category_id=:category_id
                        where id=:id
                ";
                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":name", $name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":quantity", $quantity);
                $stmt->bindParam(":image_url", $image_url);
                $stmt->bindParam(":category_id", $category_id);
                $stmt->bindParam(":id", $id);
                
                $this->connection->beginTransaction();

                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {
                //throw $th;
                echo $e;
            }
            return false;
        }

        public function delete($id)
        {
            
        }
       
    }

?>