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
import net.coderodde.roddenotes.model.Document;
import net.coderodde.roddenotes.sql.support.MySQLDataAccessObject;

/**
 * This servlet is responsible for deleting documents from the database.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 16, 2017)
 */
@WebServlet(name = "DeleteDocumentServlet", urlPatterns = {"/delete"})
public class DeleteDocumentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response)
            throws ServletException, IOException {
        String documentId = request.getParameter(Config.PARAMETERS.DOCUMENT_ID);
        String editToken  = request.getParameter(Config.PARAMETERS.EDIT_TOKEN);
        
        if (documentId == null || editToken == null) {
            return;
        }
        
        Document document = null;
        
        try {
            document = MySQLDataAccessObject.INSTANCE.getDocument(documentId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        if (document == null) {
            return;
        }
        
        if (!document.getEditToken().equals(editToken)) {
            return;
        }
        
        try {
            MySQLDataAccessObject.INSTANCE.deleteDocument(documentId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
