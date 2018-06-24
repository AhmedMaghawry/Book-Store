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

$result = mysqli_query($con2, "call Book_Store.getTopSelling();");
if ($result) {
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // books node
    $response["books"] = array();

    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $book = array();
        $authors = array();
        $book["isbn"] = $row["ISBN"];
        $book["title"] = $row["Title"];
        $book["pub"] = $row["Publisher_Name"];
        $book["year"] = $row["Publication_Year"];
        $book["price"] = $row["Selling_Price"];
        $book["cat"] = $row["Category"];
        $book["num"] = $row["no_Copies"];
        $book["min"] = $row["Min_quantity"];
        array_push($response["books"], $book);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
    mysqli_commit($con2);
} else {
    // no books found
    $response["success"] = 0;
    $response["msg"] = "No Book found";

    // echo no users JSON
    echo json_encode($response);
}
} else {
  $response["success"] = 0;
  $response["msg"] = mysqli_error($con2);
  echo json_encode($response);
}
mysqli_close($con2);
?>
