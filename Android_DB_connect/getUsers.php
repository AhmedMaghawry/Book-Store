<?php

/*
 * Following code will list all the books
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// Connecting to mysql database
$con2 = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysqli_error());
mysqli_autocommit($con2,FALSE);

$response["users"] = array();
$result = mysqli_query($con2, "Select * from Book_Store.Customer;");
if ($result) {
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $user = array();
        $user["username"] = $row["username"];
        $user["password"] = $row["password"];
        $user["fi"] = $row["firstname"];
        $user["la"] = $row["lastname"];
        $user["em"] = $row["email"];
        $user["ph"] = $row["phone"];
        $user["shi"] = $row["shipping_address"];
        // push single book into final response array
        array_push($response["users"], $user);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
    mysqli_commit($con2);
} else {
    // no books found
    $response["success"] = 0;
    $response["msg"] = mysqli_error($con2);

    // echo no users JSON
    echo json_encode($response);
}
mysqli_close($con2);
?>
