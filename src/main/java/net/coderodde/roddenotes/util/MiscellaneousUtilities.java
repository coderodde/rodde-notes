package net.coderodde.roddenotes.util;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides various facilities.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class MiscellaneousUtilities {
    
    /**
     * Returns the full URL of the web application.
     * 
     * @param request the servlet request object.
     * @return an URL.
     */
    public static String getServerURL(HttpServletRequest request) {
//        String url = request.getServerName() + request.getContextPath() + request.getPathInfo();
        String url = request.getRequestURL().toString();
        int lastSlashIndex = url.lastIndexOf('/');
        return url.substring(0, lastSlashIndex);
    }
}
