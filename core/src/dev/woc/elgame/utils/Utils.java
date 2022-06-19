package dev.woc.elgame.utils;

import com.badlogic.gdx.Gdx;

public class Utils {
    public static String loadFileFromNSPathWithPrefixPath(NamespacedPath path, String prefixPath) {
        return Gdx.files.internal(makePath(path.getNamespace(), prefixPath, path.getPath())).readString();
    }

    public static String makePath(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String s : parts) {
            sb.append(s);
            if (!(s.endsWith("/") || s.endsWith("\\"))) {
                sb.append('/');
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static <T> T flexindex(T[] items, int i) {
        if (i >= 0) {
            return items[i];
        }

        int ii = items.length + i;
        return items[ii]; // -1 means last item
    }

    public static String loadFileFromNSPathWithPrefixPathAndExtension(NamespacedPath path, String prefixPath, String ext) {
        return Gdx.files.internal(makePath(path.getNamespace(), prefixPath, path.getPath()) + "." + ext).readString();
    }
}
