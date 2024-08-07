package za.net.hanro50.forgiac.basic;

import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import za.net.hanro50.forgiac.core.ArgsParser;
import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.ExitCodes;
import za.net.hanro50.forgiac.core.install.Installv1;
import za.net.hanro50.forgiac.core.install.Installv2;
import za.net.hanro50.forgiac.core.install.Installv3;
import za.net.hanro50.forgiac.core.misc.ArgObj;

/**
 * Hello world!
 *
 */
public class App {
    static {
        ArgsParser.Register("virtual", new ArgObj(
                "Used to install forge in launcher environments that don't emulate the vanilla launcher's file structure",
                new String[] { "version folder", "library folder" }, (argz) -> {
                    try {
                        System.out.println(argz[0]);
                        System.out.println(argz[1]);
                        if (Base.isLocked()) {
                            System.err.println("[basic]: The lock parameter needs to after the virtual parameter");
                            ExitCodes.exit(300);
                        }

                        File virtual = new File("virtual");
                        if (virtual.exists()) {
                            Util.deleteDirectory(virtual);
                        }
                        virtual.mkdir();
                        // virtual.deleteOnExit();
                        Base.setDotMC(virtual);

                        System.out.println("[basic]: Using dir " + virtual.getAbsolutePath());

                        File link_versions = new File(virtual, "versions");
                        link_versions.deleteOnExit();

                        File og_versions = new File(argz[0]);
                        Util.link(link_versions.toPath(), og_versions.toPath());

                        File link_libraries = new File(virtual, "libraries");
                        link_libraries.deleteOnExit();

                        File og_libraries = new File(argz[1]);
                        Util.link(link_libraries.toPath(), og_libraries.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        ExitCodes.exit(100);
                    }
                }));
    }

    public static void main(String[] args) throws Exception {
        Base.standalone = true;
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JFrame self = new JFrame("Forgiac");
        try {
            Image img = ImageIO.read(Base.class.getResourceAsStream("/za/net/hanro50/forgiac/resources/favicon.png"));
            img = img.getScaledInstance(1024, 1024, Image.SCALE_REPLICATE);
            self.setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Base.init(args, self);
        try {
            new Installv3(Base.getJar(), Base.getDotMC());
        } catch (Exception e1) {
            try {
                new Installv2(Base.getJar(), Base.getDotMC());
            } catch (Exception e2) {

                try {
                    new Installv1(Base.getJar(), Base.getDotMC());
                } catch (Exception e3) {
                    System.out.println("V3 failed");
                    e1.printStackTrace();
                    System.out.println("V2 failed");
                    e2.printStackTrace();
                    System.out.println("V1 failed");
                    e3.printStackTrace();
                    ExitCodes.exit(201);
                }
            }
        }
        System.out.println("Bye World!");
        ExitCodes.exit(0);
    }
}