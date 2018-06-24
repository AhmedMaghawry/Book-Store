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

if (isset($_GET['num'])) {
  $num1 = $_GET['num'];
  $num2 = $num1 + 100;
  // get all books from books table
  $result = mysqli_query($con2, "Select * from Book_Store.Book LIMIT $num1, $num2");
} else {
  // get all books from books table
  $result = mysqli_query($con2, "Select * from Book_Store.Book");

}
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
        $isb = $book["isbn"];
        $book["title"] = $row["Title"];
        $book["pub"] = $row["Publisher_Name"];
        $book["year"] = $row["Publication_Year"];
        $book["price"] = $row["Selling_Price"];
        $book["cat"] = $row["Category"];
        $book["num"] = $row["no_Copies"];
        $book["min"] = $row["Min_quantity"];

        $auth = mysqli_query($con2, "select * from Book_Store.Book_has_Author where Book_ISBN = $isb");
        if ($auth) {
          // check for empty result
          if (mysqli_num_rows($auth) > 0) {
              $i = 0;
              while ($rowN = mysqli_fetch_array($auth)) {
                $authors[$i] = $rowN["Author_Author_name"];
                $i++;
              }
            }
          $book["auth"] = $authors;
        } else {
          $book["auth"] = $authors;
        }
        // push single book into final response array
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
    $response["msg"] = "No Rows";
    // echo no users JSON
    echo json_encode($response);
}
} else {
  $response["success"] = 0;
  $response["msg"] = mysqli_error($con2);
  // echo no users JSON
  echo json_encode($response);
}
mysqli_close($con2);
?>
