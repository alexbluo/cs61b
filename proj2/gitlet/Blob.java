package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Blob implements Serializable {
    private final String fileName;
    private final File file;
    private final String contents;
    protected Blob(String fn, File f, String c) {
        fileName = fn;
        file = f;
        contents = c;
    }
    protected String getName() {
        return fileName;
    }
    protected File getFile() {
        return file;
    }
    protected String getContents() {
        return fileName;
    }
}
