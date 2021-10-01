package gitlet;
import java.io.File;
import java.util.*;

public class Blob {
    protected String fileName;
    protected File file;
    protected Blob(String fn, File f, String contents) {
        fileName = fn;
        file = f;
        Utils.writeContents(file, contents);
    }
}
