package za.net.hanro50.forgiac.basic;

import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.install.Installv1;
import za.net.hanro50.forgiac.core.install.Installv2;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        Base.init(args);
        try {
            new Installv2(Base.getJar(), Base.getDotMC());
        } catch (Exception e) {
            e.printStackTrace();
            new Installv1(Base.getJar(), Base.getDotMC());
        }
        System.out.println("Bye World!");
        System.exit(0);
    }
}