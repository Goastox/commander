package com.goastox.decision.test;

import java.io.*;
import java.net.URL;

public class DroolsUtil {
        public static String getDrlString(String drlFileName) {
            StringBuilder drlStringBuilder = new StringBuilder();
            try {
//                URL url = DroolsTest.class.getClassLoader().getResource(drlFileName);
//                System.out.println(url.getFile());
//                File file = new File(url.getFile());
                File file = new File("/Users/konghanghang/commander/decision/src/test/java/com/goastox/decision/test/activities.drl");
                Reader reader = new InputStreamReader(new FileInputStream(file));
                char[] buffer = new char[1024 * 1024];
                int bytes = reader.read(buffer);
                for (int i = 0; i < bytes; ++i) {
                    drlStringBuilder.append(buffer[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return drlStringBuilder.toString();
        }
}
