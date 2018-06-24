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
  // get all books from books table
  $result = mysqli_query($con2, "Select * from Book_Store.Book_Order;");

if ($result) {
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // books node
    $response["orders"] = array();

    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $order = array();
        $order["isbn"] = $row["ISBN"];
        $order["num"] = $row["num_of_books"];
        // push single book into final response array
        array_push($response["orders"], $order);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
    mysqli_commit($con2);
} else {
    // no books found
    $response["success"] = 0;
    $response["msg"] = "No Rows";

    // echo no users JSON
    echo json_encode($response);
}
} else {
  $response["success"] = 0;
  $response["message"] = mysqli_error($con2);
  echo json_encode($response);
}
mysqli_close($con2);
?>
