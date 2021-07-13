<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");    
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    $data = json_decode(file_get_contents("php://input"));    

    $name1 = $data->name1;
    $name2 = $data->name2;

    $arr = array();
    $e = array(
        "id" => "PS12345",
        "name" => $name1
    );
    array_push($arr, $e);

    $e = array(
        "id" => "PS54321",
        "name" => $name2
    );
    array_push($arr, $e);

    echo json_encode($arr);






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

