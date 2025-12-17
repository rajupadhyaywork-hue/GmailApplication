package GmailApplication;

import javax.lang.model.type.ArrayType;
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
            System.out.println("3. FORGOT PASSWORD");
            System.out.println("4. EXIT");
            System.out.print("\n Enter your option: ");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            sc.nextLine();
            // Navigate based on user input
            switch (option) {
                case 1 -> createAccount();//Register new user
                case 2 -> login();        //Login Exiting user
                case 3 -> forgotPassword();
                case 4 -> System.exit(0);// close the application
                default -> System.out.println("\n INVALID OPTION \n");
            }
        }
    }

    private void forgotPassword() {
        System.out.println("\n FORGOT PASSWORD MODULE \n");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Registered Email : ");
        String email = sc.nextLine().trim();

        if (!email.endsWith("@gmail.com")) {
            email += "@gmail.com";
        }
        User foundUser = null;
        for (User user : userList) {
            if (user.getMail().equals(email)) {
                foundUser = user;
                break;
            }
        }
        if (foundUser == null) {
            System.out.println("\n ❌ User not Found! \n");
            return;
        }
        System.out.print("Enter DOB :");
        String dob = sc.nextLine().trim();

        if (!foundUser.getDob().equals(dob)) {
            System.out.println("\n ❌ DOB does not match! \n");
            return;
        }

        String newPassword;
        while (true) {
            System.out.print("Enter New Password : ");
            newPassword = sc.nextLine();

            if (newPassword.contains(" ")) {
                System.out.println("\n❌ Password does not contain space! \n");
                continue;
            }
            if (!newPassword.matches("^[A-Za-z](?=.*[@#$%&!]).{5,}$")) {
                System.out.println("""
                        ❌ INVALID PASSWORD!
                        ✔ Must start with alphabet
                        ✔ Must contain one special char (@,#,$,%,&,!)
                        ✔ Minimum 6 characters
                        """);
                continue;
            }
            break;
        }
        foundUser.setPassword(newPassword);
        foundUser.resetLoginAttempts();
        foundUser.unlockAccount();

        System.out.println("\n✅ Password reset successful!");
        System.out.println("✅ Account unlocked. Please login again.\n");

    }

    // LOGIN MODULE
    private void login() {
        System.out.println("\n LOGIN MODULE ");
        Scanner sc = new Scanner(System.in);
        System.out.print("Email Id :");

        //Get user email input
        String userMail = sc.nextLine().trim();

        // Email must not contain Space
        if (userMail.contains(" ")) {
            System.out.println("\n Email must not contain spaces!!\n");
            return;//stop login
        }
        // Autocorrect id user doesn't type @gmail.com
        if (!(userMail.endsWith("@gmail.com"))) {
            userMail += "@gmail.com";
        }
        User foundUser = null;

        for (User user : userList) {
            if (user.getMail().equals(userMail)) {
                foundUser = user;
                break;
            }
        }
        if (foundUser == null) {
            System.out.println("\n User not found! \n");
            return;
        }
        if (foundUser.isLocked()) {
            System.out.println("\n Account is locked. Please reset password.\n");
            return;
        }
        System.out.print("Password : ");
        String password = sc.nextLine();

        if (!foundUser.getPassword().equals(password)) {
            foundUser.incrementLoginAttempts();
            int remaining = 3 - foundUser.getLoginAttempts();

            if (foundUser.isLocked()) {
                System.out.println("\n Account locked after 3 failed attempts!\n");
            } else {
                System.out.println("\n Wrong password! Attempt left :" + remaining + "\n");
            }
            return;
        }
        foundUser.resetLoginAttempts();
        System.out.println("\n LOGIN SUCCESSFULLY! \n");
        homePage(foundUser);
    }

    // Account creation module
    private void createAccount() {
        System.out.println("\n CREATE ACCOUNT MODULE ");
        Scanner sc = new Scanner(System.in);
        System.out.print("First Name : ");
        String firstName = sc.next();

        System.out.print("Last Name : ");
        String lastName = sc.next();
        sc.nextLine();

        System.out.print("Contact : ");
        long contact = sc.nextLong();
        sc.nextLine();
        String mail = null;

        //Loop until user enters unique email
        outerLoop:
        for (; ; ) {
            System.out.print("Email : ");
            mail = sc.nextLine().trim();

            //Email must not contain spaces
            if (mail.contains(" ")) {
                System.out.println("\n Email cannot contain spaces! TRY AGAIN.\n");
                continue;//ask again
            }
            // Email must contain '@'
            if (!mail.contains("@")) {
                System.out.println("\n Email must contain '@' symbol \n");
                continue;
            }
            // '@' should not be first or last character
            if (mail.startsWith("@") || mail.endsWith("@")) {
                System.out.println("\n ❌Invalid position of @ in email! ✅ Correct format EX:username@gmail.com");
                continue;
            }
            // Email must end with @gmail.com
            if (!mail.endsWith("@gmail.com")) {
                System.out.println("\n Email must end with '@gmail.com' \n");
                continue;
            }

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
                    int opt = sc.nextInt();

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
        String dob = sc.next();

        while (true) {
            System.out.print("Password : ");
            String password = sc.next();

            //No space allowed
            if (password.contains(" ")) {
                System.out.println("\n Password cannot contain Space! TRY AGAIN.\n");
                continue;
            }
            // Validate password format
            if (!password.matches("^[A-Za-z](?=.*[@#$%&!]).{5,}$")) {
                System.out.println("""
                        ❌ INVALID PASSWORD! Follow these rules:
                           - Must start with an alphabet
                           - Must contain at least ONE special character (@,#,$,%,&,!)
                           - Must be at least 6 characters long
                        """);
                continue;
            }
            User newUser = new User(firstName + " " + lastName, contact, mail, dob, password);

            userList.add(newUser);
            System.out.println("\n Account Created Successfully! \n");
            // If valid break
            break;
        }
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
            System.out.println("2. Draft Mail");
            System.out.println("3. Send Mails");
            System.out.println("4. Inbox Mail");
            System.out.println("5. All Mail");
            System.out.println("6. Starred Mail");
            System.out.println("7. Trash/Bin Mai1");
            System.out.println("8. Logout");
            System.out.print("Enter an option: ");
            int option = new Scanner(System.in).nextInt();
            switch (option) {
                case 1 -> composeMail(user);
                case 2 -> draftModule(user);
                case 3 -> sendModule(user);
                case 4 -> inboxModule(user);
                case 5 -> allMailModule(user);
                case 6 -> starredMailModule(user);
                case 7 -> transhModule(user);
                case 8 -> logout(user);
            }
        }
    }

    private void transhModule(User user) {
        System.out.println("\n ** TRASH MODULE *** ");

        ArrayList<Mail> binList = user.getBinMail();

        if(binList.size()==0){
            System.out.println("\n TRASH IS EMPTY \n");
            return;
        }
        int srno = 1;

        for(Mail mail : binList){
            System.out.println("SRNO : "+srno++);
            mail.getMailInfo();
            System.out.println("---------------------------");
        }
        System.out.println("\n 1. Restore Mail");
        System.out.println(" 2. Delete Forever");
        System.out.println("3. Back");
        System.out.print("Enter option :");
        int opt = new Scanner(System.in).nextInt();

        switch (opt){
            case 1 -> restoreMail(user);
            case 2 -> deleteForever(user);
            case 3 ->{
                return;
            }
        }
    }

    private void deleteForever(User user) {
        ArrayList<Mail> binList = user.getBinMail();

        if(binList.size() == 0){
            System.out.println("\n TRASH IS EMPTY \n");
            return;
        }
        System.out.print("Enter mail number to delete permanently : ");
        int opt = new Scanner(System.in).nextInt();

        if(opt<1 || opt>binList.size()){
            System.out.println("\n INVALID SELECTION \n");
            return;
        }

        //permanent deletion
        binList.remove(opt-1);
        System.out.println("\n MAIL DELETED PERMANENTLY ❌ \n");
    }

    private void restoreMail(User user) {
        ArrayList<Mail> binList = user.getBinMail();

        if(binList.size() == 0){
            System.out.println("\n TRASH IS EMPTY \n ");
            return;
        }
        System.out.print("Enter mail number to restore : ");
        int opt = new Scanner(System.in).nextInt();

        if(opt<1 || opt>binList.size()){
            System.out.println("\n INVALID SECTION \n");
            return;
        }
        Mail restoreMais = binList.remove(opt-1);

        if(restoreMais.getReceiverMail().equals(user.getMail())){
            user.inboxMail(restoreMais);
            System.out.println("\n MAIL RESTORED TO INBOX \n");
        }else{
            user.sendMail(restoreMais);
            System.out.println("\n MAIL RESTORED TO SEND \n");
        }
    }

    // LOGOUT FUNCTION
    private void logout(User user) {
        System.out.println("\n THANK U " + user.getName() + " FOR USING GMAIL \n");
        launchApplication();
    }

    private void starredMailModule(User user) {
        System.out.println("\n ⭐ STARRED MAIL MODULE \n");

        ArrayList<Mail> starList = user.getStarredMail();

        if(starList.size()==0){
            System.out.println("\n NO STARRED MAILS \n");
            return;
        }
        int srno =1;
        for(Mail mail : starList){
            System.out.println("SRNO :"+srno++);
            mail.getMailInfo();
            System.out.println("---------------------");
        }
        System.out.println("\n1. Unstar Mail");
        System.out.println("2. Back");
        System.out.print("Enter Option :");
        int opt = new Scanner(System.in).nextInt();

        if(opt == 1){
            unstarMail(user);
        }
    }
    private void unstarMail(User user){
        ArrayList<Mail> starList = user.getStarredMail();

        if(starList.size()==0){
            System.out.println("\n NO STARRED MAILS\n");
            return;
        }
        System.out.print("Enter mail number to unstar : ");
        int opt = new Scanner(System.in).nextInt();

        if(opt<1||opt>starList.size()){
            System.out.println("\n INVALID SELECTION \n");
            return;
        }
        Mail mail =starList.get(opt-1);

        //user handles actual removal
        user.unstarMail(mail);
        System.out.println("\n MAIL UNSTARRED ⭐❌ \n");
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

        if(inboxList.size() == 0){
            System.out.println("\n NO INBOX MAILS \n");
            return;
        }
        int srno = 1;
        for (Mail ele : inboxList) {
            System.out.println("SRNO : "+srno++);
            ele.getMailInfo(); // print mail info
            System.out.println("__________________________");
        }
        System.out.println("\n 1. Delete Mail");
        System.out.println(" 2. Star Mail");
        System.out.println(" 3. Back");
        System.out.print("Enter option :");
        int opt = new Scanner(System.in).nextInt();

        if(opt == 1){
            deleteMail(inboxList,user,"INBOX");
        }
        else if(opt==2){
            starMailFromList(user,inboxList);
        }
    }

    private void starMailFromList(User user, ArrayList<Mail> sourceList) {

        if (sourceList.size() == 0) {
            System.out.println("\n NO MAILS AVAILABLE \n");
            return;
        }

        System.out.print("Enter mail number to star : ");
        int opt = new Scanner(System.in).nextInt();

        if (opt < 1 || opt > sourceList.size()) {
            System.out.println("\n INVALID SELECTION \n");
            return;
        }

        Mail mail = sourceList.get(opt - 1);

        user.starMail(mail);

        System.out.println("\n MAIL STARRED ⭐ \n");
    }


    // show sent module
    private void sendModule(User user) {
        System.out.println("\n ** SEND MODULE **");

        ArrayList<Mail> sendList = user.getSendMail();// retrieve sent mail

        if(sendList.size() == 0) {
            System.out.println("\n NO SENT MAILS \n");
            return;
        }
        for (Mail ele : sendList) {
            ele.getMailInfo();
            System.out.println("__________________________");
        }

        System.out.println("\n 1. Delete Mail");
        System.out.println(" 2. Back");
        System.out.print("Enter option :");
        int opt = new Scanner(System.in).nextInt();

        if(opt == 1){
            deleteMail(sendList,user,"SENT");
        }

    }

    // Draft Module(Edit/send)
    private void draftModule(User user) {
        System.out.println("\n DRAFT MAIL MODULE \n");
        ArrayList<Mail> draftList = user.getDraftMail();

        if (draftList.size() == 0) {
            System.out.println("\n NO DRAFTS FOUND \n");
            return;
        }
        int srno = 1;
        // Show all the draft
        for (Mail ele : draftList) {
            System.out.println("\n SRNO DRAFT: " + srno++);
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

            System.out.println("Do u want to \n1. Edit \n2. send \n3. Delete \n 4. Back");
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
            }else if(opt == 3){
                deleteMail(draftList, user, "DRAFT");
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
        for (; ; ) {
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

    private void deleteMail(ArrayList<Mail> sourceList, User user, String sourceName) {

        if (sourceList.size() == 0) {
            System.out.println("\n NO MAIL TO DELETE IN"+ sourceName + "\n");
            return;
        }
        int srno =1;
        for(Mail mail : sourceList){
            System.out.println("SRNO : "+srno++);
            mail.getMailInfo();
            System.out.println("-------------------");
        }
        System.out.print("Enter mail number to delete : ");
        int opt = new Scanner(System.in).nextInt();

        if(opt<1||opt>sourceList.size()){
            System.out.println("\n INVALID SELECTION \n");
            return;
        }
        Mail deleteMail = sourceList.remove(opt-1);
        user.getBinMail().add(deleteMail);

        System.out.println("\n MAIL MOVED TO TRASH SUCCESSFULLY \n");
    }

}
