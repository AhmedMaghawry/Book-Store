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


if (isset($_GET['username']) && isset($_GET['password'])) {

    // receiving the GET params
    $User = $_GET['username'];
    $Pass = base64_encode($_GET['password']);

    $result = mysqli_query($con2, "select * from Book_Store.Manager where username = '$User' && password = '$Pass';");
    if ($result) {
    // check for empty result
    if (mysqli_num_rows($result) > 0) {
        // looping through all results
        // users node
        $response["user"] = array();
        $row = mysqli_fetch_array($result);
            $user = array();
            $user["username"] = $row["username"];
            $user["password"] = base64_decode($row["password"]);
            $user["firstN"] = $row["firstname"];
            $user["lastN"] = $row["lastname"];
            $user["email"] = $row["email"];
            $user["phone"] = $row["phone"];
            $user["ship"] = $row["shipping_address"];
            array_push($response["user"], $user);
        // success
        $response["success"] = 1;
        // echoing JSON response
        echo json_encode($response);
        mysqli_commit($con2);
    } else {
        // no users found
        $response["success"] = 0;
        $response["msg"] = "No user found";

        // echo no users JSON
        echo json_encode($response);
    }
  }else {
    $response["success"] = 0;
    $response["msg"] = mysqli_error($con2);
    echo json_encode($response);
  }

} else {
    // required GET params is missing
    $response["success"] = 0;
    $response["msg"] = "Wrong parameters";
    echo json_encode($response);
}
mysqli_close($con2);
?>
