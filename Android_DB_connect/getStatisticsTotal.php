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

$result = mysqli_query($con2, "call Book_Store.getTotalSales();");

// check for empty result
//if (mysqli_num_rows($result1) > 0 || mysqli_num_rows($result2) > 0 ||mysqli_num_rows($result3) > 0) {
if($result) {    // looping through all results
    // books node
    $row = mysqli_fetch_array($result);
    $response["total"] = $row["total"];
    $response["success"] = 1;
    echo json_encode($response);
    mysqli_commit($con2);
  } else {
    $response["success"] = 0;
    $response["msg"] = mysqli_error($con2);
    echo json_encode($response);
  }
  mysqli_close($con2);
?>
