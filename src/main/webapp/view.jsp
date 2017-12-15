<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>rodde-notes</title>
        <style>
            #view {
                width: 800px;
                margin: auto;
            }
            
            #text {
                text-align: justify;
            }
        </style>
    </head>
    <body>
        <div id="view">
            <p id="text">${documentText}</p>
        </div>
    </body>
</html>
