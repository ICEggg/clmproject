package org.clm.demo.abstractPackage;

public abstract class AbstractLogin {
    public String username;
    public String password;

    public AbstractLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    abstract void login();
}
