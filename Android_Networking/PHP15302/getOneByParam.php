<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


// http://10.0.2.2:8081/getOneByParam.php?id=1
$number = $_GET["id"];


$arr = array();
$o = array(
    "id" => "PS12345",
    "name" => "Hôm nay trời lạnh quá. Muốn mua một vòng ôm. Nhưng mà tiền không có. Nên đành trả nụ hôn."
);
array_push($arr, $o);

$o = array(
    "id" => "PS12346",
    "name" => "Trời nay lạnh đến thế. Lại còn lất phất mưa. Tớ thích cậu nhiều thế. Cậu đã động lòng chưa."
);
array_push($arr, $o);

$o = array(
    "id" => "PS12347",
    "name" => "Mùa đông thì lạnh. Áo quần mong manh. Điều em muốn nhất. Là gần bên anh."
);
array_push($arr, $o);

$o = array(
    "id" => "PS12348",
    "name" => "Ngoài kia gió rét sương sa. Về đây có nắng, có nhà, có em"
);
array_push($arr, $o);


echo json_encode($arr[$number]);
// php -S 127.0.0.1:8081
?>

