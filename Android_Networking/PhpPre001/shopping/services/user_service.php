<?php
    include_once '../config/database_config.php';
    include_once '../models/user.php';
    include_once '../models/reset.php';


    class UserService{        
        private $conn;
        private $db_table = "tblUsers";
        private $db_tableReset = "tblPasswordResets";

        public function __construct(){
            $this->conn = (new Database())->getConnection();
        }

        public function login($email){
            try {
                $sqlQuery = "SELECT id, email, hash_password FROM " . $this->db_table . " WHERE email = ? LIMIT 0,1";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->bindParam(1, $email);
                $stmt->execute();
                if ($stmt->rowCount()>0) {
                    $dataRow = $stmt->fetch(PDO::FETCH_ASSOC);            
                    extract($dataRow);
                    return new User($id, $email,$hash_password);
                }                
            } catch (Exception $e) {           
            }
            return null;
        }

        public function getByEmail($email)
        {
            try {
                $sqlQuery = "SELECT id, email FROM " . $this->db_table . " WHERE email = ? LIMIT 0,1";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->bindParam(1, $email);
                $stmt->execute();
                if ($stmt->rowCount()>0) {
                    return true;
                }    
                return false;            
            } catch (Exception $e) {           
            }
            return false;
        }

        public function generateToken($email){
            $token = md5($email) . rand(10, 9999);
            try {
                $sqlQuery = "INSERT INTO
                            ". $this->db_tableReset ."
                                SET
                                    email = :email, 
                                    token = :token";
                $stmt = $this->conn->prepare($sqlQuery);
            
                // bind data
                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":token", $token);
            
                $this->conn->beginTransaction();
                if($stmt->execute()){
                    $this->conn->commit();
                    return $token;
                } else {
                    $this->conn->rollBack();  
                    return null;          
                }
            } catch (Exception $e) {
                $this->conn->rollBack();                
            }
            return null;
        }

        public function getByToken($token, $email)
        {
            // chua xong
            try {
                $sqlQuery = "SELECT * FROM " . $this->db_tableReset . " WHERE token = ? and email =? and available = 1
                                                                     and created  > NOW() - INTERVAL 30 MINUTE LIMIT 0,1";
                $stmt = $this->conn->prepare($sqlQuery);
                $stmt->bindParam(1, $token);
                $stmt->bindParam(2, $email);
                $stmt->execute();
                if ($stmt->rowCount()>0) {                    
                    return true;
                }    
                return false;            
            } catch (Exception $e) {           
            }
            return false;
        }

        public function changePassword($email, $hash_password)
        {
            try {
                $sqlQuery = "UPDATE
                            ". $this->db_table ."
                                SET
                                hash_password = :hash_password WHERE email = :email";
                $stmt = $this->conn->prepare($sqlQuery);
            
                // bind data
                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":hash_password", $hash_password);
            
                $this->conn->beginTransaction();
                if($stmt->execute()){
                    $this->conn->commit();
                    return true;
                } else {
                    $this->conn->rollBack();  
                    return true;          
                }
            } catch (Exception $e) {
                $this->conn->rollBack();                
            }
            return true;
        }

        public function clearToken($token)
        {
            try {
                $sqlQuery = "UPDATE
                            ". $this->db_tableReset ."
                                SET
                                available = 0 WHERE token = :token";
                $stmt = $this->conn->prepare($sqlQuery);
            
                // bind data
                $stmt->bindParam(":token", $token);
            
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

    }
?>
