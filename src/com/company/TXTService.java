package com.company;

import java.io.*;
import java.util.*;

public class TXTService {

    private File file;
    private List<String> textList;
    private HashSet<String> ids;
    private HashMap<String, String> textMap;

    public TXTService(String path) {
        file = new File(path);
        textList = new ArrayList<>();
        textMap = new HashMap<>();
        ids = new HashSet<>();
    }


    public HashSet<String> getObjectIDS() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("*****")) {
                    counter = 0;
                }
                if(counter == 1 && line.matches("[0-9]+")) {
                    ids.add(line);
                }
                counter = counter + 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }


    public void printList() {
        for(String string : textList) {
            System.out.println(string);
        }
    }


    public List<String> get() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public HashMap<String, String> getTextMap() {
        return textMap;
    }

    public void setTextMap(HashMap<String, String> textMap) {
        this.textMap = textMap;
    }
}
