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
        try (PrintWriter out = response.getWriter()) {
            String documentId = 
                    request.getParameter(Config.PARAMETERS.DOCUMENT_ID);
            
            String editToken = 
                    request.getParameter(Config.PARAMETERS.EDIT_TOKEN);

            if (documentId == null || editToken == null) {
                out.print(Config.STATUS_MESSAGES.FAILURE);
                return;
            }

            Document document = null;

            try {
                document = MySQLDataAccessObject.INSTANCE.getDocument(documentId);
            } catch (SQLException ex) {
                out.print(Config.STATUS_MESSAGES.FAILURE);
                throw new RuntimeException(ex);
            }

            if (document == null) {
                out.print(Config.STATUS_MESSAGES.FAILURE);
                return;
            }

            if (!document.getEditToken().equals(editToken)) {
                out.print(Config.STATUS_MESSAGES.FAILURE);
                return;
            }

            try {
                MySQLDataAccessObject.INSTANCE.deleteDocument(documentId);
                out.print(Config.STATUS_MESSAGES.SUCCESS);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
