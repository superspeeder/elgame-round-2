package dev.woc.elgame.render;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AnimatedTexture implements BaseTexture {
    private static int currentFrame = 0;
    private final List<TextureRegion> regions;
    private final int frameRate;

    public static void nextFrame() {
        currentFrame++;
    }

    public AnimatedTexture(Stream<TextureRegion> regions, int frameRate) {
        this.regions = regions.toList();
        this.frameRate = frameRate;
    }

    public int getAnimationFrame() {
        return Math.floorMod(Math.floorDiv(currentFrame, frameRate), regions.size());
    }

    @Override
    public TextureRegion getRegion() {
        return regions.get(getAnimationFrame());
    }
}
