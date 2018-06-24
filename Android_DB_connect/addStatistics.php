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

if (isset($_GET['isbn']) && isset($_GET['username']) && isset($_GET['total']) && isset($_GET['date'])) {

    // receiving the post params
    $ISBN = $_GET['isbn'];
    $username = $_GET['username'];
    $total = $_GET['total'];
    $daty = $_GET['date'];

    $res = mysqli_query($con2, "insert into Book_Store.Customer_has_Book (Book_ISBN, username, total_price, datey) values($ISBN,'$username', $total, '$daty');");

    if ($res) {
        $response["success"] = 1;
        mysqli_commit($con2);
        echo json_encode($response);
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
