<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    $a = $_GET["a"];
    $b = $_GET["b"];
    
    echo "Kết quả: " . ($a + $b);
?>

