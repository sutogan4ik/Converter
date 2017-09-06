package ru.brucha.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.brucha.converter.entity.ValCurs;

/**
 * Created by prog on 06.09.2017.
 */

public class FileUtil {
    public void saveToFile(ValCurs currencies, File cacheFile){
        try {
            if(!cacheFile.exists()){
                cacheFile.getParentFile().mkdirs();
                cacheFile.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(currencies);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ValCurs loadFile(File cacheFile){
        try {
            if(!cacheFile.exists()){
                cacheFile.getParentFile().mkdirs();
                cacheFile.createNewFile();
            }
            FileInputStream inputStream = new FileInputStream(cacheFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (ValCurs) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
