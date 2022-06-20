package dev.woc.elgame.world;

import dev.woc.elgame.entity.Entity;
import dev.woc.elgame.tile.TileType;
import dev.woc.elgame.tile.TileTypes;
import dev.woc.elgame.utils.Vector2i;
import noise.OpenSimplex2;

import java.util.Set;

public class Chunk {

    public static final int SIZE = 64;
    private World world;
    private Vector2i pos;
    private Set<Entity> entities;

    private TileType[][] map = new TileType[SIZE][SIZE];

    public Chunk(World world, Vector2i pos) {
        this.world = world;
        this.pos = pos;


        Vector2i highestPoint = new Vector2i(0, pos.y * SIZE);
        boolean containedSurface = false;


        for (int x = 0 ; x < SIZE ; x++) {
            int tx = x + pos.getX() * SIZE;
            float mountainness_mod = (float) Math.sqrt(OpenSimplex2.noise2(world.getSeed(), tx / 1500.0f, 100.0f) * 2.0f + 2.0f);
            float crag_mod = (float) Math.max(1.5f, Math.pow(OpenSimplex2.noise2(world.getSeed(), tx / 1500.0f, 300.0f) * 2.0f + 3.0f, 2));
            float height_mod = (float) Math.sqrt(OpenSimplex2.noise2(world.getSeed(), tx / 1500.0f, 500.0f) + 1.0f) * 150;

            int h = (int) ((world.getSurfaceGenerationNoise().eval(tx / (150.0f * crag_mod), 0) + 1.0f) * 50.0f * mountainness_mod + height_mod);
            if (h > highestPoint.y) {
                highestPoint.x = tx;
                highestPoint.y = h;
            }
            for (int y = 0 ; y < SIZE ; y++) {
                int ty = y + pos.getY() * SIZE;
                if (ty < h - 5) {
                    map[y][x] = TileTypes.STONE;
                } else if (ty == h - 5) {
                    map[y][x] = TileTypes.DIRT_STONE;
                } else if (ty < h) {
                    map[y][x] = TileTypes.DIRT;
                } else if (ty == h) {
                    containedSurface = true;
                    map[y][x] = TileTypes.GRASS;
                } else {
                    map[y][x] = TileTypes.AIR;
                }
            }
        }

        if (containedSurface) {
            System.out.println(highestPoint);
            for (int y = highestPoint.y + 1; y < highestPoint.y + 6; y++) {
                world.setTile(highestPoint.x, y, TileTypes.WOOD);
            }

            world.setTile(highestPoint.x, highestPoint.y + 6, TileTypes.SPINNY);
        }


        System.out.println("Loaded " + pos);

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

    public static Chunk load(World world, Vector2i pos) {

        return new Chunk(world, pos);
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
