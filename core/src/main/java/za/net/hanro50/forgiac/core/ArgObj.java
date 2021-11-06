package za.net.hanro50.forgiac.core;

import java.util.function.Consumer;

public class ArgObj {
    public final Consumer<String[]> execute;
    public final String discription;
public final int argCount;
    public ArgObj(String discription, int argCount, Consumer<String[]> execute) {
        this.execute = execute;
        this.argCount = argCount;
        this.discription = discription;
    }
}