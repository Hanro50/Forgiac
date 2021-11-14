package za.net.hanro50.forgiac.core;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import za.net.hanro50.forgiac.core.misc.ArgObj;

public class ExitCodes {
    final static Map<Integer, String> codes = new HashMap<>();
static boolean error = false;
    public static void Init() {
        ArgsParser.Register("error_on", new ArgObj("Shows error messages on non zero exits", new String[0], (args) -> {
            error=true;
        }));
        ArgsParser.Register("err", new ArgObj("Show the possible error codes", new String[0], (args) -> {
            System.out.println("<table>");
            System.out.println("<tr><th>Codes</td><td>message</td></tr>");
            codes.forEach((key, val) -> {
                System.out.println("<tr><td>" + key + "</td><td>" + val + "</td></tr>");
            });
            System.out.println("</table>");
            ExitCodes.exit(0);
        }));
        codes.put(0, "Done!");
        codes.put(100, "Could not create virtual folder");
        codes.put(101, "Could not create junction link");
        codes.put(102, "Please use Windows Vista or later");

        codes.put(200, "User cancelled request");
        codes.put(201, "Invalid installation jar");
        codes.put(202, "Forge failed to install");

        codes.put(300, "Parameter error");

    }

    public static void exit(int code) {
        if (code != 0 && !Base.noGui && error) {
            JOptionPane.showMessageDialog(Base.self, codes.getOrDefault(code, "Unknown error"), "Exit code " + code,
                    JOptionPane.ERROR_MESSAGE);
        }
        if (Base.standalone)
            System.exit(code);
    }
}
