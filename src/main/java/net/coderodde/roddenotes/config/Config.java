package net.coderodde.roddenotes.config;

/**
 * This class contains the general application parameters.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class Config {
    
    /**
     * This class contains form parameter names.
     */
    public static final class PARAMETERS {
        
        /**
         * The name of the parameter holding the document ID.
         */
        public static final String DOCUMENT_ID = "documentId";
        
        /**
         * The name of the parameter holding the edit token.
         */
        public static final String EDIT_TOKEN = "editToken";
    }
    
    /**
     * This class contains attribute names.
     */
    public static final class ATTRIBUTES {
        
        /**
         * Used for communicating the actual document text.
         */
        public static final String DOCUMENT_TEXT = "documentText";
        
        /**
         * Used for communicating publish links.
         */
        public static final String PUBLISH_LINK = "publishLink";
        
        /**
         * Used for communicating document IDs.
         */
        public static final String DOCUMENT_ID = "documentId";
        
        /**
         * Used for communicating edit tokens.
         */
        public static final String EDIT_TOKEN = "editToken";
    }
    
    public static final class PAGES {
        
        /**
         * The name of the JSP file for viewing a (non-editable) document.
         */
        public static final String VIEW_PAGE = "view.jsp";
        
        /**
         * The name of the JSP file for editing documents.
         */
        public static final String EDITOR_PAGE = "edit.jsp";
        
        /**
         * The name of the JSP file rendered upon missing document.
         */
        public static final String NO_DOCUMENT_PAGE = "viewDocNotFound.jsp";
        
        /**
         * The name of the HTML file rendered upon requesting a view without the
         * document ID parameter.
         */
        public static final String NO_ID_VIEW_PAGE = "viewIdNotGiven.html";
    }
    
    /**
     * This class contains all the error messages in the application.
     */
    public static final class ERROR_MESSAGES {

        /**
         * The name of the CSS class used for rendering error messages.
         */
        public static final String ERROR_MESSAGE_CSS_CLASS = "error";
        
        /**
         * The opening span tag.
         */
        public static final String SPAN_BEGIN = 
                "<span class='" +
                ERROR_MESSAGE_CSS_CLASS + 
                "'>";
        
        /**
         * The closing span tag.
         */
        public static final String SPAN_END = "</span>";
        
        /**
         * The text rendered whenever the document with given ID does not exist.
         */
        public static final String NO_SUCH_DOCUMENT_TEXT_FORMAT = 
                SPAN_BEGIN + "(Document with ID %s does not exist.)" + SPAN_END;

        /**
         * The text rendered whenever the user accesses the view page without a any
         * document ID.
         */
        public static final String NO_GIVEN_ID_TEXT = 
                SPAN_BEGIN + 
                "(Cannot find a document without an ID.)" +
                SPAN_END;
    }
}
