package com.loose.coupling;

public class LooseCouplingExample {

    public static void main(String[] args) {
        UserDataProvider userDataProvider = new UserDatabaseProvider();
        UserManager userManager = new UserManager(userDataProvider);
        System.out.println(userManager.getUserDetails());


        userDataProvider = new WebServiceProvider();
        userManager = new UserManager(userDataProvider);
        System.out.println(userManager.getUserDetails());

    }
}
