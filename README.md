# SQLitePasswordProtected
Android Sqlite Password protected database

# Overview

The app does the following:

This application basically does sqlite database as a password protected using 
third party libarary.
('net.zetetic:android-database-sqlcipher:4.3.0@aar')
App having 2 pages Save Data and Display Data which will save data into sqlite database and display data 
form the sqlite database.

# To achieve this, 
 - Designed sqlite databse
 - Integtaed third party library
 - Designed two pages
 - Design the AES-SHA-256 Alogrithm for Encryption and Decryption

# Once application launch then Save Data page will display to user with four fields:
- Email
- Name
- Phone
- Address

# MainActivity 

- Responsible for executing Save button click event and capture the data from first page and save that in sqlite 
database with encrypted formate
associated with email. Data is save along with email if email is alredy saved in database that data will update
not add duplicate entry of that email.


# SecondActivity

- Responsible for open the Display data page and fetch data from the sqlite and decrypt it and display
on the page.

# Used Security 

- AES SHA-256 Alogorithm for encryption and decyption technique.

# Libraries
This app leverages android X.
This app use the third party library for making sqlite database as password protected.
- 'net.zetetic:android-database-sqlcipher:4.3.0@aar'


# For Test App
Clone or Download from this repo.
Open downlaoded project in android studio and just run.
