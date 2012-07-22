package syam.BoatRace.Listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Enum.Configables;
import syam.BoatRace.Race.Race;
import syam.BoatRace.Race.RaceManager;
import syam.BoatRace.Util.Actions;

public class BRPlayerListener implements Listener{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;

	public BRPlayerListener(final BoatRace plugin){
		this.plugin = plugin;
	}

	/* 登録するイベントはここから下に */

	/**
	 * 管理モードでブロックをクリックした
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteractAsManager(final PlayerInteractEvent event){
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if(block != null){
			// 管理モード
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && RaceManager.getManager(player) != null &&
					player.getItemInHand().getTypeId() == plugin.getConfigs().toolID && player.hasPermission("boat.admin.set")){
				/* 管理モードで特定のアイテムを持ったままブロックを右クリックした */
				Race race = RaceManager.getSelectedRace(player);
				if (race == null){
					Actions.message(null, player, "&c先に編集するゲームを選択してください！");
					return;
				}
				Location loc = block.getLocation();

				// ゲーム用ワールドでなければ返す
				if (loc.getWorld() != Bukkit.getWorld(plugin.getConfigs().gameWorld)){
					Actions.message(null, player, "&cここはゲーム用ワールドではありません！");
					return;
				}

				Configables conf = RaceManager.getManager(player);

				switch(conf){
					case START:
						// 既にスタート地点なら解除
						if (race.isStartPos(loc)){
							race.removeStartPos(loc);
							Actions.message(null, player, "&aスタート地点から解除しました！");
						}else{
							race.addStartPos(loc);
							Actions.message(null, player, "&aスタート地点を設定しました！");
						}
						event.setCancelled(true);
						event.setUseInteractedBlock(Result.DENY);
						event.setUseItemInHand(Result.DENY);
						return;

					default:
						Actions.message(null, player, "&c現在選択中の設定モードでは選択ツールを使用できません！");
						return;
				}// End switch
			}
		}
	}

	/**
	 * コマンドを使おうとした
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		// ワールドチェック
		if (player.getWorld() != Bukkit.getWorld(plugin.getConfigs().gameWorld))
			return;

		String cmdMsg = event.getMessage().trim();
		String cmds[] = cmdMsg.split(" ");
		String cmd = null;

		if (cmds.length > 1){
			cmd = cmds[0].trim();
		}else{ // cmds.length == 1
			cmd = cmdMsg;
		}

		// 存在するレースを回す
		for (Race race : plugin.races.values()){
			if (!race.isReady() && !race.isStarting())
				return;

			// そのレースに参加中
			if (race.isJoined(player)){
				for (String s : plugin.getConfigs().disableCommands){
					if (s.trim().equalsIgnoreCase(cmd)){
						// コマンド実行キャンセル
						event.setCancelled(true);
						Actions.message(null, player, msgPrefix+"このコマンドはレース中に使えません！");
						return;
					}
				}
			}
		}
	}
}
