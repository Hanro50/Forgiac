package za.net.hanro50.forgiac.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class Install {
    URLClassLoader child;

    public Class load(String name) throws ClassNotFoundException {
        return Class.forName(name, true, child);
    }

    public Class simplify(Class clazz) throws ClassNotFoundException {
        if (clazz.getName().indexOf("$") > 0) {
            System.out.println(clazz.getName());
            clazz = load(clazz.getName().substring(0, clazz.getName().indexOf("$")));
        }
        return clazz;
    }

    public Class[] clasify(Object... Argz) throws ClassNotFoundException {
        Class[] classes = new Class[Argz.length];
        for (int i = 0; i < Argz.length; i++) {
            classes[i] = simplify(Argz[i].getClass());
        }
        return classes;
    }

    public Object construct(Class clazz, Object... Argz)
            throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return clazz.getConstructor(clasify(Argz)).newInstance(Argz);
    }

    public Object invoke(Object obj, String name, Object... Argz) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        Class clazz;
        Object object = null;
        if (obj instanceof Class) {
            clazz = (Class) obj;
        } else {
            clazz = simplify(obj.getClass());
            object = obj;
        }
        try {
            Method method = clazz.getDeclaredMethod(name, clasify(Argz));
            return method.invoke(object, Argz);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            System.out.println("Cannot invoke default method of invocation. Going with plan B...huh...Plan F");
        }
        Method[] methodz = clazz.getMethods();
        for (Method method : methodz) {
            System.out.println(method.getName() + ":" + method.getParameterTypes().length);
            if (method.getParameterTypes().length == Argz.length && method.getName() == name) {
                return method.invoke(object, Argz);
            }
        }

        for (Method method : methodz) {
            if (method.getName() == name) {
                Object[] objs = new Object[method.getParameterTypes().length ];
                for (int g66 = 0; g66<method.getParameterTypes().length;g66++) {
                    objs[g66]=g66<Argz.length?Argz[g66]:null;
                }
                return method.invoke(object, objs);
            }
        }
        for (Method method : methodz) {

            if (method.getParameterTypes().length == Argz.length) {
                return method.invoke(object, Argz);
            }
        }

        throw new NoSuchMethodException();
    }

    public Install(File jar, File dotMC) throws MalformedURLException {
        System.out.println("[core]: Using jar: " + jar.getAbsolutePath());
        System.out.println("[core]: Using .minecraft: " + dotMC.getAbsolutePath());
        child = new URLClassLoader(new URL[] { jar.toURI().toURL() }, this.getClass().getClassLoader());
        File launcherProfiles = new File(dotMC, "launcher_profiles.json");
        if (!launcherProfiles.exists()) {
            try {
                launcherProfiles.createNewFile();
                FileWriter myWriter = new FileWriter(launcherProfiles);
                myWriter.write("{\"launcherVersion\" : {},\"profiles\" : {},\"settings\" : {}}");
                myWriter.close();
            } catch (IOException e) {
            }
        }
    }
}
