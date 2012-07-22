package syam.BoatRace.Util;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.CuboidRegionSelector;

import syam.BoatRace.BoatRace;

/**
 * WorldEditの選択領域を取得するためのWorldEditハンドラ
 * @author syam
 */
public class WorldEditHandler{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	/**
	 * 指定したプレイヤーが選択中のWorldEdit領域を取得する
	 * @param bPlayer WorldEditで領域を指定しているプレイヤー
	 * @return 選択された領域の両端のブロック配列[2] エラーならnull
	 */
	@SuppressWarnings("deprecation")
	public static Block[] getWorldEditRegion(final Player bPlayer){
		// WorldEditプラグイン取得
		Plugin plugin = BoatRace.getInstance().getServer().getPluginManager().getPlugin("WorldEdit");

		// プラグインが見つからない
		if (plugin == null){
			Actions.message(null, bPlayer, msgPrefix+ "&cWorldEdit is not loaded!");
			return null;
		}

		WorldEditPlugin we = (WorldEditPlugin) plugin;
		LocalPlayer player = new BukkitPlayer(we, we.getServerInterface(), (Player) bPlayer);
		LocalSession session = we.getWorldEdit().getSession(player);

		// セレクタが立方体セレクタか判定
		if (!(session.getRegionSelector() instanceof CuboidRegionSelector)){
			Actions.message(null, bPlayer, msgPrefix+ "&cFlagGame supports only cuboid regions!");
			return null;
		}

		CuboidRegionSelector selector = (CuboidRegionSelector) session.getRegionSelector();

		try{
			CuboidRegion region = selector.getRegion();

			// 選択範囲の端と端のブロックを格納する配列
			Block[] corners = new Block[2];

			Vector v1 = region.getPos1();
			Vector v2 = region.getPos2();

			corners[0] = bPlayer.getWorld().getBlockAt(v1.getBlockX(), v1.getBlockY(), v1.getBlockZ());
			corners[1] = bPlayer.getWorld().getBlockAt(v2.getBlockX(), v2.getBlockY(), v2.getBlockZ());

			// 角のブロック配列[2]を返す
			return corners;
		}catch (IncompleteRegionException ex){
			// 正しく領域が選択されていない例外
			Actions.message(null, bPlayer, msgPrefix+ "&cWorldEdit region is not fully selected!");
		}
		catch (Exception ex){
			// その他一般例外
			log.warning(logPrefix+"Error while retreiving WorldEdit region: "+ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}
