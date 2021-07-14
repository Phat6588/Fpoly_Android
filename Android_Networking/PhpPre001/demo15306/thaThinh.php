<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$data = json_decode(file_get_contents("php://input"));

$number = $data->number;

$arr = array();
$e = array( "sentence" => "Mùa xuân thì có hoa đào, nhưng cho anh hỏi mùa nào có em");
array_push($arr, $e);
$e = array( "sentence" => "Tết nhà anh cũng có nem, nhưng mà anh muốn có em theo kèm");
array_push($arr, $e);
$e = array( "sentence" => "Người ta thì thích lì xì, còn em chỉ thích tánh kỳ anh thôi");
array_push($arr, $e);
$e = array( "sentence" => "Tết về xuân cũng đã sang, giờ đây chỉ muốn rước nàng về dinh");
array_push($arr, $e);
$e = array( "sentence" => "Xuân sang tết đến linh đình, mong nhanh hết dịch chúng mình cưới nhau");
array_push($arr, $e);
$e = array( "sentence" => "Bên kia cái giậu mùng tơi, tôi nuôi một mối tình thời ấu thơ");
array_push($arr, $e);
$e = array( "sentence" => "Thịt mỡ dưa hành câu đối đỏ, thì thầm nói nhỏ em yeu anh");
array_push($arr, $e);
$e = array( "sentence" => "Hôm nay trời lạnh quá. Muốn mua một vòng ôm. Nhưng mà tiền không có. Nên đành trả nụ hôn.");
array_push($arr, $e);


echo json_encode($arr[$number]);


?>