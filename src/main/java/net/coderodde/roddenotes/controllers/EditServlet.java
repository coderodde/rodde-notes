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
     * The name of the attribute for the page type. The respective attribute is
     * passed to page.jsp.
     */
    private static final String PAGE_TYPE_ATTRIBUTE_NAME = "pageType";
    
    /**
     * The value of the page type for viewing the edit page.
     */
    private static final String PAGE_TYPE_EDIT = "editPage";
    
    /**
     * The name of the attribute holding the document ID.
     */
    private static final String DOCUMENT_ID_ATTRIBUTE_NAME = "documentId";
    
    /**
     * The name of the attribute holding the edit token.
     */
    private static final String EDIT_TOKEN_ATTRIBUTE_NAME = "editToken";
    
    /**
     * The name of the JSP file implementing the document editor.
     */
    private static final String EDITOR_JSP_PAGE = "page.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(ID_PARAMETER_NAME);
        String editToken = request.getParameter(EDIT_TOKEN_PARAMETER_NAME);
        
        if (id == null || editToken == null) {
            // TODO: create a new document and redirect to it.
            return;
        }
        
        Document document = null;
        
        try {
            document = MySQLDataAccessObject.INSTANCE.getDocument(id);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        if (document == null) {
            // TODO: create a new document and redirect to it.
            return;
        }
        
        if (!document.getEditToken().equals(editToken)) {
            // TODO: create a new document and redirect to it.
            return;
        }
        
        request.setAttribute(PAGE_TYPE_ATTRIBUTE_NAME, PAGE_TYPE_EDIT);
        request.setAttribute(DOCUMENT_ID_ATTRIBUTE_NAME, document.getId());
        request.setAttribute(EDIT_TOKEN_ATTRIBUTE_NAME, 
                             document.getEditToken());
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
}
