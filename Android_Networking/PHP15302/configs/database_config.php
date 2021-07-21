<?php
    class Database {
        private $host = "127.0.0.1:3307";
        private $database_name = "ecommerce";
        private $username = "root";
        private $password = "123456";

        public $connection;

        public function getConnection()
        {
            $this->connection = null;
            try {
                $this->connection= new PDO("mysql:host=" . $this->host .
                                            "; dbname=" . $this->database_name,
                                            $this->username, $this->password);
                $this->connection->exec("set names utf8");                            
            } catch (Exception $e) {
                echo "Connection " . $e->getMessage();
            }
            return $this->connection;
        }
    }
?>