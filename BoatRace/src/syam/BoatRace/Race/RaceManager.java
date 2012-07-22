package syam.BoatRace.Race;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import syam.BoatRace.BoatRace;

public class RaceManager{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;
	public RaceManager(final BoatRace plugin){
		this.plugin = plugin;
	}

	// 選択中のレース
	private static Map<String, Race> selectedRace = new HashMap<String, Race>();
	// 選択中のブロック
	private static Map<String, Location> selectedBlock = new HashMap<String, Location>();

	// ゲームマネージャモードのリスト
	private static List<String> brManager = new ArrayList<String>();

	/* getter/setter */

	/**
	 * 指定したレースを選択中にする
	 * @param player 対象プレイヤー
	 * @param race 対象レース
	 */
	public static void setSelectedRace(Player player, Race race){
		selectedRace.put(player.getName(), race);
	}
	/**
	 * 選択中のレースを返す
	 * @param player 対象のプレイヤー
	 * @return null または対象のレース
	 */
	public static Race getSelectedGame(Player player){
		if (player == null || !selectedRace.containsKey(player.getName())){
			return null;
		}else{
			return selectedRace.get(player.getName());
		}
	}

	/**
	 * 指定したブロックを選択中にする
	 * @param player プレイヤー
	 * @param loc 対象ブロックの座標
	 */
	public static void setSelectedBlock(Player player, Location loc){
		selectedBlock.put(player.getName(), loc);
	}
	/**
	 * 選択中のブロックの座標を返す
	 * @param player 対象プレイヤー
	 * @return null または対象ブロックLocation
	 */
	public static Location getSelectedBlock(Player player){
		if (player == null || !selectedBlock.containsKey(player.getName())){
			return null;
		}else{
			return selectedBlock.get(player.getName());
		}
	}

	/**
	 * プレイヤーをマネージモードにする/しない
	 * @param player 対象のプレイヤー
	 * @param state true = 管理モードにする/false = しない
	 */
	public static void setManager(Player player, boolean state){
		if (state){
			if (!brManager.contains(player.getName()))
				brManager.add(player.getName());
		}else{
			if (brManager.contains(player.getName()))
				brManager.remove(player.getName());
		}
	}
	/**
	 * プレイヤーがマネージモードかどうか返す
	 * @param player チェックするプレイヤー
	 * @return trueなら管理モード、falseなら管理モードでない
	 */
	public static boolean isManager(Player player){
		if(player != null && brManager.contains(player.getName())){
			return true;
		}else{
			return false;
		}
	}
}
