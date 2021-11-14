package za.net.hanro50.forgiac.basic;

import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import za.net.hanro50.forgiac.core.ArgsParser;
import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.ExitCodes;
import za.net.hanro50.forgiac.core.install.Installv1;
import za.net.hanro50.forgiac.core.install.Installv2;
import za.net.hanro50.forgiac.core.misc.ArgObj;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {

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

                        File virtual = new File(System.getProperty("java.io.tmpdir"), ".forgiac");
                        System.setProperty("user.dir", virtual.getAbsolutePath());
                        System.out.println(System.getProperty("user.dir"));
                        if (virtual.exists()) {
                            Util.deleteDirectory(virtual);
                        }
                        virtual.mkdir();
                        virtual.deleteOnExit();
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
        JFrame self = new JFrame("Forgiac");
        try {
            // URL iconURL = Base.class.getResource("/za/net/hanro50/forgiac/favicon.png");
            // ImageIcon icon = new ImageIcon(iconURL);
            // Image img = icon.getImage();
            Image img = ImageIO.read(Base.class.getResourceAsStream("/za/net/hanro50/forgiac/favicon.png"));
            img = img.getScaledInstance(1024, 1024, Image.SCALE_REPLICATE);
            self.setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Base.init(args, self);
        Base.standalone = true;
        try {
            new Installv2(Base.getJar(), Base.getDotMC());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                new Installv1(Base.getJar(), Base.getDotMC());
            } catch (Exception e1) {
                ExitCodes.exit(201);
            }
        }
        System.out.println("Bye World!");
        ExitCodes.exit(0);
    }
}