package syam.BoatRace.Listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;

import syam.BoatRace.BoatRace;


public class BRVehicleListener{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;

	public BRVehicleListener(final BoatRace plugin){
		this.plugin = plugin;
	}

	/* 登録するイベントはここから下に */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVehicleDestroy(final VehicleDestroyEvent event){
		Vehicle vehicle = event.getVehicle();
		// ゲームワールドでなければ返す
		if (vehicle.getWorld() != Bukkit.getWorld(plugin.getConfigs().gameWorld))
			return;

		// 設定とインスタンスチェック
		if (plugin.getConfigs().dropBoat && vehicle instanceof Boat){
			ItemStack item = new ItemStack(Material.BOAT);
			Location loc = vehicle.getLocation();
			loc.getWorld().dropItem(loc, item);
			event.setCancelled(true);
			vehicle.remove();
		}
	}
}
