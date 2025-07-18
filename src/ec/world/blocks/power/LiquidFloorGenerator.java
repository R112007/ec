package ec.world.blocks.power;

import java.util.ArrayList;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import ec.world.meta.ECStat;
import ec.world.meta.ECStatValues;

public class LiquidFloorGenerator extends PowerGenerator {
  public Liquid[] level1 = new Liquid[] { Liquids.water };
  public Liquid[] level2 = new Liquid[] { Liquids.cryofluid };
  public Liquid[] level3 = new Liquid[] { Liquids.oil, Liquids.slag };
  public ArrayList<Floor> floors = new ArrayList<>();
  public ArrayList<Floor> liquids = new ArrayList<>();
  public Block[] blocks;
  public Effect generateEffect = Fx.none;
  public float effectChance = 0.05f;
  public float minEfficiency = 0f;
  public float displayEfficiencyScale = 1f;
  public boolean displayEfficiency = true;

  public LiquidFloorGenerator(String name) {
    super(name);
    this.powerProduction = 1.0f;
    this.noUpdateDisabled = true;
    this.placeableLiquid = true;
    blocks = Vars.content.blocks().toArray(Block.class);
    for (Block b : blocks) {
      if (b instanceof Floor) {
        floors.add((Floor) b);
      }
    }
  }

  @Override
  public void load() {
    super.load();
  }

  @Override
  public boolean canPlaceOn(Tile tile, Team team, int rotation) {
    Seq<Tile> everytiles = tile.getLinkedTilesAs(this, tempTiles);
    for (Tile t : everytiles) {
      for (Liquid liq : level2) {
        if (t.floor().liquidDrop == liq) {
          return true;
        }
      }
      for (Liquid liq : level1) {
        if (t.floor().liquidDrop == liq) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void init() {
    this.emitLight = true;
    super.init();
    lightClipSize = Math.max(lightClipSize, 45f * size * 2f * 2f);
  }

  @Override
  public void setStats() {
    super.setStats();
    stats.remove(generationType);
    stats.add(generationType, powerProduction * 60.0f / displayEfficiencyScale, StatUnit.powerSecond);
    stats.add(Stat.tiles, ECStatValues.text("220%"));
    for (Floor f : floors) {
      for (Liquid liq : level1) {
        if (f.drownTime != 0 && f.liquidDrop == liq)
          stats.add(Stat.tiles, StatValues.content(f));
      }
    }
    stats.add(Stat.tiles, ECStatValues.text("100%"));
    for (Floor f : floors) {
      for (Liquid liq : level1) {
        if (f.drownTime == 0 && f.liquidDrop == liq)
          stats.add(Stat.tiles, StatValues.content(f));
      }
    }
    stats.add(Stat.tiles, ECStatValues.text("80%"));
    for (Floor f : floors) {
      for (Liquid liq : level2) {
        if (f.liquidDrop == liq)
          stats.add(Stat.tiles, StatValues.content(f));
      }
    }
    stats.add(Stat.tiles, ECStatValues.text("0%"));
    for (Floor f : floors) {
      for (Liquid liq : level3) {
        if (f.liquidDrop == liq)
          stats.add(Stat.tiles, StatValues.content(f));
      }
    }
    for (Floor f : floors) {
      for (Liquid liq : level1) {
        if (f.liquidDrop == liq) {
          liquids.add(f);
        }
      }
      for (Liquid liq : level2) {
        if (f.liquidDrop == liq) {
          liquids.add(f);
        }
      }
    }
    stats.add(ECStat.dependfloor, ECStatValues.listcontents(liquids));
  }

  public int[] getTileNumbers(Seq<Tile> tile, int[] list) {
    for (Tile t : tile) {
      for (Liquid liq : level1) {
        if (t.floor().liquidDrop == liq && t.floor().drownTime != 0) {
          list[0]++;
        } else if (t.floor().liquidDrop == liq && t.floor().drownTime == 0) {
          list[1]++;
        }
      }
      for (Liquid liq : level2) {
        if (t.floor().liquidDrop == liq) {
          list[2]++;
        }
      }
      for (Liquid liq : level3) {
        if (t.floor().liquidDrop == liq) {
          list[3]++;
        }
      }
    }
    return list;
  }

  public float getEfficiency(int x, int y) {
    int[] tiles = { 0, 0, 0, 0, 0 };
    float sum;
    Tile tile = Vars.world.tile(x, y);
    Seq<Tile> everytiles = tile.getLinkedTilesAs(this, tempTiles);
    tiles = getTileNumbers(everytiles, tiles);
    float tileProduce = powerProduction / (size * size);
    Log.info("tileProduce " + tileProduce);
    sum = tiles[0] * tileProduce * 2.2f + tiles[1] * tileProduce * 1 + tiles[2] * tileProduce * 0.8f;
    Log.info("sum" + sum);
    return sum / powerProduction;
  }

  @Override
  public void drawPlace(int x, int y, int rotation, boolean valid) {
    super.drawPlace(x, y, rotation, valid);
    drawPotentialLinks(x, y);
    drawPlaceText(Core.bundle.format("bar.efficiency", getEfficiency(x, y) * 100f), x, y, valid);
  }

  public class LiquidFloorGeneratorBuild extends GeneratorBuild {
    public float sum;
    public float tileProduce = powerProduction / (size * size);
    public int[] tiles = { 0, 0, 0, 0, 0 };

    public int[] getTileNumber(Seq<Tile> tile, int[] list) {
      for (Tile t : tile) {
        for (Liquid liq : level1) {
          if (t.floor().liquidDrop == liq && t.floor().drownTime != 0) {
            list[0]++;
          } else if (t.floor().liquidDrop == liq && t.floor().drownTime == 0) {
            list[1]++;
          }
        }
        for (Liquid liq : level2) {
          if (t.floor().liquidDrop == liq) {
            list[2]++;
          }
        }
        for (Liquid liq : level3) {
          if (t.floor().liquidDrop == liq) {
            list[3]++;
          }
        }
      }
      return list;
    }

    @Override
    public void updateTile() {
      Seq<Tile> everytiles1 = tile.getLinkedTilesAs(this.block, tempTiles);
      tiles = getTileNumber(everytiles1, tiles);
      sum = tiles[0] * tileProduce * 2.2f + tiles[1] * tileProduce * 1 + tiles[2] * tileProduce * 0.8f;
      productionEfficiency = sum / powerProduction;
      if (productionEfficiency > 0.1f && Mathf.chanceDelta(effectChance)) {
        generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
      }
      for (int i = 0; i < 5; i++) {
        tiles[i] = 0;
      }
    }

    public boolean noinLevel3(Tile t) {
      for (var liq : level3) {
        if (t.floor().liquidDrop == liq)
          return false;
      }
      return true;
    }

    @Override
    public void draw() {
      super.draw();
    }

    public Liquid maxLiquid() {
      Seq<Tile> tilelist;
      tilelist = tile.getLinkedTilesAs(block, tempTiles);
      Liquid liq = Liquids.water;
      for (var t : tilelist) {
        if (t.floor().liquidDrop != null && noinLevel3(t)) {
          liq = t.floor().liquidDrop;
          break;
        }
      }
      return liq;
    }

    @Override
    public void afterPickedUp() {
      super.afterPickedUp();
      sum = 0f;
    }

    @Override
    public float totalProgress() {
      return enabled && sum > 0 ? super.totalProgress() : 0f;
    }

    @Override
    public void drawLight() {
      Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
    }

  }
}
