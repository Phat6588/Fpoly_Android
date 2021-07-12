<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    // $data = json_decode(file_get_contents("php://input"));    

    $arr = array();
    $e = array(
        "id" => "PS12345",
        "name" => "Mr Đẹp trai"
    );
    array_push($arr, $e);

    echo json_encode($e);






    // echo "Name:  " . $data->name . " ID: " . $data->id;
    
    // $employeeArr = array();
    // $e = array("id" => $data->id,
    //             "name" => $data->name);
    // array_push($employeeArr, $e);
    // $e = array("id" => $data->id,
    //         "name" => "trời đất ơi");
    // array_push($employeeArr, $e);
    // echo json_encode($employeeArr);
?>

