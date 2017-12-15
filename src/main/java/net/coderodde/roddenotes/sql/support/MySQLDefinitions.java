package net.coderodde.roddenotes.sql.support;

/**
 * This class defines all the data regarding the database schema for the 
 * rodde-notes app.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class MySQLDefinitions {
    
    /**
     * Defines the structure of the database table holding the note entries.
     */
    public static final class DOCUMENT_TABLE {
        
        /**
         * The name of the notes table.
         */
        public static final String TABLE_NAME = "rodde_notes_documents";
        
        /**
         * Describes the note ID column.
         */
        public static final class ID_COLUMN {
            
            /**
             * The name of the note ID column.
             */
            public static final String NAME = "document_id";
            
            /**
             * The length of IDs in characters.
             */
            public static final int LENGTH = 10;
            
            /**
             * The data type of the column.
             */
            public static final String TYPE = 
                    "CHAR(" + LENGTH + ") NOT NULL";
        }
            
        /**
         * Describes the edit token column.
         */
        public static final class EDIT_TOKEN_COLUMN {
            
            /**
             * The name of the edit token column.
             */
            public static final String NAME = "edit_token";
            
            /**
             * The length of edit tokens in characters.
             */
            public static final int LENGTH = 12;
            
            /**
             * The data type of the column.
             */
            public static final String TYPE = "CHAR(" + LENGTH + ") NOT NULL";
        }
        
        /**
         * Describes the text column.
         */
        public static final class TEXT_COLUMN {
            
            /**
             * The name of the text column.
             */
            public static final String NAME = "text";
            
            /**
             * The data type of the column.
             */
            public static final String TYPE = "TEXT NOT NULL";
        }
        
        /**
         * The SQL statement for creating the note table.
         */
        public static final String CREATE_STATEMENT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "  " + ID_COLUMN.NAME + " " + ID_COLUMN.TYPE + ",\n" +
                "  " + EDIT_TOKEN_COLUMN.NAME + " " + 
                       EDIT_TOKEN_COLUMN.TYPE + ",\n" +
                "  " + TEXT_COLUMN.NAME + " " + TEXT_COLUMN.TYPE + ",\n" +
                "  PRIMARY KEY(" + ID_COLUMN.NAME + "));";
    }
    
    /**
     * Contains all the delete statements.
     */
    public static final class DELETE {
        
        /**
         * Deletes the document from the database.
         */
        public static final String DOCUMENT = 
                "DELETE FROM " + DOCUMENT_TABLE.TABLE_NAME + " WHERE " +
                DOCUMENT_TABLE.ID_COLUMN.NAME + " = ?;";
    }
    
    /**
     * Contains all the insert statements.
     */
    public static final class INSERT {
        
        /**
         * Inserts a document.
         */
        public static final String DOCUMENT = 
                "INSERT INTO " + DOCUMENT_TABLE.TABLE_NAME + 
                " VALUES (?, ?, ?);";
    }
    
    /**
     * Contains all the select statements.
     */
    public static final class SELECT {
        
        /**
         * Contains all the select statements selecting documents.
         */
        public static final class DOCUMENT {
            
            /**
             * Selects a document via an ID.
             */
            public static final String VIA_DOCUMENT_ID = 
                    "SELECT * FROM " + DOCUMENT_TABLE.TABLE_NAME + " WHERE " +
                    DOCUMENT_TABLE.ID_COLUMN.NAME + " = ?;";
            
            /**
             * Selects a document via an ID and an edit token.
             */
            public static final String VIA_DOCUMENT_ID_AND_EDIT_TOKEN = 
                    "SELECT * FROM " + DOCUMENT_TABLE.TABLE_NAME + " WHERE " +
                    DOCUMENT_TABLE.ID_COLUMN.NAME + " = ? AND " +
                    DOCUMENT_TABLE.EDIT_TOKEN_COLUMN.NAME + " = ?;";
        }
    }
    
    /**
     * Contains all the update statements.
     */
    public static final class UPDATE {
        
        /**
         * Contains all the update statements on the document.
         */
        public static final class DOCUMENT {
            
            /**
             * Updates the text via the document ID.
             */
            public static final String VIA_DOCUMENT_ID = 
                    "UPDATE " + DOCUMENT_TABLE.TABLE_NAME + " SET " +
                    DOCUMENT_TABLE.TEXT_COLUMN.NAME + " = ? WHERE " +
                    DOCUMENT_TABLE.ID_COLUMN.NAME + " = ?;";
            }
    }
}
