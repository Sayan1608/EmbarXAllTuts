package ioc.challenge;

public class UserDatabaseProvider implements UserDataProvider {

    @Override
    public String getUserData() {
        return "Fetching User Data from User Database.";
    }
}
