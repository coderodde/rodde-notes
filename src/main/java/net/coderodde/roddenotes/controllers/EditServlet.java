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
import net.coderodde.roddenotes.config.Config.ATTRIBUTES;
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
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(Config.PARAMETERS.DOCUMENT_ID);
        String editToken = request.getParameter(Config.PARAMETERS.EDIT_TOKEN);
        
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
        request.setAttribute(ATTRIBUTES.PUBLISH_LINK, 
                             getPublishLink(request, document));
        
        request.getRequestDispatcher(Config.PAGES.EDITOR_PAGE)
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
                .append(Config.PARAMETERS.DOCUMENT_ID)
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
                                  .append(Config.PARAMETERS.DOCUMENT_ID)
                                  .append('=')
                                  .append(document.getId())
                                  .append('&')
                                  .append(Config.PARAMETERS.EDIT_TOKEN)
                                  .append('=')
                                  .append(document.getEditToken())
                                  .toString();
    }
}
