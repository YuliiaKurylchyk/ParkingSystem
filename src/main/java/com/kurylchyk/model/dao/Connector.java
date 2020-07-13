package com.kurylchyk.model.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.*;
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

            ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
            InputStream inputStream = Connector.class.getClassLoader().getResourceAsStream("database.sql");
            Reader reader = new InputStreamReader(inputStream);
            sr.runScript(reader);
            logger.info("Reading data base from file");
        }catch (SQLException  exception){
            logger.error(exception);
        }
    }

    public DataSource getDataSource(){
        return dataSource;
    }



}
