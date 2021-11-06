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
      Register("help", new ArgObj("Shows this dialogue", 0, (a) -> {
         System.out.println("Help commands:");
         parsedArgz.forEach((k, v) -> {
            System.out.println("\t" + k + " : " + v.discription);
         });
         System.exit(0);
      }));
   }

   public ArgsParser(String[] args) {
      List<String> lst = new ArrayList<>();
      for (String arg : args) {
         lst.add(arg.toLowerCase());
      }

      for (int i = 0; i<lst.size();i++){
         if (lst.get(i).startsWith("--")){
            ArgObj arg = parsedArgz.get(lst.get(i));
            if (arg == null) continue;
            i++;
            int k = i+arg.argCount;
            String[] parg = new String[arg.argCount];
            int f = 0;
            for (;i<k &&i<lst.size();i++){
               parg[f] = lst.get(i);
            }
            arg.execute.accept(parg);
            --i;
         }
      }

   }

}
