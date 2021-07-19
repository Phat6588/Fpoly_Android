<?php
    include_once '../configs/database_config.php';
    include_once '../models/user.php';
    include_once '../models/reset.php';


    class UserService {
        private $connection;
        private $tblUsers = "tblUsers";
        private $tblPasswordResets = "tblPasswordResets";

        public function __construct()
        {
            $this->connection = (new DatabaseConfig())->getConnection();
        }

        public function register($user)
        {
            try {
                $query = "INSERT INTO " . $this->tblUsers ." 
                                        SET email = :email, hash_password = :hash_password
                                        ";
                $stmt = $this->connection->prepare($query);
                
                $email = $user->getEmail();
                $password = $user->getHashPassword();

                $stmt->bindParam(":email", $email);
                $stmt->bindParam(":hash_password", $password);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {                
            }
            return false;
        }

        public function getUserByEmail($email)
        {
            try {
                $query = "SELECT id, email, hash_password FROM " . $this->tblUsers ." 
                                        WHERE email = ? LIMIT 0,1";
                $stmt = $this->connection->prepare($query);
                
                $stmt->bindParam(1, $email);
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $row = $stmt->fetch(PDO::FETCH_ASSOC);
                    extract($row);
                    return new User($id, $email, $hash_password);
                }
            } catch (Exception $e) {                
            }
            return null;
        }
    }
?>