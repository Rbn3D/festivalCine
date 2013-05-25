## Festival de cine (Film Festival) ##

Final project of **Java Programming**, Web Application Development degree (1st course). 

The application manages Film Festivals. The admin user can create voting of films, and jurors/spectator users can vote their films, until the admin marks the Pool as finished. When the voting have been closed, juror and spectator users are able to view the voting results. Two different categories are shown, the jurors election and the spectators one.

### Features ###

 - Easy to use
 - Cool user interface (swing powered)
 - One single app, multiple profiles, based on the user who login (spectator, juror or admin)
 - Clean implementation (full OO design)
 - Spanish only interface (Sorry!)

### How to install ###

All you need is a running MySQL service in order to run the application

- Import the sql script (`pf04.sql`, found in the `sql` folder) into the MySQL database usign phpMyAdmin or MySQL Workbench.
- Adjust the username, password, url in `bd.properties` file, to match your MySQL configuration. The `bd.properties` file is located in `dao` package (src folder).
- Compile and run! You should see the login screen. In order to access to the different application areas, you should login as users with different roles. Here are the default users:

	- Spectator account: 
		- login: espectador 
		- password:  espectador
	- Juror account: 
		- login: jurado 
		- password:  jurado
	- Administrator account: 
		- login: admin 
		- password: admin

### About the autor ###

## 	Rubén Vallejo Gamboa ##


Hi, I'm Rubén, a 22 years old software developer / graphic designer from Seville (Spain).
I build desktop and web Java/.net applications using the latest frameworks such as Hibernate or Spring. I'm also able to do 3D interactive virtual walkthroughs (Architectural visualization) and i can develop video games. At the moment I'm doing a Web Application Development degree at Hermanos Machado College of Seville.

You can see my complete profile/portfolio here: [http://www.rubenvallejogamboa.com/](http://www.rubenvallejogamboa.com/ "www.rubenvallejogamboa.com")