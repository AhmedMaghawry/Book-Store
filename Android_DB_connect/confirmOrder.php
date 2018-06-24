<?php
// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// Connecting to mysql database
$con2 = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysqli_error());
mysqli_autocommit($con2,FALSE);

if (isset($_GET['isbn'])) {

    // receiving the post params
    $Isbn = $_GET['isbn'];

    $res = mysqli_query($con2, "call Book_Store.confirm_order('$Isbn');");

    if ($res) {
        $response["success"] = 1;
        echo json_encode($response);
        mysqli_commit($con2);
    } else {
        $response["success"] = 0;
        $response["msg"] = mysqli_error($con2);
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["success"] = 0;
    $response["msg"] = "Wrong Params";
    echo json_encode($response);
}
mysqli_close($con2);
?>
