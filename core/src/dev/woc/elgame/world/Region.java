package dev.woc.elgame.world;

import dev.woc.elgame.utils.Vector2i;

public class Region {
    public Region(Vector2i pos) {

    }

    public void onRemove() {

    }

    public static Region load(Vector2i pos) {
        return new Region(pos);
    }
}
