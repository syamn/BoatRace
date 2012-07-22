package syam.BoatRace.Race;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;

import syam.BoatRace.BoatRace;

public class BoatManager{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;
	public BoatManager(final BoatRace plugin){
		this.plugin = plugin;
	}

	private Map<Integer, BRBoat> boatMap = new HashMap<Integer, BRBoat>();

	/**
	 * ボートマップに新規ボート追加
	 * @param boat
	 */
	public void addBoat(BRBoat boat){
		BRBoat b = boatMap.get(boat.getEID());
		if (b != null) boatMap.remove(boat.getEID());

		boatMap.put(boat.getEID(), boat);
	}

	/**
	 * BRBoatを返す マップになければnull
	 * @param boat
	 * @return
	 */
	public BRBoat getBRBoat(Boat boat){
		return boatMap.get(boat.getEntityId());
	}

	/**
	 * ボートマップからボートを削除
	 * @param boat
	 */
	public void removeBRBoat(Boat boat){
		if (getBRBoat(boat) != null)
			boatMap.remove(boat.getEntityId());
	}

	/**
	 * ゲームワールドのすべてのボートエンティティに対してonUpdateを発行する
	 */
	public void updateBoats(){
		Collection<Boat> boatList = Bukkit.getWorld(plugin.getConfigs().gameWorld).getEntitiesByClass(Boat.class);
		for (Boat bBoat : boatList){
			onUpdate(bBoat);
		}
	}

	/**
	 * ボートの更新時に呼ばれる
	 * @param bBoat
	 */
	public void onUpdate(Boat bBoat){
		BRBoat boat = getBRBoat(bBoat);
		if (boat == null){
			boat = new BRBoat(bBoat);
			addBoat(boat);
		}
		
		
	}
}
