package dev.woc.elgame.utils;

public class NamespacedPath {

    private final String namespace;
    private final String path;

    public NamespacedPath(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public NamespacedPath(String nspath) {
        String[] nspp = nspath.split("::", 2);
        if (nspp.length == 1) {
            namespace = "base";
            path = nspath;
            System.out.println("FUCK " + this);
        } else {
            namespace = nspp[0];
            path = nspp[1];
        }
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        return namespace + "::" + path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamespacedPath that = (NamespacedPath) o;

        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
