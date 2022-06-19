package dev.woc.elgame.render;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StaticTexture implements BaseTexture {
    private TextureRegion region;

    public StaticTexture(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion getRegion() {
        return region;
    }
}
