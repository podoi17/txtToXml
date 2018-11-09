package com.company;

import java.io.*;
import java.util.HashSet;

public class FileHelper {

    private File file;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public FileHelper(File file) {
        this.file = file;
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.file));
            this.bufferedWriter = new BufferedWriter(new FileWriter(this.file));
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }

    }


    public HashSet<String> fileToMap() {
        String st;
        HashSet<String> set = new HashSet<>();
        try {
            while((st = bufferedReader.readLine()) != null) {
                set.add(st);
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        return set;
    }

    public void mapToFile(HashSet<String> set) {
        for(String string : set) {
            try {
                bufferedWriter.write(string);
                bufferedWriter.write("\n");
            } catch (IOException e) {
                System.err.print(e.getMessage());
            }
        }
        closeReaderWriter();
    }

    private void closeReaderWriter() {
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }


}
