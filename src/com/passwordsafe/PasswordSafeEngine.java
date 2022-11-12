package com.passwordsafe;;
import com.passwordsafe.DataLayer.IDataSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordSafeEngine {
    private final IDataSource dataSource;
    private CipherFacility cipherFacility;
    public PasswordSafeEngine(IDataSource dataSource, CipherFacility cipherFacility) {
        this.dataSource = dataSource;
        this.cipherFacility = cipherFacility;
    }
    public String[] GetStoredPasswords() throws Exception {
        return dataSource.getAllStoredPasswords();
    }
    public void AddNewPassword(PasswordInfo info) throws IOException, Exception {
        dataSource.addNewPassword(info);
    }
    public void DeletePassword(String passwordName) throws Exception, IOException {
        dataSource.deletePassword(passwordName);
    }
    public String GetPassword(String passwordName) throws Exception {
        char[] buffer = dataSource.getPassword(passwordName);
        return this.cipherFacility.Decrypt(new String(buffer));
    }
    public void UpdatePassword(PasswordInfo info) throws Exception {
        dataSource.updatePassword(info);
    }
}
