package com.karimov;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String urlStr;
        try {
            FileInputStream fstream = new FileInputStream("url.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            if ((urlStr = br.readLine()) != null) {
                System.out.println("File will be read from " + urlStr);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return;
        }
        String outFile = "downloaded.html";
        try {
            URL url = new URL(urlStr);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        Map<String, Integer> dict = new HashMap<>();
        try {
            FileInputStream fstream = new FileInputStream(outFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] words = line.split("[ ,.!?\";:\\[\\]()\\n\\r\\t\\-]+");
                for (String str : words) {
                    if (dict.containsKey(str)) {
                        dict.replace(str, dict.get(str) + 1);
                    } else {
                        dict.put(str, 1);
                    }
                }
                System.out.println(Arrays.toString(words));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return;
        }

        for (Map.Entry<String, Integer> item : dict.entrySet()) {
            System.out.println("Word: " + item.getKey() + " Number: " + item.getValue());
        }
    }
}
