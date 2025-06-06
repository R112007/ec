package ec.world.blocks.turret;

import arc.Events;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Call;
import mindustry.world.blocks.defense.turrets.BaseTurret;

import static mindustry.Vars.*;

public class FinallyTurret extends BaseTurret {
  public FinallyTurret(String name) {
    super(name);
    this.solid = true;
    this.update = true;
  }

  public class FinallyTurretBuild extends BaseTurretBuild {
    @Override
    public void updateTile() {
    }

    @Override
    public void damage(float damage) {
    }
  }
}
