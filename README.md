📧 Gmail Console Application (Java)
📌 Project Description

This project is a console-based Gmail-like email application developed using Core Java.
It simulates real-world email functionalities such as account creation, login, composing mails, inbox management, trash handling, and starred mails using OOP principles and Java Collections.
This application is designed to strengthen understanding of Encapsulation, Has-A relationship, and real-life system flow, and it can be easily extended into a REST API in the future.

🚀 Features Implemented

👤 User Management
1. Create Account with validations
2. Login with limited attempts (Account Lock)
3. Forgot Password & Reset
4. Email normalization (case-insensitive)

🔐 Validations
* Email:
    No spaces allowed 
    Must contain @gmail.com
* Password:
    Must start with an alphabet
    Must contain at least one special character (@#$%&!)
    Minimum length: 6 characters

📬 Mail Functionalities
1. Compose Mail
2. Inbox
3. Sent Mail
4. Draft Mail
5. Delete Mail → Move to Trash
6. View Trash
7. Restore Mail from Trash
8. Permanent Delete (Delete Forever)

   ⭐ Star / Unstar Mail


🧠 Core Concepts Used
Object-Oriented Programming (OOP)
Encapsulation
Has-A Relationship
Dependency Handling
Java Collections (ArrayList)
Menu-Driven Application Design
Real-World Application Flow

🏗️ Project Structure
GmailApplication/
│
├── Gmail.java        // Main application flow & menus
├── GmailDriver.java  // Entry point (main method)
├── User.java         // User data & mail folders
├── Mail.java         // Mail entity
└── README.md

🔄 Application Flow
1. User creates an account
2. User logs in
3. User can:
       Compose mail
       View inbox / sent / drafts
       Delete mail → move to Trash
       Restore mail from Trash
       Permanently delete mail
       Star / Unstar mails
4. User logs out


🧪 Sample Test Flow
1. Create two users
2. Login as User A → send mail to User B
3. Login as User B → check Inbox
4. Delete mail → view Trash
5. Restore mail → Inbox
6. Star mail → view Starred
7. Unstar mail → removed from Starred


👨‍💻 Author
Raj Upadhyay
Java Backend Developer (Fresher)

⭐ Final Note
This project focuses on logic clarity and real-world behavior, making it an excellent foundation for backend development and API design.