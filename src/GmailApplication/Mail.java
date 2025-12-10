package GmailApplication;

public class Mail {
    private String sender;
    private String receiver;
    private String subject;
    private String body;

    public Mail(String sender, String receiver, String subject, String body) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public void getMailInfo() {
        System.out.println("\n **** MAIL ****");
        System.out.println("Sender : " + sender);
        System.out.println("Receiver :" + receiver);
        System.out.println("Subject : " + subject);
        System.out.println("Body : " + body);
    }

    public String getReceiverMail() {
        return this.receiver;
    }
    public void setReceiverMail(String newReceiverMail){
        this.receiver = newReceiverMail;
    }
    public void setSubject(String newSubject){
        this.subject = newSubject;
    }
    public void setBody(String newBody){
        this.body = newBody;
    }
    public String getSender(){
        return this.sender;
    }
}
