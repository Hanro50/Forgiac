package za.net.hanro50.forgiac.basic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import za.net.hanro50.forgiac.core.ArgObj;
import za.net.hanro50.forgiac.core.ArgsParser;
import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.install.Installv1;
import za.net.hanro50.forgiac.core.install.Installv2;

/**
 * Hello world!
 *
 */
public class App {
    /**
     * @param directoryToBeDeleted
     * @return
     * @see https://www.baeldung.com/java-delete-directory
     */
    public static boolean deleteDirectory(File directoryToBeDeleted) {
        if (!Files.isSymbolicLink(directoryToBeDeleted.toPath())) {
            File[] allContents = directoryToBeDeleted.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file);
                }
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static void main(String[] args) throws Exception {

        ArgsParser.Register("virtual", new ArgObj(
                "Used to install forge in launcher environments that don't emulate the vanilla launcher's file structure",
                new String[] { "version folder", "library folder" }, (argz) -> {
                    try {
                        System.out.println(argz[0]);
                        System.out.println(argz[1]);
                        if (Base.isLocked()) {
                            System.err.println("[basic]: The lock parameter needs to after the virtual parameter");
                            System.exit(0);
                        }

                        File virtual = new File(System.getProperty("user.dir"), ".temp");
                        System.setProperty("user.dir", virtual.getAbsolutePath());
                        System.out.println(System.getProperty("user.dir"));
                        if (virtual.exists()) {
                            deleteDirectory(virtual);
                        }
                        virtual.mkdir();
                        virtual.deleteOnExit();
                        Base.setDotMC(virtual);

                        System.out.println("[basic]: Using dir " + virtual.getAbsolutePath());

                        File link_versions = new File(virtual, "versions");
                        link_versions.deleteOnExit();

                        File og_versions = new File(argz[0]);
                        Files.createSymbolicLink(link_versions.toPath(), og_versions.toPath());

                        File link_libraries = new File(virtual, "libraries");
                        link_libraries.deleteOnExit();

                        File og_libraries = new File(argz[1]);
                        Files.createSymbolicLink(link_libraries.toPath(), og_libraries.toPath());
                    } catch (IOException e) {
                        System.err.println("[basic]: Could not create virtual folder");
                        e.printStackTrace();
                        System.exit(0);
                    }
                }));

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