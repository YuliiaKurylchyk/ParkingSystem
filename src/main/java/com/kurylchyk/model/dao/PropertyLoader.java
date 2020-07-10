package com.kurylchyk.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

        public static Properties getPropValues(Class clazz ,String fileName) {
           Properties prop = new Properties();
            try {
                InputStream inputStream = clazz.getClassLoader().getResourceAsStream(fileName);
                if (inputStream != null) {
                    prop.load(inputStream);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return prop;
        }

}
