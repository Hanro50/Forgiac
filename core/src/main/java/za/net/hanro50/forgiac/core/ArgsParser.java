package za.net.hanro50.forgiac.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgsParser {

   private final static Map<String, ArgObj> parsedArgz = new HashMap<>();

   public static void Register(String com, ArgObj arg) {
      com = "--" + com.toLowerCase();
      parsedArgz.put(com, arg);
   }

   static {
      Register("help", new ArgObj("Shows this dialogue", new String[0], (a) -> {
         System.out.println("\u001b[1mHelp commands:\u001b[0m");
         parsedArgz.forEach((k, v) -> {
            String argCN = "";
            if (v.argCount > 0) {
               argCN = "\u001b[31m\u001b[1m <" + String.join("> <", v.argNames) + ">";
            }
            System.out.format("%2s\u001b[32m%-76s\u001b[0m\n", "", k + argCN);
            String disc = v.discription;

            while (disc.length() > 70) {
               int cutVal = disc.substring(0, 70).lastIndexOf(" ");
               System.out.format("%-10s%-70s\n", "", disc.substring(0, cutVal));
               disc= disc.substring(cutVal).trim();
            }
            System.out.format("%-10s%-70s\n", "", disc);
            System.out.println("");
         });
         System.exit(0);
      }));
   }

   public ArgsParser(String[] args) {
      List<String> lst = new ArrayList<>();
      for (String arg : args) {
         lst.add(arg);
      }

      for (int i = 0; i < lst.size(); i++) {
         if (lst.get(i).startsWith("--")) {
            ArgObj arg = parsedArgz.get(lst.get(i).toLowerCase());
            if (arg == null)
               continue;
            i++;
            int k = i + arg.argCount;
            String[] parg = new String[arg.argCount];
            int f = 0;
            for (; i < k && i < lst.size(); i++) {
               parg[f++] = lst.get(i);
            }
            arg.execute.accept(parg);
            --i;
         }
      }

   }

}
