package com.kurylchyk.model;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Connector {

    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:/comp/env/jdbc/parkingSystemDB";
    static {

        try {
            Context context = new InitialContext();
            Object lookup = context.lookup(JNDI_LOOKUP_SERVICE);
            if(lookup!=null){
                dataSource = (DataSource) lookup;
                createDataBase();
            }
            else{
                throw new RuntimeException("DataSource is not found!");
            }
        }catch (NamingException e){
            e.printStackTrace();
        }
    }
    private static void createDataBase(){
        try {
//            File file = new File(
  //                  ClassLoader.getSystemClassLoader().getResource("database.properties").getFile());


            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            Reader reader = new BufferedReader(new FileReader("D:\\Інфа з диску С\\NewCourse\\thisProject\\ParkingSystem\\src\\main\\resources\\database.sql"));

            sr.runScript(reader);
        }catch (SQLException|IOException  exception){
            exception.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
