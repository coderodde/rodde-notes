<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>rodde-notes</title>
        <style>
            <%@include file="styles.css" %>
        </style>
        <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous">

        </script>
        <script
            src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
            integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
            crossorigin="anonymous">

        </script>

        <script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.2/MathJax.js?config=TeX-MML-AM_CHTML'></script>

        <script type="text/x-mathjax-config">
            MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\[','\\]']]}});
        </script>

        <script>
            <%@include file="code.js" %>
        </script>
    </head>

    <body>
        <div id="page">
            <div id="documentContainer">
                <p id="documentView" align="justify"></p>
                <textarea id="editorTextArea" oninput="moveTextToDocument()">${documentText}</textarea>
                <button id="typesetButton" class="button" onclick="typeset()">Typeset!</button>
                <button id="saveButton" class="button" onclick="save()">Save!</button>
                <button id="deleteButton" class="button" onclick="deleteDocument()">Delete!</button>
                <div id="publishLink">
                    <div id="publishLinkLabel">Non-editable publish link:</div>
                    <div id="publishLinkContent">${publishLink}</div>
                </div>
            </div>

            <form id="dataForm">
                <input type="hidden" value="${documentId}" id="documentId" />
                <input type="hidden" value="${editToken}" id="editToken" />
            </form>

            <form id="deleteForm" action="delete" method="post" style="display: none;">
                <input type="text" id="idField" name="documentId"/>
                <button type="submit">Delete</button>
            </form>

            <div id ="savedSuccessful" class="topNotifications">
                The document is updated.
            </div>

            <div id="savedFailed" class="topNotifications">
                Could not update the document.
            </div>
        </div>

        <script>
            startTypesettingLoop();
            moveTextToDocument();
        </script>
    </body>
</html>
