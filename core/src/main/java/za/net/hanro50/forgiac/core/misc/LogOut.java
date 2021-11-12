package za.net.hanro50.forgiac.core.misc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class LogOut extends OutputStream {
    PrintStream[] parents;

    public LogOut(PrintStream... parents) throws FileNotFoundException {
        this.parents = parents;
    }

    @Override
    public void write(int b) throws IOException {
        for (PrintStream parent : parents) {
            parent.write(b);
        }
    }

    public PrintStream getPrintStream() {
        return new PrintStream(this, true);
    }
}
