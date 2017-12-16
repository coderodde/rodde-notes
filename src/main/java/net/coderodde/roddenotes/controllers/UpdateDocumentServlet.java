package net.coderodde.roddenotes.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coderodde.roddenotes.config.Config;
import net.coderodde.roddenotes.config.Config.PAGES;
import net.coderodde.roddenotes.config.Config.PARAMETERS;
import net.coderodde.roddenotes.model.Document;
import net.coderodde.roddenotes.sql.support.MySQLDataAccessObject;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * This servlet is responsible for updating an existing document. If the
 * incoming document is not yet in the database, it is put there.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
@WebServlet(name = "UpdateDocumentServlet", urlPatterns = {"/update"})
public class UpdateDocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
    throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("This servlet does not work via GET method.");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String documentId   = request.getParameter(PARAMETERS.DOCUMENT_ID);
        String documentText = request.getParameter(PARAMETERS.DOCUMENT_TEXT);
        String editToken    = request.getParameter(PARAMETERS.EDIT_TOKEN);
        
        documentText = sanitizeText(documentText);
        
        try (PrintWriter out = response.getWriter()) {
            if (documentId == null 
                    || editToken == null 
                    || documentText == null) {
                out.println(Config.STATUS_MESSAGES.FAILURE);
                return;
            }
            
            Document document = new Document();
            document.setId(documentId);
            document.setEditToken(editToken);
            document.setText(documentText);
            
            try {
                boolean validUpdate = 
                        MySQLDataAccessObject.INSTANCE.updateDocument(document);
                
                if (!validUpdate) {
                    request.getRequestDispatcher(PAGES.DONT_HACK_US_PAGE)
                           .forward(request, response);
                    return;
                }
                
                out.println(Config.STATUS_MESSAGES.SUCCESS);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    private String sanitizeText(String text) {
        return StringEscapeUtils.escapeHtml(text);
    }
}
