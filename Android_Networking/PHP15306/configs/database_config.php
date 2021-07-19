<?php
    class DatabaseConfig {
        private $host = "127.0.0.1:3307";
        private $username = "root";
        private $password = "123456";
        private $database_name = "SHOPPING";

        public $connection;

        public function getConnection(){
            $this->connection = null;
            try {
                $this->connection = new PDO("mysql:host=" . $this->host .
                                            "; dbname=" . $this->database_name,
                                            $this->username, $this->password);
                $this->connection->exec("set names utf8");
            } catch (Exception $e) {   
                echo "Ket noi database khong thanh cong: " .$e->getMessage();     
            }
            return $this->connection;
        }

    }
?>