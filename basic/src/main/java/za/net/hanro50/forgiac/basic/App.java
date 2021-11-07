package za.net.hanro50.forgiac.basic;

import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.Install1;
import za.net.hanro50.forgiac.core.Install2;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        Base.init(args);
        try {
            new Install2(Base.getJar(), Base.getDotMC());
        } catch (Exception e) {
            new Install1(Base.getJar(), Base.getDotMC());
        }
        System.out.println("Bye World!");
    }
}