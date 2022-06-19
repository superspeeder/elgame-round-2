package dev.woc.elgame.render;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.woc.elgame.utils.NamespacedPath;
import dev.woc.elgame.utils.Utils;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface BaseTexture {

    TextureRegion getRegion();


    static BaseTexture load(NamespacedPath path) {
        String data = Utils.loadFileFromNSPathWithPrefixPathAndExtension(path, "textures", "texture.json");
        JSONObject obj = new JSONObject(data);
        String texName = Utils.flexindex(path.getPath().split("[/\\\\]"), -1);

        TextureAtlas atlas = AtlasSystem.get(new NamespacedPath(obj.getString("atlas")));

        if (obj.has("animation")) {
            // create an animated obj
            JSONObject animo = obj.getJSONObject("animation");
            int fr = animo.getInt("frame-rate");

            return new AnimatedTexture(Arrays.stream(atlas.findRegions(texName).toArray(TextureAtlas.AtlasRegion.class)), fr);
        } else {
            int i = -1; // ignored
            if (obj.has("id")) {
                i = obj.getInt("id");
            }

            TextureRegion reg;
            if (i >= 0) {
                reg = atlas.findRegion(texName, i);
            } else {
                reg = atlas.findRegion(texName);
            }

            return new StaticTexture(reg);
        }


    }
}
