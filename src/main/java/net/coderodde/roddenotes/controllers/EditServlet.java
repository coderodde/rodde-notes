package net.coderodde.roddenotes.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coderodde.roddenotes.model.Document;
import net.coderodde.roddenotes.sql.support.MySQLDataAccessObject;
import static net.coderodde.roddenotes.util.MiscellaneousUtilities.getServerURL;

/**
 * This servlet handles the edit requests. If the servlet receives parameters 
 * defining the document ID and its edit token, and both are valid, this servlet
 * prepares an editor view for the document. Otherwise, a new document is
 * created and is presented to the user.
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
@WebServlet(name = "EditServlet", urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet {

    /**
     * The name of the parameter holding the document ID.
     */
    private static final String ID_PARAMETER_NAME = "documentId";
    
    /**
     * The name of the parameter holding the edit token.
     */
    private static final String EDIT_TOKEN_PARAMETER_NAME = "editToken";
    
    
    /**
     * The value of the page type for viewing the edit page.
     */
    private static final String PAGE_TYPE_EDIT = "editPage";
    
    
    /**
     * Contains all the attribute definitions this servlet relies on.
     */
    private static final class ATTRIBUTES {
        
        /**
         * Holds the document ID.
         */
        private static final String DOCUMENT_ID = "documentId";
        
        /**
         * Holds the edit token.
         */
        private static final String EDIT_TOKEN = "editToken";
        
        /**
         * Holds the document text.
         */
        private static final String DOCUMENT_TEXT = "documentText";
        
        /**
         * Holds the publish link.
         */
        private static final String PUBLISH_LINK = "publishLink";
    }
    
    /**
     * The name of the JSP file implementing the document editor.
     */
    private static final String EDITOR_JSP_PAGE = "edit.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(ID_PARAMETER_NAME);
        String editToken = request.getParameter(EDIT_TOKEN_PARAMETER_NAME);
        
        if (id == null || editToken == null) {
            serveFreshEmptyDocument(request, response);
            return;
        }
        
        Document document = null;
        
        try {
            document = MySQLDataAccessObject.INSTANCE.getDocument(id);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        if (document == null) {
            serveFreshEmptyDocument(request, response);
            return;
        }
        
        if (!document.getEditToken().equals(editToken)) {
            serveFreshEmptyDocument(request, response);
            return;
        }
        
        request.setAttribute(ATTRIBUTES.DOCUMENT_ID, document.getId());
        request.setAttribute(ATTRIBUTES.EDIT_TOKEN, document.getEditToken());
        request.setAttribute(ATTRIBUTES.DOCUMENT_TEXT, document.getText());
        request.setAttribute(ATTRIBUTES.PUBLISH_LINK, getPublishLink(request, 
                                                                     document));
        request.getRequestDispatcher(EDITOR_JSP_PAGE)
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("This servlet does not serve POST requests.");
        }
    }
    
    private String getPublishLink(HttpServletRequest request, 
                                  Document document) {
        return new StringBuilder(getServerURL(request))
                .append("/view?")
                .append(ID_PARAMETER_NAME)
                .append('=')
                .append(document.getId())
                .toString();
    }
    
    private void serveFreshEmptyDocument(HttpServletRequest request,
                                         HttpServletResponse response)
    throws ServletException, IOException {
        Document document = null;
        
        try {
            document = MySQLDataAccessObject.INSTANCE.createNewDocument();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        String path = getPath(request, document);
        request.getRequestDispatcher(path).forward(request, response);
    }
    
    private String getPath(HttpServletRequest request, Document document) {
        return new StringBuilder().append(request.getPathInfo())
                                  .append('?')
                                  .append(ID_PARAMETER_NAME)
                                  .append('=')
                                  .append(document.getId())
                                  .append('&')
                                  .append(EDIT_TOKEN_PARAMETER_NAME)
                                  .append('=')
                                  .append(document.getEditToken())
                                  .toString();
    }
}
