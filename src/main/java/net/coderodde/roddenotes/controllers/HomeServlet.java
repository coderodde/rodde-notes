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
 * This servlet listens to the root resource of this application, creates a new
 * document and redirects to the document's edit view.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {

    private static final class PARAMETERS {
        
        private static final String DOCUMENT_ID = "documentId";
        private static final String EDIT_TOKEN = "editToken";
        private static final String DOCUMENT_TEXT = "documentText";
    }
    
    private static final String EDIT_SERVLET_NAME = "edit";
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
            throws ServletException, IOException {
        Document document = null;
        
        try {
            document = MySQLDataAccessObject.INSTANCE.createNewDocument();         
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        if (document == null) {
            throw new NullPointerException("Creating a document failed.");
        }
        
        response.sendRedirect(getEditPageAddress(request, document));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.print("Please access this resource via GET method.");
        }
    }
    
    private String getEditPageAddress(HttpServletRequest request,
                                      Document document) {
        return new StringBuilder()
                   .append(getServerURL(request))
                   .append('/')
                   .append(EDIT_SERVLET_NAME)
                   .append('?')
                   .append(PARAMETERS.DOCUMENT_ID)
                   .append('=')
                   .append(document.getId())
                   .append('&')
                   .append(PARAMETERS.EDIT_TOKEN)
                   .append('=')
                   .append(document.getEditToken())
                   .toString();
    }
    
    private String getServerURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        int lastSlashIndex = url.lastIndexOf('/');
        return url.substring(0, lastSlashIndex);
    }
}
