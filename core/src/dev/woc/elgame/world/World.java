package dev.woc.elgame.world;


import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.woc.elgame.tile.TileType;
import dev.woc.elgame.utils.Vector2i;

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
            .buildAsync(Chunk::load);

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
}
