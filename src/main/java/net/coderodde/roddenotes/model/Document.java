package net.coderodde.roddenotes.model;

/**
 * This class implements a document.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Dec 15, 2017)
 */
public final class Document {
    
    private String id;
    private String editToken;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEditToken() {
        return editToken;
    }

    public void setEditToken(String editToken) {
        this.editToken = editToken;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
