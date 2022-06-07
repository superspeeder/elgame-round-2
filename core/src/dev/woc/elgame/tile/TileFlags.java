package dev.woc.elgame.tile;

public interface TileFlags {

    int VISIBLE = 0b1;
    int COLLIDABLE = 0b10;

    int COMPLEX = 0b100;
    int COMPLEX_RENDER = 0b1000;
}
