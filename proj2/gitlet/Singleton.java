package gitlet;

import java.io.Serializable;

public class Singleton implements Serializable {
    protected static Commit head;
    protected static Commit branch;
}
