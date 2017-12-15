            
            function moveTextToDocument() {
                var editorElement = document.getElementById("editorTextArea");
                var documentViewElement = document.getElementById("documentView");
                var documentText = editorElement.value;
                documentViewElement.innerHTML = documentText;
            }
            
            function typeset() {
                MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
            }
            