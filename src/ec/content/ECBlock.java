package ec.content;

import ec.world.blocks.turret.FinallyTurret;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;

public class ECBlock {
  public static Block test;

  public static void load() {
    ECBlock.test = new FinallyTurret("finally") {
      {
        requirements(Category.liquid, ItemStack.with(Items.titanium, 30, Items.metaglass, 40));
        size = 3;
      }
    };
  }

}
