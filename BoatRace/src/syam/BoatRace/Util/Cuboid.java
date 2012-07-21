package syam.BoatRace.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * 立体領域を表すクラス
 * @author syam
 */
public class Cuboid {
	/**
	 * コンストラクタ
	 * @param point1 Pos1
	 * @param point2 Pos2
	 */
	public Cuboid(Location point1, Location point2) {
		// 対角点設定
		this.pos1 = point1;
		this.pos2 = point2;

		// 各頂点設定
		this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
		this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
		this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
		this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
		this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
		this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());

		// ワールド設定
		this.world = point1.getWorld();
	}

	private Location pos1, pos2;
	private int xMin, xMax, yMin, yMax, zMin, zMax;
	private World world;

	/**
	 * 指定した座標が立体領域内かチェック
	 * @param loc チェックする座標
	 * @return 領域内ならtrue 違えばfalse
	 */
	public boolean isIn(Location loc) {
	    if (loc.getWorld() != this.world) return false;
	    if (loc.getBlockX() < xMin) return false;
	    if (loc.getBlockX() > xMax) return false;
	    if (loc.getBlockY() < yMin) return false;
	    if (loc.getBlockY() > yMax) return false;
	    if (loc.getBlockZ() < zMin) return false;
	    if (loc.getBlockZ() > zMax) return false;
	    return true;
	}
	/**
	 * X軸の幅を取得(int)
	 * @return X軸の幅
	 */
	public int getXWidth() {
	    return xMax - xMin;
	}
	/**
	 * Y軸の幅を取得(int)
	 * @return Y座標の幅
	 */
	public int getZWidth() {
	    return zMax - zMin;
	}
	/**
	 * Z幅を取得(int)
	 * @return Z座標の幅
	 */
	public int getHeight() {
		return yMax - yMin;
	}

	/**
	 * 指定領域全体のブロック数を取得
	 * @return X幅*Y幅*高さ (int)
	 */
	public int getArea() {
		return getHeight() * getXWidth() * getZWidth();
	}

	/**
	 * 指定領域内のブロックをリストに入れて返す
	 * @return 領域内のブロックリスト
	 */
	public List<Block> getBlocks() {
		List<Block> blocks = new ArrayList<Block>();

		// 全軸を最小値から最大値まで回す
		for (int x = xMin; x <= xMax; x++){
			for (int y = yMin; y <= yMax; y++){
				for (int z = zMin; y <= zMax; z++){
					blocks.add(world.getBlockAt(x, y, z)); // リストにブロックを追加
				}
			}
		}

		return blocks;
	}

	/* getter / setter */
	/**
	 * ワールドを返す
	 * @return
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * 対角点1を返す
	 * @return pos1
	 */
	public Location getPos1(){
		return pos1;
	}
	/**
	 * 対角点2を返す
	 * @return pos2
	 */
	public Location getPos2(){
		return pos2;
	}
}