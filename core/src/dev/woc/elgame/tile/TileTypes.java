package dev.woc.elgame.tile;

import dev.woc.elgame.utils.NamespacedPath;

public class TileTypes {
    private static NamespacedPath makens(String s) { return new NamespacedPath(s); }

    public static final TileType AIR = new TileType.Builder(makens("base::air"))
            .flags(0)
            .build();
    public static final TileType STONE = new TileType.Builder(makens("base::stone"))
            .flags(TileFlags.COLLIDABLE | TileFlags.VISIBLE)
            .build();

    public static final TileType DIRT = new TileType.Builder(makens("base::dirt"))
            .flags(TileFlags.COLLIDABLE | TileFlags.VISIBLE)
            .build();
}
