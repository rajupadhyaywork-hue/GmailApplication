package GmailApplication;

import java.util.ArrayList;
import java.util.Scanner;

public class Gmail {
    //Store all registered users
    ArrayList<User> userList = new ArrayList<User>();

    //main Application
    public void launchApplication() {
        for (; ; ) {//infinite loop
            System.out.println("\n ***** WELCOME TO GMAIL ******");
            System.out.println("1. CREATE ACCOUNT");
            System.out.println("2. LOGIN");
            System.out.println("3. EXIT");
            System.out.print("\n Enter your option: ");
            int option = new Scanner(System.in).nextInt();

            // Navigate based on user input
            switch (option) {
                case 1 -> createAccount();//Register new user
                case 2 -> login();        //Login Exiting user
                case 3 -> System.exit(0);// close the application
                default -> System.out.println("\n INVALID OPTION \n");
            }
        }
    }
    // LOGIN MODULE
    private void login() {
        System.out.println("\n LOGIN MODULE ");
        System.out.print("Email Id :");

        //Get user email input
        String userMail = new Scanner(System.in).next();

        // Autocorrect id user doesn't type @gmail.com
        if (!(userMail.endsWith("@gmail.com"))) {
            userMail += "@gmail.com";
        }

        System.out.print("Password : ");
        String userPassword = new Scanner(System.in).next();

        //Validate user mail And password
        for (User user : userList) {
            if (userMail.equals(user.getMail()) && userPassword.equals(user.getPassword())) {
                homePage(user); //Navigate to home page
            }
        }
        System.out.println("\n INVALID CRED \n");
    }

    // Account creation module
    private void createAccount() {
        System.out.println("\n CREATE ACCOUNT MODULE ");
        System.out.print("First Name : ");
        String firstName = new Scanner(System.in).next();

        System.out.print("Last Name : ");
        String lastName = new Scanner(System.in).next();

        System.out.print("Contact : ");
        long contact = new Scanner(System.in).nextLong();

        String mail = null;

        //Loop until user enters unique email
        outerLoop:
        for (; ; ) {
            System.out.print("Email : ");
            mail = new Scanner(System.in).next();

            //chack if email is already exists
            for (User ele : userList) {
                if (mail.equals(ele.getMail())) {
                    System.out.println("\n MAIL ID ALREADY EXISTS \n");

                    // Generate Suggestion
                    String[] suggestion = suggestion(firstName);
                    int srno = 1;
                    for (String newMailId : suggestion) {
                        System.out.println(srno++ + " : " + newMailId);
                    }
                    System.out.println("4.No-Suggestion");
                    System.out.print("\nEnter the srno : ");
                    int opt = new Scanner(System.in).nextInt();

                    // pick suggested mail
                    if (opt == 1) {
                        mail = suggestion[0];
                        break outerLoop;
                    } else if (opt == 2) {
                        mail = suggestion[1];
                        break outerLoop;
                    } else if (opt == 3) {
                        mail = suggestion[2];
                        break outerLoop;
                    }
                    continue outerLoop;
                }
            }
            break;
        }
        System.out.print("DOB :");
        String dob = new Scanner(System.in).next();

        System.out.print("Password : ");
        String password = new Scanner(System.in).next();

        // Create new user object
        User newUser = new User(firstName + " " + lastName, contact, mail, dob, password);

        // Add to user list
        userList.add(newUser);

    }

    // Generate 3 random email suggestion
    private String[] suggestion(String name) {
        String[] suggestion = new String[3];

        for (int i = 0; i <= 2; i++) {
            String randomNumber = "";

            // Generate 4 digit random number
            for (int j = 1; j <= 4; j++) {
                int dgt = (int) (Math.random() * 10);
                randomNumber += dgt;
            }

            String gmail = name + randomNumber + "@gmail.com";

            // Ensure suggestion is unique
            for (User ele : userList) {
                if (gmail.equals(ele.getMail())) {
                    i--;
                    continue;
                }
            }
            suggestion[i] = gmail;
        }
        return suggestion;
    }


    // HOME PAGE MODULE AFTER LOGIN
    private void homePage(User user) {
        for (; ; ) {
            System.out.println("\n *** HOME PAGE MODULE **** \n");
            System.out.println("1. Compose Mail");
            System.out.println("2. Draft");
            System.out.println("3. Send Mails");
            System.out.println("4. Inbox Mail");
            System.out.println("5. All Mail");
            System.out.println("6. Starred Mail");
            System.out.println("7. Logout");
            System.out.print("Enter an option: ");
            int option = new Scanner(System.in).nextInt();
            switch (option) {
                case 1 -> composeMail(user);
                case 2 -> draftModule(user);
                case 3 -> sendModule(user);
                case 4 -> inboxModule(user);
                case 5 -> allMailModule(user);
                case 6 -> starredMailModule(user);
                case 7 -> logout(user);
            }
        }
    }

    // LOGOUT FUNCTION
    private void logout(User user) {
        System.out.println("\n THANK U " + user.getName() + " FOR USING GMAIL \n");
        launchApplication();
    }

    private void starredMailModule(User user) {
        System.out.println("\n COMING SOON \n");
    }

    // Show both sent & inbox mails
    private void allMailModule(User user) {
        System.out.println("\n ALL MAIL MODULE \n");
        sendModule(user);
        System.out.println("***************************");
        inboxModule(user);
    }

    //Show inbox mail
    private void inboxModule(User user) {
        System.out.println("\n INBOX MODULE \n");

        ArrayList<Mail> inboxList = user.getInboxMail(); // get inbox list

        for (Mail ele : inboxList) {
            ele.getMailInfo(); // print mail info
            System.out.println("__________________________");
        }
    }

    // show sent module
    private void sendModule(User user) {
        System.out.println("\n SEND MODULE \n");

        ArrayList<Mail> sendList = user.getSendMail();// retrieve sent mail
        for (Mail ele : sendList) {
            ele.getMailInfo();
            System.out.println("__________________________");
        }
    }

    // Draft Module(Edit/send)
    private void draftModule(User user) {
        System.out.println("\n DRAFT MAIL MODULE \n");
        ArrayList<Mail> draftList = user.getDraftMail();

        if (draftList.size() == 0) {
            System.out.println("\n NO DRAFTS FOUND\n");
            return;
        }
        int srno = 1;

        // Show all the draft
        for (Mail ele : draftList) {
            System.out.println("SRNO DRAFT: " + srno++);
            ele.getMailInfo();
            System.out.println("__________________________");
        }

        //Ask user if they want to send or edit a draft
        System.out.println("\n DO U WANT TO SEND/EDIT A DRAFT (YES/NO): ");
        String resp = new Scanner(System.in).next();

        if (resp.equalsIgnoreCase("YES")) {

            System.out.print("\nEnter the srno : ");
            int num = new Scanner(System.in).nextInt();

            Mail editSend = draftList.get(num - 1);//Get selected draft

            System.out.println("Do u want to \n1.Edit \n2. send ");
            System.out.print("Enter your option : ");
            int opt = new Scanner(System.in).nextInt();

            if (opt == 1) {
                editDraft(editSend);//edit draft
            } else if (opt == 2) {

                //Move mail to sent folder
                user.sendMail((editSend));

                // Deliver mail to receiver inbox
                String toUserMail = editSend.getReceiverMail();

                User toUserObject = null;

                for (User ele : userList) {
                    if (ele.getMail().equals(toUserMail)) {
                        toUserObject = ele;
                    }
                }
                toUserObject.inboxMail((editSend));

                // Remove draft after sending
                draftList.remove(num - 1);
                System.out.println("\nMAIL HAS BEEN SEND SUCCESSFULLY FROM DRAFT\n");
            }
        }
    }

    //Edit the draft Function
    private void editDraft(Mail editSend) {
        System.out.println(" \n EDIT DRAFT MODUlE \n");
        editSend.getMailInfo();
        System.out.print("\n What do u want to edit: ");
        System.out.println("\n1.To \n2.Subject \n3.Body ");
        System.out.print("\nEnter your option : ");
        int opt = new Scanner(System.in).nextInt();

        User toUser = null;

        //Find receiver user object
        for (User ele : userList) {
            if (editSend.getReceiverMail().equals(ele.getMail())) {
                toUser = ele;
                break;
            }
        }

        // Editing based on user choice
        switch (opt) {
            case 1 -> {

                // Edit receiver mail
                outerLoop:
                for (; ; ) {
                    System.out.print("To : ");
                    String toMail = new Scanner(System.in).next();
                    for (User ele : userList) {
                        if (toMail.equals(ele.getMail())) {
                            editSend.setReceiverMail(toMail);
                            toUser = ele;
                            break outerLoop;
                        }
                    }
                    System.out.println(" \n USER NOT FOUND \n");
                }
                break;
            }
            case 2 -> {
                System.out.print("\n Enter Subject : ");
                String newSubject = new Scanner(System.in).nextLine();
                editSend.setSubject(newSubject);
                break;
            }
            case 3 -> {
                System.out.print("\n Enter Body : ");
                String newBody = new Scanner(System.in).nextLine();
                editSend.setBody(newBody);
                break;
            }
            default -> System.out.println("\n INVALID OPTION");
        }

        // Find sender user object
        User fromUser = null;

        for (User ele : userList) {
            if (editSend.getSender().equals(ele.getMail())) {
                fromUser = ele;
                break;
            }
        }

        // Move edited draft to sent folder
        fromUser.sendMail(editSend);
        toUser.inboxMail(editSend);

        // Remove from Draft list
        int srno = 1;
        ArrayList<Mail> draftList = fromUser.getDraftMail();
        for (Mail ele : draftList) {
            if (ele.equals(editSend)) {
                break;
            }
            srno++;
        }
        fromUser.getDraftMail().remove(srno - 1);
        System.out.println("\n MAIL HAS BEEN SEND SUCCESSFULLY FROM DRAFT \n");
    }

    // COMPOSE A NEW MAIL
    private void composeMail(User user) {
        User toUser = null;
        System.out.println("\n COMPOSE MAIL \n");
        System.out.println("From : " + user.getMail());

        // Ask for receiver until valid mail is found
        outerLoop:
        for (;;) {
            System.out.print("To : ");
            String toMail = new Scanner(System.in).next();

            for (User ele : userList) {
                if (toMail.equals(ele.getMail())) {
                    toUser = ele;
                    break outerLoop;
                }
            }
            System.out.println("\n USER  NOT FOUND \n");
        }
        System.out.print("Subject : ");
        String subject = new Scanner(System.in).next();

        System.out.print("Body : ");
        String body = new Scanner(System.in).nextLine();

        //Let’s create an actual Mail object that can be stored in inbox/sent/draft.”
        Mail newMail = new Mail(user.getMail(), toUser.getMail(), subject, body);

        System.out.print("DO U WANT TO SEND :");
        String resp = new Scanner(System.in).next();


        if (resp.equalsIgnoreCase("YES")) {
            user.sendMail(newMail); // Add to senders sent
            toUser.inboxMail(newMail); // deliver to receiver inbox
        } else {
            user.draftMail(newMail);// Save draft
        }
    }

}
