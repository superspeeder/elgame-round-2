package dev.woc.elgame.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.woc.elgame.utils.NamespacedPath;
import dev.woc.elgame.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class AtlasSystem {

    private AtlasSystem() { throw new UnsupportedOperationException("dev.woc.elgame.render.AtlasSystem may not be instantiated"); }

    private static Map<NamespacedPath, TextureAtlas> atlases = new HashMap<>();

    public static TextureAtlas get(NamespacedPath nsp) {
        if (!atlases.containsKey(nsp)) {
            atlases.put(nsp, new TextureAtlas(Gdx.files.internal(Utils.makePath(nsp.getNamespace(), "atlases", nsp.getPath(), "pack.atlas"))));
        }

        return atlases.get(nsp);
    }
}
