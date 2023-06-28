public class RandomPosition {

    /**
     * Returns a Location,
     * which is in a certain limit range of the center,
     * is not standing on Water, Lava, or Bedrock
     * and has 1 or 2 non-solid blocks of space
     * <p>
     * The method will return 'null'
     * if the maximum number of attempts (10) that can be set in the config has been reached and no matching block was found
     *
     * @param offset certain limit range
     * @param center center
     * @param two blocks 2 son-solid blocks of space
     * @return      A valid Location or null
     * @see         Location
     */
   public static Location withOffset(Location center, int offset, boolean twoBlocks) {
      World world = center.getWorld();

      int trys = 0;
      while (trys < 10) {
         trys++;
         double x = center.getX() + ThreadLocalRandom.current().nextInt(-offset, offset+1);
         double z = center.getZ() + ThreadLocalRandom.current().nextInt(-offset, offset+1);

         for (int y = world.getHighestBlockYAt((int) x, (int) z); y >= 0; y--) {
               if ((world.getEnvironment().equals(World.Environment.CUSTOM) || world.getEnvironment().equals(World.Environment.NORMAL)) && GameManager.getBlacklistedMaterials().contains(world.getBlockAt((int) x, y, (int) z).getType())) continue;
               if (world.getBlockAt((int) x, y, (int) z).getType().isSolid() && !world.getBlockAt((int) x, y+1, (int) z).getType().isSolid() && !GameManager.getBlacklistedMaterials().contains(world.getBlockAt((int) x, y, (int) z).getType()))
                  if (twoBlocks && world.getBlockAt((int) x, y+2, (int) z).getType().isSolid()) continue;
                  return new Location(world, x, y+1, z);
         }
      }
      return null;
   }

}
