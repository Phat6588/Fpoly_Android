<?php
    include_once '../config/database_config.php';
    include_once '../models/user.php';

    class UserService{        
        private $conn;
        private $db_table = "tblUsers";

        public function __construct(){
            $this->conn = (new Database())->getConnection();
        }

        public function login($username){
            try {
                $sqlQuery = "SELECT id, username, email, hash_password FROM " . $this->db_table . " WHERE username = ? LIMIT 0,1";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->bindParam(1, $username);
                $stmt->execute();
                if ($stmt->rowCount()>0) {
                    $dataRow = $stmt->fetch(PDO::FETCH_ASSOC);            
                    extract($dataRow);
                    return new User($id, $username,$email,$hash_password);
                }                
            } catch (Exception $e) {           
            }
            return null;
        }
    }
?>
