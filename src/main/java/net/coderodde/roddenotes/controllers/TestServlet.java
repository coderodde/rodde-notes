/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coderodde.roddenotes.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rodionefremov
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/"})
public class TestServlet extends HttpServlet {
    
    private static final String PAGE_TYPE_STRING = "pageType";
    
    private static final String VIEW_PAGE_NAME = "viewPage";
    
    private static final String EDIT_PAGE_NAME = "editPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(PAGE_TYPE_STRING, EDIT_PAGE_NAME);
        request.getRequestDispatcher("page.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
