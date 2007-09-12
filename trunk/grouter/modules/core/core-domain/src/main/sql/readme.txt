
To create a database in mysql
=============================

0. Drop if you have any existing grouter database already.
mysqladmin -u root -pXYZ DROP grouter;


1. Start up mysql the mysql demon
> sudo ./mysql/bin/mysqld_safe

2. Issue create
> mysqladmin -u root -pthepwd  CREATE grouter
And then reload the database
>mysqladmin -u root -pdmnb330 reload;

3. Grant grouter user
As root create a mingle user
mysql -u root -pdmnb330  mysql
Then create the grouter user with (INSERT...)
mysql> GRANT ALL PRIVILEGES ON grouter.* TO 'grouter'@'localhost'  IDENTIFIED BY 'grouter' WITH GRANT OPTION;

4. Login as grouter
mysql>  -p -u grouter
