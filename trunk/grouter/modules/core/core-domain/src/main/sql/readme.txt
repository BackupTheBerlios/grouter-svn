
0. Drop if you have any existing grouter database already.
mysqladmin -u root -pXYZ DROP grouter;


Creating a new database>
1. Start up mysql
> sudo ./mysql/bin/mysqld_safe

2. Issue create
> mysqladmin -u root -pthepwd  CREATE grouter

3. Grant grouter user
mysql> GRANT ALL ON grouter.* TO 'grouter'@'localhost' IDENTIFIED BY 'grouter';

4. Login as grouter
mysql>  -p -u grouter
GRANT ALL PRIVILEGES ON *.* TO 'cex'@'%' IDENTIFIED BY 'cex' WITH GRANT OPTION;