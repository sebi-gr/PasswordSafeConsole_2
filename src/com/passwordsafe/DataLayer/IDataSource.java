package com.passwordsafe.DataLayer;

import com.passwordsafe.PasswordInfo;

public interface IDataSource {
    public String[] getAllStoredPasswords() throws Exception;

    public void addNewPassword(PasswordInfo info) throws Exception;

    public void deletePassword(String passwordName) throws Exception;

    public char[] getPassword(String passwordName) throws Exception;

    public void updatePassword(PasswordInfo info) throws Exception;

}
