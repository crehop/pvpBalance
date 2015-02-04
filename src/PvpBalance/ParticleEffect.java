/*     */ package PvpBalance;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public enum ParticleEffect
/*     */ {
/*  13 */   SMOKE("smoke", 0), HUGE_EXPLOSION("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORKS_SPARK("fireworksSpark", 2), BUBBLE("bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA("townaura", 6), CRIT("crit", 7), MAGIC_CRIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), WITCH_MAGIC("witchMagic", 13), NOTE("note", 14), PORTAL("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17), FLAME("flame", 18), LAVA("lava", 19), FOOTSTEP("footstep", 20), SPLASH("splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23), RED_DUST("reddust", 24), SNOWBALL_POOF("snowballpoof", 25), DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_SHOVEL("snowshovel", 28), SLIME("slime", 29), HEART("heart", 30), ANGRY_VILLAGER("angryVillager", 31), HAPPY_VILLAGER("happyVillager", 32), ICONCRACK("iconcrack", 33), TILECRACK("tilecrack", 34);
/*     */ 
/*     */   private String name;
/*     */   private int id;
/*     */   private static final Map<String, ParticleEffect> NAME_MAP;
/*     */   private static final Map<Integer, ParticleEffect> ID_MAP;
/*     */ 
/*     */   static
/*     */   {
/*  31 */     NAME_MAP = new HashMap();
/*  32 */     ID_MAP = new HashMap();
/*     */ 
/*  34 */     for (ParticleEffect effect : values()) {
/*  35 */       NAME_MAP.put(effect.name, effect);
/*  36 */       ID_MAP.put(Integer.valueOf(effect.id), effect);
/*     */     }
/*     */   }
/*     */ 
/*     */   private ParticleEffect(String name, int id)
/*     */   {
/*  19 */     this.name = name;
/*  20 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */ 
/*     */   public int getId() {
/*  28 */     return this.id;
/*     */   }
/*     */ 
/*     */   public static ParticleEffect fromName(String name)
/*     */   {
/*  41 */     if (name == null) {
/*  42 */       return null;
/*     */     }
/*  44 */     for (Map.Entry e : NAME_MAP.entrySet()) {
/*  45 */       if (((String)e.getKey()).equalsIgnoreCase(name)) {
/*  46 */         return (ParticleEffect)e.getValue();
/*     */       }
/*     */     }
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   public static ParticleEffect fromId(int id) {
/*  53 */     return (ParticleEffect)ID_MAP.get(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */   public static void sendToPlayer(ParticleEffect effect, Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
/*  57 */     Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
/*  58 */     sendPacket(player, packet);
/*     */   }
/*     */ 
/*     */   public static void sendToLocation(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
/*  62 */     Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
/*  63 */     for (Player player : Bukkit.getOnlinePlayers())
/*  64 */       sendPacket(player, packet);
/*     */   }
/*     */ 
/*     */   public static void sendCrackToPlayer(boolean icon, int id, byte data, Player player, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception
/*     */   {
/*  69 */     Object packet = createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
/*  70 */     sendPacket(player, packet);
/*     */   }
/*     */ 
/*     */   public static void sendCrackToLocation(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
/*  74 */     Object packet = createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
/*  75 */     for (Player player : Bukkit.getOnlinePlayers())
/*  76 */       sendPacket(player, packet);
/*     */   }
/*     */ 
/*     */   public static Object createPacket(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception
/*     */   {
/*  81 */     if (count <= 0)
/*  82 */       count = 1;
/*  83 */     Object packet = getPacket63WorldParticles();
/*  84 */     setValue(packet, "a", effect.name);
/*  85 */     setValue(packet, "b", Float.valueOf((float)location.getX()));
/*  86 */     setValue(packet, "c", Float.valueOf((float)location.getY()));
/*  87 */     setValue(packet, "d", Float.valueOf((float)location.getZ()));
/*  88 */     setValue(packet, "e", Float.valueOf(offsetX));
/*  89 */     setValue(packet, "f", Float.valueOf(offsetY));
/*  90 */     setValue(packet, "g", Float.valueOf(offsetZ));
/*  91 */     setValue(packet, "h", Float.valueOf(speed));
/*  92 */     setValue(packet, "i", Integer.valueOf(count));
/*  93 */     return packet;
/*     */   }
/*     */ 
/*     */   public static Object createCrackPacket(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
/*  97 */     if (count <= 0)
/*  98 */       count = 1;
/*  99 */     Object packet = getPacket63WorldParticles();
/* 100 */     String modifier = "iconcrack_" + id;
/* 101 */     if (!icon) {
/* 102 */       modifier = "tilecrack_" + id + "_" + data;
/*     */     }
/* 104 */     setValue(packet, "a", modifier);
/* 105 */     setValue(packet, "b", Float.valueOf((float)location.getX()));
/* 106 */     setValue(packet, "c", Float.valueOf((float)location.getY()));
/* 107 */     setValue(packet, "d", Float.valueOf((float)location.getZ()));
/* 108 */     setValue(packet, "e", Float.valueOf(offsetX));
/* 109 */     setValue(packet, "f", Float.valueOf(offsetY));
/* 110 */     setValue(packet, "g", Float.valueOf(offsetZ));
/* 111 */     setValue(packet, "h", Float.valueOf(0.1F));
/* 112 */     setValue(packet, "i", Integer.valueOf(count));
/* 113 */     return packet;
/*     */   }
/*     */ 
/*     */   private static void setValue(Object instance, String fieldName, Object value) throws Exception {
/* 117 */     Field field = instance.getClass().getDeclaredField(fieldName);
/* 118 */     field.setAccessible(true);
/* 119 */     field.set(instance, value);
/*     */   }
/*     */ 
/*     */   private static Object getEntityPlayer(Player p) throws Exception {
/* 123 */     Method getHandle = p.getClass().getMethod("getHandle", new Class[0]);
/* 124 */     return getHandle.invoke(p, new Object[0]);
/*     */   }
/*     */ 
/*     */   private static String getPackageName() {
/* 128 */     return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
/*     */   }
/*     */ 
/*     */   private static Object getPacket63WorldParticles() throws Exception {
/* 132 */     Class packet = Class.forName(getPackageName() + ".PacketPlayOutWorldParticles");
/* 133 */     return packet.getConstructors()[0].newInstance(new Object[0]);
/*     */   }
/*     */ 
/*     */   private static void sendPacket(Player p, Object packet) throws Exception {
/* 137 */     Object eplayer = getEntityPlayer(p);
/* 138 */     Field playerConnectionField = eplayer.getClass().getField("playerConnection");
/* 139 */     Object playerConnection = playerConnectionField.get(eplayer);
/* 140 */     for (Method m : playerConnection.getClass().getMethods())
/* 141 */       if (m.getName().equalsIgnoreCase("sendPacket")) {
/* 142 */         m.setAccessible(true);
/* 143 */         m.invoke(playerConnection, new Object[] { packet });
/* 144 */         return;
/*     */       }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.ParticleEffect
 * JD-Core Version:    0.6.2
 */