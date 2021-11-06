package za.net.hanro50.forgiac.basic;
import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.Install;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        Base.init(args);
        new Install(Base.getJar(), Base.getDotMC());
        System.out.println("Bye World!");
    }
}