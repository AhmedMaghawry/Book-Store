# Book Store project

- A bookstore application is a database system which has the capabilities of storing and retrieving information about books, customers, and orders.

- In this project, we are considering to develop a sample centralized relational Bookstore application database for the customers and managers at a book store.

- This database can be used to store the records of customers and their preferences, the technical/nontechnical books, magazines to which customers can be subscribed, and the customerorders to the shop (e.g. telephone orders of customer for books, etc.), to be sent to their address.

## Description :

**1. ER Diagram :**

- The design of the entity relationship model marked the start of our project. In this first step the scope of our final program was decided on. The reason for this is that every object added to this diagram would mean programming a whole new table in the final application. As work progressed the diagram was slimmed down a bit and two objects that we decided using at first were dropped later due to massive time pressure. 

![ERD](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/1.png)

**2. Database Relations :**

- For each book in the store, the system keeps the book’s ISBN number, title, author(s), publisher, publication year, selling price, and category.

- The book’s category can be one of the following: "Science", "Art", "Religion", "History" and "Geography".

- The system has to keep track of the names of publishers, their addresses and telephone numbers.

- Information about book orders is also maintained.

**3. Operations :**

1. Add new books:

To add a new book, the user enters the properties of the new book along with a threshold (the minimum quantity in stock to be maintained for that book).

2. Modify existing books

For updating an existing book, the user first searches for the book then he does the required update. For a given book, the user can update the quantity in stock when a copy or more of the book is sold. The user cannot update the quantity of a book if this update will cause the quantity of a book in stock to be negative. (hint: trigger before update).

3. Place orders on books

An order with constant quantity is placed only when the quantity of a book drops from above a given threshold (the minimum quantity in stock) to below the given threshold (hint: trigger after update in books table).

4. Confirm orders

The user can confirm an order when receiving the ordered quantity from the book’s publisher; the quantity of the book in store automatically increases with the quantity specified in the order. Assume that deleting the order means that the order is received from publisher. (hint: trigger before deletion from orders table).

5. Search for books

The user can search for a book by ISBN, and title. The user can search for books of a specific Category, author or publisher.

5. Customers and Managers

The Login can be done by a customer or a manager which will differ the UI where the manager has more functionalities than the customer.

**Customer functionalities :**

1. Edit his personal information including his password.

2. Search for books by any of the book’s attributes. (Use indices to speed up searches when possible).

3. Add books to a shopping cart.

4. Manage his shopping cart. This includes the following.

• View the items in the cart

• View the individual and total prices of the books in the cart

• Remove items from the cart

5. Checkout a shopping cart

• The customer is then required to provide a credit card number and its expiry date, This transaction is completed successfully if the credit card information is appropriate.

• The book’s quantities in the store are updated according to this transaction.

6. Logout of the system

• Doing this will remove all the items in the current cart.

**Manager functionalities :**

1. Add new books

2. Modify existing books

3. Place orders for books

4. Confirm orders

5. Promote registered customers to have managers credentials

6. View the following reports on sales

a. The total sales for books in the previous month

b. The top 5 customers who purchase the most purchase amount in descending order for the last three months

c. The top 10 selling books for the last three months

**4. Android :**

- Use Async Task class to get the result from the API , This operation is done in background and each on a thread.

- We use a Recycle view which is used on big data (Like facebook posts) which it doesn’t bring the data to the foreground until some previous are loaded ( uses Cache ), also we limit the returned tuples to only 100 at a time.

- Validation checks are made to check the entering fields , and also limit the choice of the category to the user.

- Interactive design which any action have an effect on the views this affect will be shown immediately on the UI.

***Welcome Page***

![Welcome](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/2.png)

***Customer Pages***

![Customer Home](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/3.png)
![Book Into](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/4.png)
![Confirm Cart](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/5.png)
![Credit](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/6.png)

***Manager Pages***

![Manager Home](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/7.png)
![Book Into](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/8.png)
![Confirm](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/9.png)
![Add Book](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/10.png)
![Promote](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/11.png)
![Confirm](https://github.com/AhmedMaghawry/Book-Store/blob/master/photos%20Report/12.png)

---

**How to Run:**
1. Install lampp
2. Add the ```Android_DB_connect``` file to htdocs in the src of the lampp
3. run the apache server on the localhost
4. connect your device to a wifi hotspot from the host PC
5. Run the application
6. Enjoy
