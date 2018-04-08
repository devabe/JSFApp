# Setup #
The DB schema is automatically created with the option hbm2dll.auto in persistence.xml file :

	<property name="hibernate.hbm2ddl.auto" value="create" />	
	NOTA : Change the "create" value to "update" after first setup.

Init sql script contained in file import.sql will create a sample data inside DB. This file is autmatically detected by Hibernate.
this file will create two users in db. A simple user and and admin user :

	login=user/password=user
	login=admin/password=admin

# How it works ? #

 All pages except login and register are secured. Only authenticated users have access to the app.
 The user when he arrives on the app, has two choices :
   - login by his login/password or
   - register to create an account.
   
 Users can also be created by an Admin user. After authentication, an admin user has access to a menu "Admin panel" in which he can create, edit or delete users.
 	The simple users, when they try to access the Admin Panel, the are redirected to an access denied page.
 
 Each user has access to his profile page in order to edit his name and password.
 
 
 
 
 