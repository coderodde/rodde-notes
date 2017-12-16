            
            var RoddeNotes = {};
            
            RoddeNotes.Parameters = {};
            RoddeNotes.Parameters.DOCUMENT_ID = "documentId";
            RoddeNotes.Parameters.EDIT_TOKEN = "editToken";
            RoddeNotes.Parameters.EDITOR_TEXT_AREA = "editorTextArea";
            
            function moveTextToDocument() {
                var editorElement = 
                        document.getElementById(
                        RoddeNotes.Parameters.EDITOR_TEXT_AREA);
                
                var documentViewElement = 
                        document.getElementById("documentView");
                
                var documentText = editorElement.value;
                documentViewElement.innerHTML = documentText;
            }
            
            function typeset() {
                MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
            }
            
            function startTypesettingLoop() {
                setInterval(function() {typeset();}, 5000);
            }
            
            function save() {
                var documentId = 
                        document.getElementById(
                        RoddeNotes.Parameters.DOCUMENT_ID).value;
                
                var editToken =
                        document.getElementById(
                        RoddeNotes.Parameters.EDIT_TOKEN).value;
                
                var documentText = 
                        document.getElementById(
                        RoddeNotes.Parameters.EDITOR_TEXT_AREA).value;
                
                var xhr = new XMLHttpRequest();
                
                xhr.onreadystatechange = function() {
                    if (this.readystate == 4 && this.status == 200) {
                        console.log("yeah");
                        
                    }
                };
                
                xhr.open("POST", "update", true);
                xhr.setRequestHeader("Content-type", 
                                     "application/x-www-form-urlencoded");
                xhr.send("documentId=" + documentId +
                         "&editToken=" + editToken + 
                         "&documentText=" + documentText);
            }
            