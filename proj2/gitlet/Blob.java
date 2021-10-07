package gitlet;
import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    private final String name;
    private final File file;
    private final String contents;
    protected Blob(String n, File f, String c) {
        name = n;
        file = f;
        contents = c;
    }
    protected String getName() {
        return name;
    }
    protected File getFile() {
        return file;
    }
    protected String getContents() {
        return contents;
    }
}
