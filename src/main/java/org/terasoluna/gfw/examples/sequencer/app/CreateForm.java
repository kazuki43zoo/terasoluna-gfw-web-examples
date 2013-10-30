package org.terasoluna.gfw.examples.sequencer.app;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Size(min = 1, max = 1000)
    private String overview;

    @NotNull
    @Size(min = 1, max = 10000)
    private String content;

    @NotNull
    @Size(min = 1, max = 100)
    private String author;

    private boolean usingSequencer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isUsingSequencer() {
        return usingSequencer;
    }

    public void setUsingSequencer(boolean usingSequencer) {
        this.usingSequencer = usingSequencer;
    }

}
