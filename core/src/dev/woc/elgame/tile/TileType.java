package dev.woc.elgame.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.InputStream;

public class TileType {
    private static final int DEFAULT_FLAGS = TileFlags.VISIBLE | TileFlags.COLLIDABLE;
    private String name;
    private TextureRegion texture;
    private int flags;

    public TileType(int flags, String name) {
        this.flags = flags;
        this.name = name;
    }


    public boolean checkFlags(int flags) {
        return (this.flags & flags) == flags;
    }

    public static class Builder {
        private int flags = DEFAULT_FLAGS;
        private String name;

        public Builder(String name) {
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
