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

if (isset($_GET['username']) || isset($_GET['password']) || isset($_GET['fiName']) || isset($_GET['laName']) || isset($_GET['email']) || isset($_GET['phone']) || isset($_GET['shi'])) {

    // receiving the post params
    $User = $_GET['username'];
    $Pass = base64_encode($_GET['password']);
    $First = $_GET['fiName'];
    $Last = $_GET['laName'];
    $Email = $_GET['email'];
    $Phone = $_GET['phone'];
    $Shi = $_GET['shi'];

    $res = mysqli_query($con2, "update Book_Store.Customer set username = '$User', password = '$Pass', firstname = '$First', lastname = '$Last', email = '$Email', phone = '$Phone', shipping_address = '$Shi' where username = '$User';");

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
