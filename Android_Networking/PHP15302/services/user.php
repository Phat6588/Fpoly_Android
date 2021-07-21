<?php
    include_once '../configs/database_config.php';
    include_once '../models/user.php';


    class UserService {
        private $connection;
        private $tblUsers = "tblUsers";

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
    }

?>