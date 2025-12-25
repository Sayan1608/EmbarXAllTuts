package com.tight.coupling;

public class UserManager {
    // A - SQL
    // B - Postgres

    // need to change code if want to switch between dbs

    // tight coupling with UserDatabase
    private UserDatabase userDatabase = new UserDatabase();

    public String getUserDetails(){
        return userDatabase.getUserDatabase();
    }
}
