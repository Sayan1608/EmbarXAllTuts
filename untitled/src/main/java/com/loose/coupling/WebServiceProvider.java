package com.loose.coupling;

public class WebServiceProvider implements  UserDataProvider{
    @Override
    public String getUserData() {
        return "Fetching User Data from Webservice Provider.";
    }
}
