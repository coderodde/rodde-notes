package net.coderodde.roddenotes.util;

import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.coderodde.roddenotes.sql.support.MySQLDataAccessObject;

/**
 * This class implements a servlet context listener.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class RoddenoteServletContextListener 
        implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            MySQLDataAccessObject.INSTANCE.initializeDatabaseTables();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}
