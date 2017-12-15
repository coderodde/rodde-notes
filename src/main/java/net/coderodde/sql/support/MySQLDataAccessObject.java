package net.coderodde.sql.support;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.coderodde.roddenotes.model.Document;
import net.coderodde.sql.DataAccessObject;
import net.coderodde.sql.support.MySQLDefinitions.DOCUMENT_TABLE;
import net.coderodde.util.RandomUtilities;

/**
 * This class implements a data access object over a MySQL database.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class MySQLDataAccessObject implements DataAccessObject {

    /**
     * The name of the environment variable holding the connection URI for the 
     * MySQL database server.
     */
    private static final String DATABASE_URI_ENVIRONMENT_VARIABLE = 
            "RODDE_NOTES_DB_URI";
    
    static {
        try {
            // Attempts to load the driver.
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot load the JDBC driver for MySQL.",
                                       ex);
        }
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public Document createNewDocument() throws SQLException {
        String id = null;
        String editToken = RandomUtilities.generateRandomEditToken();
        
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            
            
            try (PreparedStatement statement = 
                    connection.prepareStatement(MySQLDefinitions.SELECT.DOCUMENT.VIA_DOCUMENT_ID)) {
                
                while (true) {
                    id = RandomUtilities.generateRandomDocumentId();
                    statement.setString(1, id);
                    
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            break;
                        }
                    }
                }
                
                statement.setString(1, id);
                statement.setString(2, editToken);
                statement.setString(3, ""); // Document text.
                
                statement.executeUpdate();
            }
            
            connection.commit();
        }
        
        Document document = new Document();
        document.setId(id);
        document.setEditToken(editToken);
        document.setText(""); // Note the empty text.
        return document;
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public Document getDocument(String id, String editToken) 
            throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = 
                    connection.prepareStatement(MySQLDefinitions
                                    .SELECT
                                    .DOCUMENT
                                    .VIA_DOCUMENT_ID_AND_EDIT_TOKEN)) {
                statement.setString(1, id);
                statement.setString(2, editToken);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    
                    Document document = new Document();
                    document.setId(
                            resultSet.getString(DOCUMENT_TABLE.ID_COLUMN.NAME));
                    
                    document.setEditToken(
                            resultSet.getString(
                                    DOCUMENT_TABLE.EDIT_TOKEN_COLUMN.NAME));
                    
                    document.setText(
                            resultSet.getString(
                                    DOCUMENT_TABLE.TEXT_COLUMN.NAME));
                    
                    return document;
                }
            }
        }
    }
    
    private Connection getConnection() throws SQLException {
        URI dbUri = null;
        
        try {
            dbUri = new URI(System.getenv(DATABASE_URI_ENVIRONMENT_VARIABLE));
        } catch (URISyntaxException ex) {
            throw new RuntimeException("Bad URI syntax.", ex);
        }
        
        String[] tokens = dbUri.getUserInfo().split(":");
        String username = tokens[0];
        String password = tokens[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
