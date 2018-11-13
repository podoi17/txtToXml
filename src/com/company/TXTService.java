package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTService {

    private File file;
    private List<String> textList;
    private HashSet<String> ids;
    private HashMap<String, String> textMap;
    private HashSet<String> tags;

    public TXTService(String path) {
        file = new File(path);
        textList = new ArrayList<>();
        textMap = new HashMap<>();
        ids = new HashSet<>();
        tags = new HashSet<>();
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

    public HashSet<String> createTagList() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("<[a-zA-Z-]*>");
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    String temp = matcher.group(0);
                    temp = temp.replace("<", "");
                    temp = temp.replace(">", "");
                    this.tags.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return this.tags;
    }


    public void writeNewTagsIntoFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file.getPath());
            for(String tag: this.tags) {
                fileWriter.write(tag);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
