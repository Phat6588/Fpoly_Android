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

        public function getByEmail($email){
            try {
                $q = "SELECT id, email, hash_password from " . $this->tblUsers . " 
                    where email=:email limit 0,1 ";
                $stmt = $this->connection->prepare($q);
                $stmt->bindParam(":email", $email);
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $row = $stmt->fetch(PDO::FETCH_ASSOC);
                    extract($row);
                    return new User($id, $email, $hash_password);
                }
            } catch (Exception $e) {
                echo $e->getMessage();
            }
            return null;
        }

        public function register($email, $hash_password)
        {
            try {
                $q = "insert into " . $this->tblUsers . "
                        set email=:email,
                        hash_password=:hash_password
                ";
                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":hash_password", $hash_password);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (\Throwable $th) {
                //throw $th;
            }
            return false;
        }


        // tao token reset password, luu vao bang resetpassword
        public function generatePassResetToken($email)
        {
            $token = md5($email) .rand(10, 9999);
            try {
                $q = "insert into " . $this->tblPasswordResets . "
                        set email=:email,
                        token=:token";
                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":token", $token);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return $token;
                } else {
                    $this->connection->rollBack();
                    return null;
                }
            } catch (\Throwable $th) {
                //throw $th;
            }
            return null;
        }


        // check token
        public function checkToken($email, $token)
        {
            try {
                $q = "SELECT id from " . $this->tblPasswordResets . " 
                                where email=:email 
                                and token=:token
                                and available = 1
                                and created > now() - interval 30 minute
                                limit 0,1 ";
                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":token", $token);

                $stmt->execute();
                if ($stmt->rowCount() > 0) {                    
                    return true;
                }
            } catch (Exception $e) {
                echo $e->getMessage();
            }
            return false;
        }

        // change password
        public function changePassword($email, $hash_password)
        {
            try {
                $q = "update " . $this->tblUsers . "
                        set hash_password=:hash_password
                        where email=:email";

                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":hash_password", $hash_password);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (\Throwable $th) {
                //throw $th;
            }
            return false;
        }

        // clear token
        public function clearToken($token)
        {
            try {
                $q = "update " . $this->tblPasswordResets . "
                        set available = 0
                        where token=:token";

                $stmt = $this->connection->prepare($q);

                $stmt->bindParam(":token", $token);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (\Throwable $th) {
                //throw $th;
            }
            return false;
        }
    }

?>