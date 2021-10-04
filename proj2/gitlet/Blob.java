package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Blob implements Serializable {
    private final File file;
    private final String contents;
    protected Blob(File f, String c) {
        file = f;
        contents = c;
    }
    protected File getFile() {
        return file;
    }
    protected String getContents() {
        return contents;
    }
}
