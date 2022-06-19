package dev.woc.elgame.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.woc.elgame.render.BaseTexture;
import dev.woc.elgame.utils.NamespacedPath;

import java.io.InputStream;

public class TileType {
    private static final int DEFAULT_FLAGS = TileFlags.VISIBLE | TileFlags.COLLIDABLE;
    private NamespacedPath name;
    private BaseTexture texture;
    private int flags;

    public TileType(int flags, NamespacedPath name) {
        this.flags = flags;
        this.name = name;
    }

    public BaseTexture getTexture() {
        if (texture == null) {
            texture = BaseTexture.load(new NamespacedPath(name.getNamespace(), "tile." + name.getPath()));
        }

        return texture;
    }


    public boolean checkFlags(int flags) {
        return (this.flags & flags) == flags;
    }

    public void render(SpriteBatch batch, int x, int y) {
        if (checkFlags(TileFlags.VISIBLE)) {
            if (!checkFlags(TileFlags.COMPLEX_RENDER)) {
                batch.draw(getTexture().getRegion(), x, y, 16, 16);
            }
        }
    }

    public static class Builder {
        private int flags = DEFAULT_FLAGS;
        private NamespacedPath name;

        public Builder(NamespacedPath name) {
            this.name = name;
        }

        public int getFlags() {
            return flags;
        }

        public Builder flags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder defaultFlags() {
            return flags(DEFAULT_FLAGS);
        }

        public TileType build() {
            return new TileType(
                    flags, name
            );
        }
    }
}
