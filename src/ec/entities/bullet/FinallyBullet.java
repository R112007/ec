package ec.entities.bullet;

import mindustry.content.Fx;
import mindustry.entities.bullet.LaserBulletType;

public class FinallyBullet extends LaserBulletType {
  public float baseDamage = 300f;
  public float maxDamage = 30000f;

  public FinallyBullet() {
    this.speed = 0f;
    hitEffect = Fx.hitLaserBlast;
    hitColor = colors[2];
    despawnEffect = Fx.none;
    shootEffect = Fx.hitLancer;
    smokeEffect = Fx.none;
    hitSize = 4;
    lifetime = 16f;
    impact = true;
    keepVelocity = false;
    collides = false;
    pierce = true;
    hittable = false;
    absorbable = false;
    removeAfterPierce = false;
    delayFrags = true;
  }

}
