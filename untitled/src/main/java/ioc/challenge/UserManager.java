package ioc.challenge;

public class UserManager {
    // achieved loose coupling through interface
    private final UserDataProvider userDataProvider;

    public UserManager(UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    public String getUserDetails(){
        return userDataProvider.getUserData();
    }
}
