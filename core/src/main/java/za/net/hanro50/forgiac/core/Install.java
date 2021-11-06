package za.net.hanro50.forgiac.core;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.function.Predicate;

import javax.swing.JFileChooser;

@SuppressWarnings("all")
public class Install {
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
            System.out.println("Cannot invoke default method of invocation. Going with plan B...huh...Plan F");
        }
        Method[] methodz = clazz.getMethods();

        for (Method method : methodz) {
            if (method.getParameterTypes().length == Argz.length) {
                return method.invoke(object, Argz);
            }
        }
        for (Method method : methodz) {
            System.out.println(method.getName());
            for (TypeVariable<Method> method2 : method.getTypeParameters()) {
                System.out.print(method2.getName());
            }
            System.out.println();
        }
        throw new NoSuchMethodException();
    }

    /** Installer for 1.12+ installer */

    private void installer(File jar, File MCpath, Object callback) throws Exception {
        child = new URLClassLoader(new URL[] { jar.toURI().toURL() }, this.getClass().getClassLoader());
        Class Util = load("net.minecraftforge.installer.json.Util");
        Object InstallProfile = invoke(Util, "loadInstallProfile");
        System.out.println(InstallProfile);

        JFileChooser filesjooser = new JFileChooser();
        filesjooser.showOpenDialog(null);
        Class ClientInstall = load("net.minecraftforge.installer.actions.ClientInstall");

        Object obj = construct(ClientInstall, InstallProfile, callback);
        File installer = new File(obj.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        Predicate<String> optionals = a -> true;
        try {
            invoke(obj, "run", MCpath, optionals, installer);
        } catch (ReflectiveOperationException e) {
            invoke(obj, "run", MCpath, optionals);
        }
    }

    public Install(File jar, File MCpath, Object callback) throws Exception {
        installer(jar, MCpath, callback);
    }

    public Install(File jar, File MCpath) throws Exception {
        Class ProgressCallback = load("net.minecraftforge.installer.actions.ProgressCallback");
        Object withOutputs = ProgressCallback.getField("TO_STD_OUT").get(null);
        installer(jar, MCpath, withOutputs);
    }
}
