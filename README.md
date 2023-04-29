# SkyFinder – Airplane Aggregator Application
### SkyFinder is a web-based application that aggregates and displays data related to flights and airlines from different sources. The application provides users with a single platform to search, compare, and book flights based on their preferences such as price, duration, and ratings.

## Key Features:
•	User registration and login\
•	Users can search for flights by giving input like origin, destination, and travel date. \
•	The user doesn’t need to be logged in to search for flight.\
•	By default, the application will suggest users the best departing flights based on price, duration, and previous user ratings of their journey experience. \
•	Users can sort the flights based on price (Low to High), Duration (Low to High), and Ratings (High to Low).\
•	User could book the flight only if he is logged in, otherwise it will open login page. \
•	User can see list of flight bookings made through his account.\
•	User can cancel his flight booking by going to bookings page.\
•	User can manage his profile, update his personal details, and password.\
•	User will be logged out of the application after 2 mins of inactivity.\
•	Email notifications to the user when he creates an account, books a flight, and cancels a flight booking.\
•	Admin can view all the users and delete the users.\
•	Admins of the respective airplane companies can add new flight information, update flight data like status, delete flight (CRUD).\

## Technologies Used:
Backend: Spring Boot, Spring REST, Spring JPA, Spring Security, JWT, Hibernate\
Frontend: React\
Database: H2

## Architecture

![image](https://user-images.githubusercontent.com/113478192/235295201-d4bf6d43-de0d-41cb-9421-88884a3e7198.png)

## To run the SkyFinder project, you can either clone it from GitHub or download a zip file.

For the front-end, open the terminal or VS Code, navigate to the "frontend" folder, and run the "npm start" command.\

For the back-end, import all the Spring Boot applications (HorizonAir, JetStream, SkyFinder, Skywave) into Eclipse or IntelliJ IDEA and run each application as a Java application.\

Once you have done this, you can use the project. The front-end will be accessible at "http://localhost:3000".\
