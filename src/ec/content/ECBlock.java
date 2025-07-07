package ec.content;

import ec.world.blocks.power.LiquidFloorGenerator;
import ec.world.blocks.turret.FinallyTurret;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;

public class ECBlock {
  public static Block test, t2;

  public static void load() {
    ECBlock.test = new FinallyTurret("finally") {
      {
        requirements(Category.liquid, ItemStack.with(Items.titanium, 30, Items.metaglass, 40));
        size = 3;
      }
    };
    ECBlock.t2 = new LiquidFloorGenerator("t2") {
      {
        requirements(Category.liquid, ItemStack.with(Items.titanium, 30, Items.metaglass, 40));
        size = 3;

      }
    };
  }

}
