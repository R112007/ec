package ec.world.meta;

import mindustry.world.meta.Stat;

/**
 * ECStat
 */
public class ECStat {

  public static final Stat dependfloor;
  static {
    dependfloor = new Stat("dependfloor", ECStatCat.depend);
  }
}
