package quickbitnotes.model;

import java.io.Serializable;

public class ImageFile implements Serializable {
    private byte[] content;
    private String filename;
    private String mimeType;

    public ImageFile(byte[] content, String filename, String mimeType) {
        this.content = content;
        this.filename = filename;
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }

    public String getMimeType() {
        return mimeType;
    }
}
