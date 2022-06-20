package dev.woc.elgame.tile;

import dev.woc.elgame.utils.NamespacedPath;

public class TileTypes {
    private static NamespacedPath makens(String s) { return new NamespacedPath(s); }

    private static TileType simpleTile(String s) { return new TileType.Builder(makens(s))
            .flags(TileFlags.COLLIDABLE | TileFlags.VISIBLE)
            .build(); }


    public static final TileType AIR = new TileType.Builder(makens("base::air"))
            .flags(0)
            .build();
    public static final TileType STONE = simpleTile("base::stone");
    public static final TileType DIRT_STONE = simpleTile("base::dirt-stone");
    public static final TileType DIRT = simpleTile("base::dirt");
    public static final TileType GRASS = simpleTile("base::grass");
    public static final TileType WOOD = simpleTile("base::wood");
    public static final TileType SPINNY = simpleTile("base::spinny-tile");
}
