/*    */ package Party;
/*    */ 
/*    */ import PvpBalance.PVPPlayer;
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ public class Invite
/*    */ {
/*    */   private PVPPlayer sender;
/*    */   private Party party;
/*    */ 
/*    */   public Invite(PVPPlayer dp)
/*    */   {
/* 14 */     this.sender = dp;
/* 15 */     this.party = dp.getParty();
/*    */   }
/*    */ 
/*    */   public PVPPlayer getSender()
/*    */   {
/* 20 */     return this.sender;
/*    */   }
/*    */ 
/*    */   public void accept(PVPPlayer dp)
/*    */   {
/* 25 */     if (this.party == null)
/*    */     {
/* 27 */       this.party = new Party(this.sender);
/* 28 */       this.sender.setParty(this.party);
/*    */     }
/* 30 */     this.party.addPlayer(dp);
/*    */   }
/*    */ 
/*    */   public void decline(PVPPlayer dp)
/*    */   {
/* 35 */     this.sender.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.RED + " Declined Your Invitation!");
/*    */   }
/*    */ 
/*    */   public boolean isPartyFull()
/*    */   {
/* 41 */     return this.party.isFull();
/*    */   }
/*    */ 
/*    */   public Party getParty()
/*    */   {
/* 46 */     return this.party;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Party.Invite
 * JD-Core Version:    0.6.2
 */