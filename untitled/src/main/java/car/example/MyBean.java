package car.example;

public class MyBean {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void displayMessage(){
        System.out.println("Message : " + message);
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
