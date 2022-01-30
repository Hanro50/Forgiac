package za.net.hanro50.forgiac.basic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

public class Util {
    private static String OS = System.getProperty("os.name").toLowerCase();

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

    public static void link(Path link, Path target) throws IOException {
        try {
            System.out.println(link.getFileSystem().supportedFileAttributeViews());
            link.toFile().delete();
            if (!target.toFile().exists()) {
                target.toFile().mkdirs();
            }
            if (OS.indexOf("win") >= 0) {
                if (Float.parseFloat(System.getProperty("os.version")) < 6f) {
                    JOptionPane.showMessageDialog(null,
                            "This lib requires that mklink.\nPlease use Windows Vista or newer\n", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(102);
                }

                try {
                    Runtime.getRuntime().exec(new String[] { "C:\\WINDOWS\\SYSTEM32\\CMD.EXE", "/c", "mklink", "/j",
                            "\"" + link.toString() + "\"", "\"" + target.toString() + "\"" }).waitFor();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ;
                if (!link.toFile().exists()) {
                    System.exit(101);
                }
            } else {
                Files.createSymbolicLink(link, target);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(100);
        }

    }
}
