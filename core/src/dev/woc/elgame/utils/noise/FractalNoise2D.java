package dev.woc.elgame.utils.noise;

public class FractalNoise2D implements Noise2D {
    private final Noise2D base;
    private final int octaves;
    private final float lacunarity;
    private final float gain;

    public FractalNoise2D(Noise2D base, int octaves, float lacunarity, float gain) {
        this.base = base;
        this.octaves = octaves;
        this.lacunarity = lacunarity;
        this.gain = gain;
    }

    @Override
    public float eval(float x, float y) {
        float v = 0.0f;
        float amplitude = 0.5f;
        float freq = 1.0f;

        for (int i = 0 ; i < octaves ; i++) {
            v += amplitude * base.eval(freq * x, freq * y);
            freq *= lacunarity;
            amplitude *= gain;
        }

        return v;
    }
}
