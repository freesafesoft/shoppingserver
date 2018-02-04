package com.fss.shopping.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public abstract class ResponseWriter implements Serializable{
    private transient HttpServletRequest request;
    private transient HttpServletResponse response;

    public ResponseWriter(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void writeResult() throws IOException {
        String contentType = request.getContentType();
        OutputStream os = response.getOutputStream();
        if(contentType.indexOf("json") != -1)
            writeAsJson(os);
        else if(contentType.indexOf("xml") != -1)
            writeAsXML(os);
        else if(contentType.indexOf("stream") != -1)
            writeAsStream(os);
    }

    protected abstract void writeAsJson(OutputStream stream);

    protected abstract void writeAsXML(OutputStream stream);

    protected abstract void writeAsStream(OutputStream stream);
}
