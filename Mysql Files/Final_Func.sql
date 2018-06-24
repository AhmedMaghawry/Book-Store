use Book_Store;

set @negative_error = '45001';

Insert into Publisher values('qwe', 'sdads', '123131213');
Insert into Book values(1, 'QQQ', 'qwe', '2010', 200, 'Art', 2, 1);
Insert into Book values(2, 'ew', 'qwe', '2010', 200, 'Art', 5, 2);
Insert into Book values(3, 'ewasd', 'qwe', '2010', 200, 'Art', 2, 5);
Insert into Book values(4, 'ewasdqw', 'qwe', '2010', 200, 'Art', 5, 2);
Insert into Book values(5, 'ewassadqw', 'qwe', '2010', 200, 'Art', 5, 2);
Insert into Book values(7, 'ewfsdaassadqw', 'qwe', '2010', 200, 'Art', -5, 2);

call addBook(6, 'QdQdQdfff', 'qwe', '2010', 200, 'Art', -2, 1);

update Book set no_Copies = -7;
update Book set no_Copies = 1
where ISBN = 2;
update Book set no_Copies = 1
where ISBN = 4;

delete from Book_Order
where ISBN = 4;

select * from Book;
select * from Publisher;
select * from Book_Order;

Drop trigger if exists Negative_Cons_update;
DELIMITER $$
CREATE trigger Negative_Cons_update
before update
on Book
for each row
begin
	if (new.no_Copies < 0) then
		SIGNAL SQLSTATE '45001' set message_text = 'Negative value updated Error !!!';
	end if;
end$$
DELIMITER ;

Drop trigger if exists Negative_Cons_insert;
DELIMITER $$
CREATE trigger Negative_Cons_insert
before insert
on Book
for each row
begin
	if (new.no_Copies < 0) then
		SIGNAL SQLSTATE '45001' set message_text = 'Negative value added Error !!!';
	end if;
end$$
DELIMITER ;


Drop trigger if exists minimum_quan_update;
DELIMITER $$
CREATE trigger minimum_quan_update
after update
on Book
for each row
begin
	if (new.no_Copies < new.Min_quantity) then
		call place_order_on_Books(new.ISBN, new.Min_quantity);
	end if;
end$$
DELIMITER ;

Drop trigger if exists minimum_quan_insert;
DELIMITER $$
CREATE trigger minimum_quan_insert
after insert
on Book
for each row
begin
	if (new.no_Copies < new.Min_quantity) then
		call place_order_on_Books(new.ISBN, new.Min_quantity);
	end if;
end$$
DELIMITER ;

Drop trigger if exists cat_check_insert;
DELIMITER $$
CREATE trigger cat_check_insert
after insert
on Book
for each row
begin
	if (new.Category not in ('Science', 'Art', 'Religion', 'History' , 'Geography')) then
		SIGNAL SQLSTATE '45002' set message_text = 'Invalid Catrgory added !!!';
	end if;
end$$
DELIMITER ;

Drop trigger if exists cat_check_update;
DELIMITER $$
CREATE trigger cat_check_update
after update
on Book
for each row
begin
	if (new.Category not in ('Science', 'Art', 'Religion', 'History' , 'Geography')) then
		SIGNAL SQLSTATE '45002' set message_text = 'Invalid Catrgory updated !!!';
	end if;
end$$
DELIMITER ;

Drop trigger if exists delete_orders;
DELIMITER $$
CREATE trigger delete_orders
before delete
on Book_Order
for each row
begin
	update Book set no_Copies = no_Copies + old.num_of_books
	where ISBN = old.ISBN;
end$$
DELIMITER ;

Drop procedure if exists addBook;
DELIMITER $$
CREATE procedure addBook(In ISBNn INT,In Titlee varchar(255),In pName varchar(255),In pYear varchar(4),In SelPri int,In cat varchar(10),In no_co int,In minQ int)
BEGIN 
	 Insert into Book
	 values(ISBNn, Titlee, pName, pYear, SelPri, cat, no_co, minQ);
END$$
DELIMITER ;

Drop procedure if exists place_order_on_Books;
DELIMITER $$
CREATE procedure place_order_on_Books(In ISBNn INT,In minQ int)
BEGIN 
	 insert into Book_Order
	 values (ISBNn, minQ);
END$$
DELIMITER ;

Drop procedure if exists get_all_books;
DELIMITER $$
CREATE procedure get_all_books()
BEGIN 
	 #select * from (Book join Book_has_Author on ISBN = Book_ISBN);
     select * from Book;
END$$
DELIMITER ;

insert into Author values ('aaa', '0122');
insert into Author values ('bbb', '0122');
insert into Book_has_Author values (1, 'aaa');
insert into Book_has_Author values (1, 'bbb');
insert into Book_has_Author values (2, 'aaa');

Drop procedure if exists Cutomer_order_on_Books;
DELIMITER $$
CREATE procedure Cutomer_order_on_Books(In ISBNn INT,In quan int)
BEGIN 
	 update Book set no_Copies = no_Copies - quan
     where ISBN = ISBNn;
END$$
DELIMITER ;


Drop procedure if exists confirm_order;
DELIMITER $$
CREATE procedure confirm_order(In ISBNn INT)
BEGIN 
	delete from Book_Order
	where ISBN = ISBNn;
END$$
DELIMITER ;

Drop procedure if exists login_user;
DELIMITER $$
CREATE procedure login_user(In userNamee varchar(255), In passwordd varchar(255))
BEGIN 
	select * from Customer where username = userNamee && password = passwordd;
END$$
DELIMITER ;

select * from Customer;
Drop procedure if exists login_manager;
DELIMITER $$
CREATE procedure login_manager(In userNamee varchar(255), In passwordd varchar(255))
BEGIN 
	select * from Manager where username = userNamee && password = passwordd;
END$$
DELIMITER ;

Drop procedure if exists promote;
DELIMITER $$
CREATE procedure promote(In userNamee varchar(255))
BEGIN
	declare usr varchar(255);
    declare psw varchar(45);
    declare fi varchar(255);
    declare la varchar(255);
    declare em varchar(255);
    declare ph varchar(11);
    declare sh varchar(255);
    SET usr = (select username
				from Customer
                where username = userNamee);
	SET psw = (select password
				from Customer
                where username = userNamee);
	SET fi = (select firstname
				from Customer
                where username = userNamee);
	SET la = (select lastname
				from Customer
                where username = userNamee);
	SET em = (select email
				from Customer
                where username = userNamee);
	SET ph = (select phone
				from Customer
                where username = userNamee);
	SET sh = (select shipping_address
				from Customer
                where username = userNamee);
	delete from Customer where username = userNamee;
    insert into Manager values (usr, psw, fi, la, em, ph, sh);
END$$
DELIMITER ;

call search(-1,'','','aaa','');
Drop procedure if exists search;
DELIMITER $$
CREATE procedure search(In ISBNn INT,In titlee varchar(255), In Publisher_Nam varchar(255), In auth varchar(255),In Categore varchar(10))
BEGIN 
	if (ISBNn != -1) then
		select * from Book
        where ISBN = ISBNn;
    elseif (titlee != '') then
		select * from Book
        where Title = titlee;
    elseif (Publisher_Nam != '') then
		select * from Book
        where Publisher_Name = Publisher_Nam;
    elseif (auth != '') then
		select * from Book join Book_has_Author
        on ISBN = Book_ISBN
        where Author_Author_name = auth;
    elseif (Categore != '') then
		select * from Book
        where Category = Categore;
	else
		select 'There is no search selected';
    end if;
END$$
DELIMITER ;


Drop procedure if exists getTotalSales;
DELIMITER $$
CREATE procedure getTotalSales()
BEGIN 
	select sum(total_price) as total from Customer_has_Book
    where datey > DATE_SUB(NOW(), INTERVAL 1 MONTH);
END$$
DELIMITER ;


Drop procedure if exists getCustomers;
DELIMITER $$
CREATE procedure getCustomers()
BEGIN 
	select c.username, password, firstname, lastname, email, phone, shipping_address, sum(total_price) as tot
    from (Customer as c  join Customer_has_Book as b on c.username = b.username)
    where b.datey > DATE_SUB(NOW(), INTERVAL 3 MONTH)
    group by username
    order by tot desc
    limit 5;
END$$
DELIMITER ;

Drop procedure if exists getTopSelling;
DELIMITER $$
CREATE procedure getTopSelling()
BEGIN 
	select ISBN, Title, Publisher_Name, Publication_Year, Selling_Price, Category, no_Copies, Min_quantity, sum(total_price) as tot
    from (Book join Customer_has_Book on ISBN = Book_ISBN)
    where datey > DATE_SUB(NOW(), INTERVAL 3 MONTH)
    group by Book_ISBN
    order by tot desc
    limit 10;
END$$
DELIMITER ;

insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(2,'as', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(2,'as', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(2,'as', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(2,'as', 30, '2018-06-10');

insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(3,'b', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(4,'c', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(5,'d', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(7,'e', 90, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(7,'f', 30, '2018-06-10');
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(556,'g', 50, '2018-06-10');

call search(5, '', '', '', '');


insert into Author values('ASDASD', '0212313');
insert into Author values('ASddDASD', '0212313');
select * from Author;
insert into Publisher values ('dddd', 'dddaa', '55656');
insert into Book values(1,'sda','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(2,'sdad','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(3,'sdaa','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(4,'sddda','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(5,'sdfa','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(6,'sdacv','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(7,'sdaad','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(8,'sdafd','dddd','2010-05-06',12,'Art',22, 5);
insert into Book values(9,'sdga','dddd','2010-05-06',12,'Art',22, 5);
select * from Book;
call Cutomer_order_on_Books(1,20);
update Book set Category = 'Art';
update Book set no_Copies = -9
     where ISBN = 1;
delete * from Book;
insert into Manager values('aa', 'aa', 'qq','dd','dds', 'dsda', 'ds');
select * from Manager;
select * from Book_Order;
select * from Customer;
insert into Customer values('ads', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('b', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('c', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('d', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('e', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('f', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('g', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('h', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Customer values('i', 'sdsa', 'qqda', 'ssda', 'dasda', '54456', 'asd');
insert into Book_Order values(1,1);
insert into Customer_has_Book (Book_ISBN, username, total_price, datey) values(2,'as', 30, '2015-02-03');
select * from Customer_has_Book;
select * from Book;
LOAD DATA LOCAL INFILE '/home/default/Desktop/LAB4_SQL/book.csv' IGNORE 
INTO TABLE Book
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/default/Desktop/LAB4_SQL/publisher.csv' IGNORE 
INTO TABLE Publisher
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/default/Desktop/LAB4_SQL/author.csv' IGNORE 
INTO TABLE Author
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/default/Desktop/LAB4_SQL/bookHasAuth.csv' IGNORE 
INTO TABLE Book_has_Author
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n';