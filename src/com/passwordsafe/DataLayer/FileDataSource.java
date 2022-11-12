package com.passwordsafe.DataLayer;

import com.passwordsafe.CipherFacility;
import com.passwordsafe.PasswordInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileDataSource implements IDataSource{

    private String path;

    private CipherFacility cipherFacility;

    public FileDataSource(String path, CipherFacility cipherFacility) {
        this.path = path;
        this.cipherFacility = cipherFacility;
    }

    @Override
    public void updatePassword(PasswordInfo info) throws Exception {
        File storage = this.GetFileFromName(info.getName());
        if (storage.exists()) {
            this.WriteToFile(storage.getPath(), info.getPlain());
        } else {
            throw new Exception("Password with the same name not existing.");
        }
    }
    @Override
    public char[] getPassword(String passwordName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File passwordFile = this.GetFileFromName(passwordName);
        char[] buffer = null;
        if (passwordFile.exists()) {
            FileReader reader = null;
            try {
                buffer = new char[(int)passwordFile.length()];
                reader = new FileReader(passwordFile);
                reader.read(buffer);
            }
            finally {
                if (reader != null) { try { reader.close(); } catch (IOException ex) { } };
            }
        }
        return buffer;
    }

    @Override
    public String[] getAllStoredPasswords() throws Exception {
        File directory = new File(path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        List<File> files = Arrays.asList(directory.listFiles());
        return files.stream()
                .filter(s -> s.getName().endsWith(".pw"))
                .map(f -> f.getName().split("\\.")[0])
                .collect(Collectors.toList()).toArray(new String[0]);
    }

    @Override
    public void addNewPassword(PasswordInfo info) throws Exception {
        File directory = new File(path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        File storage = (this.GetFileFromName(info.getName()));
        if (storage.createNewFile()) {
            this.WriteToFile(storage.getPath(), info.getPlain());
        } else {
            throw new Exception("Password with the same name already existing. Please choose another name of update the existing one.");
        }
    }

    @Override
    public void deletePassword(String passwordName) throws Exception {
        File storage = this.GetFileFromName(passwordName);
        if (!storage.delete()) {
            throw new Exception("Unable to delete password setting under " + storage.getName());
        }
    }


    private void WriteToFile(String filename, String password) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            String crypted = this.cipherFacility.Encrypt(password);
            writer.write(crypted);
        } finally {
            if (writer != null) try { writer.close(); } catch (IOException ignore) {}
        }
    }
    private File GetFileFromName(String name) {
        return new File( path + "/" + name + ".pw");
    }
}
