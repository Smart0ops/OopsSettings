package me.smartoops.oopssettings;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {
    public Main() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        if (this.getConfig().getBoolean("Time.fixed")) {
            Iterator var2 = Bukkit.getServer().getWorlds().iterator();

            while(var2.hasNext()) {
                World w = (World)var2.next();
                w.setTime(this.getConfig().getLong("Time.meaning"));
                w.setGameRuleValue("doDaylightCycle", "false");
            }
        }

        this.getLogger().info("Плагин Включен!");
    }

    public void onDisable() {
        this.getLogger().info("Плагин Выключен");
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent e) {
        if (this.getConfig().getBoolean("Settings.noHunger")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getActivePotionEffects().clear();
        p.setLevel(0);
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setJoinMessage("");
        }

        if (this.getConfig().getBoolean("Effects.Invisibility")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
        }

        if (this.getConfig().getBoolean("Effects.Speed")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
        }

        if (this.getConfig().getBoolean("Effects.BlindNess")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
        }

        if (this.getConfig().getBoolean("LvlonJoin.enabled")) {
            p.setLevel(this.getConfig().getInt("LvlonJoin.amount"));
        }

        String gm = this.getConfig().getString("Settings.GameMode");
        if (gm != null) {
            p.setGameMode(GameMode.valueOf(gm));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setQuitMessage("");
        }

    }

    @EventHandler
    public void noDeath(PlayerDeathEvent e) {
        if (this.getConfig().getBoolean("Settings.noMessage")) {
            e.setDeathMessage("");
        }

    }

    @EventHandler
    public void noDamage(EntityDamageEvent e) {
        if (this.getConfig().getBoolean("Settings.noDamage")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassBreak")) && this.getConfig().getBoolean("Settings.noBreak")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassPlace")) && this.getConfig().getBoolean("Settings.noPlace")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noFire(BlockBurnEvent e) {
        if (this.getConfig().getBoolean("Settings.noFire")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noFire(BlockSpreadEvent e) {
        if (this.getConfig().getBoolean("Settings.noFire")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void noChat(AsyncPlayerChatEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassChat")) && this.getConfig().getBoolean("Settings.noChat")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void oCommands(PlayerCommandPreprocessEvent e) {
        if (!e.getPlayer().hasPermission(this.getConfig().getString("Permissions.bypassCommands"))) {
            if (this.getConfig().getBoolean("Settings.noCommands") && !this.getConfig().getStringList("allowed_commands").contains(e.getMessage().split(" ")[0].replace("/", ""))) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (this.getConfig().getBoolean("Settings.noExplosions")) {
            e.setCancelled(true);
        }

    }
}
