package org.clm.javaproject.designpattern.builder;

//复杂地形构造器
public class ComplexTerrainBuilder implements TerrainBuilder{
    private Terrain terrain = new Terrain();
    @Override
    public TerrainBuilder buildWall() {
        terrain.w = new Wall(1,2,3,4);
        return this;
    }

    @Override
    public TerrainBuilder buildFort() {
        terrain.f = new Fort(1,2,3,4);
        return this;
    }

    @Override
    public TerrainBuilder buildMine() {
        terrain.m = new Mine(1,2,3,4);
        return this;
    }

    @Override
    public Terrain build() {
        return terrain;
    }
}
