package dev.woc.elgame.utils.noise;

import noise.OpenSimplex2;

public class OpenSimplex2D implements Noise2D {
    private long seed;

    public OpenSimplex2D(long seed) {
        this.seed = seed;
    }

    @Override
    public float eval(float x, float y) {
        return OpenSimplex2.noise2(seed, x, y);
    }
}
