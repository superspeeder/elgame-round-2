package dev.woc.elgame.world;

import dev.woc.elgame.entity.Entity;
import dev.woc.elgame.tile.TileType;
import dev.woc.elgame.tile.TileTypes;
import dev.woc.elgame.utils.Vector2i;

import java.util.Set;

public class Chunk {

    public static final int SIZE = 64;
    private Vector2i pos;
    private Set<Entity> entities;

    private TileType[][] map = new TileType[SIZE][SIZE];

    public Chunk(Vector2i pos) {
        this.pos = pos;

        if (pos.y < 0) {
            for (int x = 0 ; x < SIZE ; x++) {
                for (int y = 0 ; y < SIZE ; y++) {
                    map[y][x] = TileType.
                }
            }
        } else {
            for (int x = 0 ; x < SIZE ; x++) {
                for (int y = 0 ; y < SIZE ; y++) {
                    map[y][x] = TileTypes.AIR;
                }
            }
        }
    }

    public void set(int x, int y, TileType tile) {
        set(new Vector2i(x, y), tile);
    }

    public void set(Vector2i tilePos, TileType tile) {
        Vector2i local = this.localizePosition(tilePos);
        setLocal(local, tile);
    }

    public void setLocal(Vector2i localPos, TileType tile) {
        setLocal(localPos.x, localPos.y, tile);
    }

    public void setLocal(int x, int y, TileType tile) {
        map[y][x] = tile;
    }

    public Vector2i localizePosition(Vector2i tilePos) {
        return new Vector2i(tilePos.x - (SIZE * pos.x), tilePos.y - (SIZE * pos.y));
    }

    public static Vector2i getChunkPositionContainingTile(Vector2i tilePosition) {
        return new Vector2i(Math.floorDiv(tilePosition.x, SIZE), Math.floorDiv(tilePosition.y, SIZE));
    }

    public void onRemove() {

    }

    public static Chunk load(Vector2i pos) {

        return new Chunk(pos);
    }

    public TileType getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }
    public TileType getTile(Vector2i tilePosition) {
        return getTileLocal(localizePosition(tilePosition));
    }

    public TileType getTileLocal(Vector2i localPos) {
        return getTileLocal(localPos.x, localPos.y);
    }

    public TileType getTileLocal(int x, int y) {
        return map[y][x];
    }


}
