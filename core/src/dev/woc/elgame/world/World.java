package dev.woc.elgame.world;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.woc.elgame.tile.TileType;
import dev.woc.elgame.utils.noise.Noise2D;
import dev.woc.elgame.utils.Vector2i;
import dev.woc.elgame.utils.noise.FractalNoise2D;
import dev.woc.elgame.utils.noise.OpenSimplex2D;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class World {
    private AsyncLoadingCache<Vector2i, Region> regionCache = Caffeine.newBuilder()
            .maximumSize(16)
            .removalListener((key, value, cause) -> {
                if (value != null) {
                    ((Region)value).onRemove();
                }
            })
            .buildAsync(Region::load);

    private AsyncLoadingCache<Vector2i, Chunk> chunkCache = Caffeine.newBuilder()
            .maximumSize(128)
            .removalListener((key, value, cause) -> {
                if (value != null) {
                    ((Chunk)value).onRemove();
                }
            })
            .buildAsync(k -> Chunk.load(this, k));

    private long seed = System.currentTimeMillis();
    private Noise2D surfaceGenNoise = new FractalNoise2D(
            new OpenSimplex2D(seed),
            4, 2.0f, 0.5f);

    public CompletableFuture<Chunk> getChunkAsync(Vector2i position) {
        return chunkCache.get(position);
    }

    public CompletableFuture<Region> getRegionAsync(Vector2i position) {
        return regionCache.get(position);
    }

    public Optional<Chunk> getChunkIfLoaded(Vector2i position) {
        return Optional.ofNullable(chunkCache.getIfPresent(position)).map(chunkCompletableFuture -> chunkCompletableFuture.getNow(null));
    }

    public Optional<Region> getRegionIfLoaded(Vector2i position) {
        return Optional.ofNullable(regionCache.getIfPresent(position)).map(regionCompletableFuture -> regionCompletableFuture.getNow(null));
    }

    public Chunk getChunkNow(Vector2i position) throws ExecutionException, InterruptedException {
        return chunkCache.get(position).get();
    }

    public Region getRegionNow(Vector2i position) throws ExecutionException, InterruptedException {
        return regionCache.get(position).get();
    }

    public boolean isChunkLoaded(Vector2i position) {
        return getChunkIfLoaded(position).isPresent();
    }

    public boolean isRegionLoaded(Vector2i position) {
        return getRegionIfLoaded(position).isPresent();
    }

    public void setTile(Vector2i tilePosition, TileType tile) {
        CompletableFuture<Chunk> targetChunk = getChunkAsync(Chunk.getChunkPositionContainingTile(tilePosition));
        targetChunk.thenAccept(chunk -> chunk.set(tilePosition, tile));
    }

    public CompletableFuture<TileType> getTile(Vector2i tilePosition) {
        return getChunkAsync(Chunk.getChunkPositionContainingTile(tilePosition)).thenApply(chunk -> chunk.getTile(tilePosition));
    }

    public Optional<TileType> getTileIfPresent(Vector2i tilePosition) {
        return getChunkIfLoaded(Chunk.getChunkPositionContainingTile(tilePosition)).map(chunk -> chunk.getTile(tilePosition));
    }

    public Optional<TileType> getTileIfPresent(int x, int y) {
        return getTileIfPresent(new Vector2i(x, y));
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        Vector3 bl = camera.unproject(new Vector3(0, 0, 0));
        Vector3 tr = camera.unproject(new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0));
        Vector2i blt = new Vector2i(Math.floorDiv((int) bl.x, 16), Math.floorDiv((int) bl.y, 16));
        Vector2i trt = new Vector2i(Math.floorDiv((int) tr.x, 16), Math.floorDiv((int) tr.y, 16));
//        System.out.println(blt + " " + trt);
        for (int x = blt.x - 1; x < trt.x + 1 ; x++) {
            for (int y = trt.y - 1; y < blt.y + 1 ; y++) {
                int X = x;
                int Y = y;
                Optional.ofNullable(getTile(x, y).getNow(null)).ifPresent(tileType -> tileType.render(batch, X * 16, Y * 16));
            }
        }
    }

    public CompletableFuture<TileType> getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Noise2D getSurfaceGenerationNoise() {
        return surfaceGenNoise;
    }

    public void setTile(int x, int y, TileType type) {
        setTile(new Vector2i(x, y), type);
    }

    public long getSeed() {
        return seed;
    }
}
