<?php 
    class Database {
        private $host = "127.0.0.1:3307";
        private $database_name = "employeedb";
        private $username = "root";
        private $password = "123456";

        public $conn;

        public function getConnection(){
            $this->conn = null;
            try{
                $this->conn = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->database_name, $this->username, $this->password);
                $this->conn->exec("set names utf8");
            }catch(PDOException $exception){
                echo "Database could not be connected: " . $exception->getMessage();
            }
            return $this->conn;
        }
        
    }  

    // lab 1, 
    // lab 2: tinh tien taxi

    // php -S 127.0.0.1:8081


    // lab 3: dùng volley, retrofit
    // màn hình 1: sử dụng get(), hiển thị danh sách (list view recycler)
    // màn hình 2: sử dụng post(), hiển thị chi tiết sinh viên, 
    // có điểm trung bình, kết quả (form, nhấn nút submit, server trả về)




?>
