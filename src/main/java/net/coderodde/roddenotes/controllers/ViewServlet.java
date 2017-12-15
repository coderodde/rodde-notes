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
 * This servlet is responsible for showing the documents via their ID.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
@WebServlet(name = "ViewServlet", urlPatterns = {"/view"})
public class ViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
            throws ServletException, IOException {
        String documentIdParameter = 
                request.getParameter(Config.PARAMETERS.DOCUMENT_ID);
        
        if (documentIdParameter == null) {
            // TODO: show no doc page.
            return;
        }
        
        Document document = null;
        
        try {
            document = MySQLDataAccessObject
                        .INSTANCE
                        .getDocument(documentIdParameter);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        if (document == null) {
            // TODO: show no doc page.
            return;
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("This servlet is not accessible via POST method.");
        }
    }
}
