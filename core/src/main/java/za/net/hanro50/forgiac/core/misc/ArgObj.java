package za.net.hanro50.forgiac.core.misc;

import java.util.function.Consumer;

public class ArgObj {
    public final Consumer<String[]> execute;
    public final String discription;
    public final int argCount;
    public final String[] argNames;

    public ArgObj(String discription, String[] argNames, Consumer<String[]> execute) {
        this.execute = execute;
        this.argCount = argNames != null ? argNames.length : 0;
        this.discription = discription;
        this.argNames = argNames;
    }
}