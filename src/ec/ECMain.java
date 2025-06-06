package ec;

import arc.util.Log;
import ec.content.ECBlock;
import mindustry.mod.Mod;

public class ECMain extends Mod {
  public ECMain() {
    Log.info("loaded ec");
  }

  @Override
  public void loadContent() {
    ECBlock.load();
  }
}
