package com.example.james.seniorproject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataLore {
    public static Context context;
    public static final String DATA_FILE_NAME = "assignments.txt";

    private static void writeToDataFile(String data) {
        File file = new File(context.getFilesDir(), DATA_FILE_NAME);
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String readFromDataFile() {
        File file = new File(context.getFilesDir(), DATA_FILE_NAME);
        String eol = System.getProperty("line.separator");
        StringBuffer buffer = new StringBuffer();
        try {
            file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append(eol);
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static void writeAssignmentsToMemory(ArrayList<Assignment> assignments) {
        Gson gson = new Gson();
        String json = gson.toJson(assignments);
        writeToDataFile(json);
    }
    public static ArrayList<Assignment> readAssignmentsFromMemory() {
        String json = readFromDataFile();
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        ArrayList<Assignment> assignments = gson.fromJson(json, new TypeToken<ArrayList<Assignment>>(){}.getType());
        if (assignments == null)
            return new ArrayList<>();
        return assignments;
    }
}
