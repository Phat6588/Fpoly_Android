<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    $employeeArr = array();
    $e = array(
        "name" => "Nguyen van A"
    );
    array_push($employeeArr, $e);
    echo json_encode($employeeArr);
?>