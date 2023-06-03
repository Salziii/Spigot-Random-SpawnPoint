private static Location getHighestRandomLocation(World world) {

    ArrayList<Material> blacklistedMaterials = new ArrayList<>();

    // Either you regulate it yourself with the config or you take the file class from my other repository
    File config = new File("plugins/plugin-name/config.yml");
    config.getKeys("world.spawning.blacklist.blocks").forEach(blockKey -> blacklistedMaterials.add(Material.getMaterial((String) config.get("world.spawning.blacklist.blocks."+blockKey))));

    int minX = (int) config.get("world.spawning.minX");
    int maxX = (int) config.get("world.spawning.maxX");
    int minZ = (int) config.get("world.spawning.minZ");
    int maxZ = (int) config.get("world.spawning.maxZ");

    int trys = 0;
    while (trys < (int) config.get("world.spawning.maxTrys")) {
        trys++;
        int x = minX + ThreadLocalRandom.current().nextInt((maxX - minX) + 1);
        int z = minZ + ThreadLocalRandom.current().nextInt((maxZ - minZ) + 1);

        for (int y = world.getHighestBlockYAt(x, z); y >= 0; y--) {
            if (!blacklistedMaterials.contains(world.getBlockAt(x, y, z).getType()) && world.getBlockAt(x, y+1, z).getType().isAir() && world.getBlockAt(x, y+2, z).getType().isAir())
                return world.getBlockAt(x, y, z).getLocation().add(0.5d, 1d, 0.5d);
        }
    }

    return world.getSpawnLocation();
    
}