package net.coderodde.roddenotes.sql;

import java.sql.SQLException;
import net.coderodde.roddenotes.model.Document;

/**
 * This interface lists all the methods a data access object should implement in
 * order to integrate with rodde-notes.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public interface DataAccessObject {
    
    /**
     * Creates a new document with unique ID, random edit token and empty text.
     * 
     * @return a document.
     * @throws SQLException if the SQL layer fails.
     */
    public Document createNewDocument() throws SQLException;
    
    /**
     * Reads a document that has ID {@code id} and edit token {@code editToken}.
     * 
     * @param id        the ID of the document.
     * @param editToken the edit token of the document.
     * @return a document or {@code null} if there is no document with given ID
     *         and edit token.
     * @throws SQLException if the SQL layer fails.
     */
    public Document getDocument(String id, String editToken) 
            throws SQLException;
    
    /**
     * Reads a document with given ID.
     * 
     * @param id the ID of the desired document.
     * @return the document with the given ID or {@code null} if there is no 
     *         such.
     * @throws SQLException if the SQL layer fails.
     */
    public Document getDocument(String id) throws SQLException;
    
    /**
     * Saves the document. If the document is not yet present in the database,
     * it is inserted. Otherwise, its state is updated.
     * 
     * @param document the document to update.
     * @return {@code true} if the ID and editToken match each other. 
     *         {@code false} otherwise.
     * @throws SQLException if the SQL layer fails.
     */
    public boolean updateDocument(Document document) throws SQLException;
    
    /**
     * Makes sure all the tables are created in the database.
     * 
     * @throws SQLException if the SQL layer fails.
     */
    public void initializeDatabaseTables() throws SQLException;
}
