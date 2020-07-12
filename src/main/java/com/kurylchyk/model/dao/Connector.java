package com.kurylchyk.model.dao;

import com.kurylchyk.model.services.impl.vehicleCommand.ConnectCustomerToVehicleCommand;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.engine.User;

import java.io.File;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;



public class Connector {

    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:comp/env/jdbc/parkingSystemDB";
    private static final Logger logger = LogManager.getLogger(Connector.class);
    static {

        try {
            Context context = new InitialContext();
            Object lookup = context.lookup(JNDI_LOOKUP_SERVICE);
            if(lookup!=null){
                dataSource = (DataSource) lookup;
                logger.debug("Initializing the dataSource");
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
          //  File file = new File(
            //        ClassLoader.getSystemClassLoader().getResource(database.properties).getFile());


            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            Reader reader = new BufferedReader(new FileReader("D:\\Інфа з диску С\\NewCourse\\thisProject\\ParkingSystem\\src\\main\\resources\\database.sql"));
            sr.runScript(reader);
            logger.info("Reading data base from file");
        }catch (SQLException | IOException  exception){
            logger.error(exception);
        }
    }

    public DataSource getDataSource(){
        return dataSource;
    }



}
