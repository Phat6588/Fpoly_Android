<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    // $employeeArr = array();
    // $e = array("name" => "Nguyen van A");
    // array_push($employeeArr, $e);
    // echo json_encode($employeeArr);
    // echo "Hello Viết Châu"

    $a = $_GET['a'];
    $b = $_GET['b'];
    $c = $_GET['c'];

    $delta = $b * $b - 4 * $a * $c;

    if($a == 0){
        if($b == 0) {
            if($c == 0){
                echo "PT vo so nghiem";
            }
            else{
                echo "PT vo nghiem";
            }
        } else {
            $nghiem = -$c / $b;
            echo "PT co 1  nghiem: " . $nghiem;
        }
    }
    else {
        if($delta < 0){
            echo "PT vo nghiem";
        } elseif ($delta == 0){
            $nghiemKep = -$b / (2* $a);
            echo "PT co nghiem kep: ". $nghiemKep;
        } else {
            $nghiemMot = (-$b + sqrt($delta)) / (2* $a);
            $nghiemHai = (-$b - sqrt($delta)) / (2* $a);
            echo "PT co 2 nghiem: ". $nghiemMot . "    " . $nghiemHai;
        }
    }
    


?>