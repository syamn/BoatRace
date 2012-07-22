package syam.BoatRace.Listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Race.BRBoat;
import syam.BoatRace.Race.Race;


public class BRVehicleListener implements Listener{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;

	public BRVehicleListener(final BoatRace plugin){
		this.plugin = plugin;
	}

	/* 登録するイベントはここから下に */

	/**
	 * 乗り物が壊れた
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVehicleDestroy(final VehicleDestroyEvent event){
		Vehicle vehicle = event.getVehicle();
		// ゲームワールドでなければ返す
		if (vehicle.getWorld() != Bukkit.getWorld(plugin.getConfigs().gameWorld))
			return;

		// 設定とインスタンスチェック
		if (plugin.getConfigs().dropBoat && vehicle instanceof Boat){
			ItemStack boatStack = new ItemStack(Material.BOAT);
			Location loc = vehicle.getLocation();
			loc.getWorld().dropItemNaturally(loc, boatStack);
			event.setCancelled(true);
			vehicle.remove();
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVehicleMove(final VehicleMoveEvent event){
		// ボート以外は返す
		if (!(event.getVehicle() instanceof Boat))
			return;

		Boat boat = (Boat) event.getVehicle();

		// プレイヤーが乗っていないものは返す
		if (boat.getPassenger() == null || !(boat.getPassenger() instanceof Player))
			return;

		Player player = (Player) boat.getPassenger();

		// ゲームワールド以外は返す
		if (boat.getLocation().getWorld() != Bukkit.getWorld(plugin.getConfigs().gameWorld))
			return;

		// 参加中のレースチェック
		Race race = null;
		for (Race r : plugin.races.values()){
			if (r.isJoined(player))
				race = r;
		}
		if (race == null) return;

		race.checkBoatLocation(boat);

//		Vector nowVec = boat.getVelocity();
//		Vector newVec = new Vector(nowVec.getX() * 1.5D, nowVec.getY(), nowVec.getZ() * 1.5D);
//		if (newVec.getX() > 4.0D || newVec.getZ() > 4.0D)
//			return;
//		boat.setVelocity(newVec);
	}

	//@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVehicleUpdate(final VehicleUpdateEvent event){
		// ボート以外は返す
		if (!(event.getVehicle() instanceof Boat))
			return;

		Boat bBoat = (Boat) event.getVehicle();

		// プレイヤーが乗っていないものは返す
		if (bBoat.getPassenger() == null || !(bBoat.getPassenger() instanceof Player))
			return;

		Player player = (Player) bBoat.getPassenger();

		Race race = null;
		for (Race check : plugin.races.values()){
			if (check.isJoined(player)){
				race = check; continue;
			}
		}

		// 乗っているプレイヤーがゲームに参加していなければ返す
		if (race == null) return;


	}
}
