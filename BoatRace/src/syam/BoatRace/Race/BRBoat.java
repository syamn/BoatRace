package syam.BoatRace.Race;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import syam.BoatRace.BoatRace;

public class BRBoat{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	// ボートデータ
	private static double maxSpeed = 0.4D;
	private Boat boat;
	private boolean flight;
	private Location lastLocation;

	public BRBoat(Boat boat){
		this.boat = boat;
		this.lastLocation = boat.getLocation().clone();
	}

	/**
	 * 飛行中かどうかのフラグを変更する
	 * @param flight
	 */
	public void changeFlight(final boolean flight){
		this.flight = flight;
	}
	public void changeFlight(){
		changeFlight(!this.flight);
	}

	/**
	 * エンティティIDを返す
	 * @return ボートのエンティティID
	 */
	public int getEID(){
		return this.boat.getEntityId();
	}

	/**
	 * ボートを消滅させる
	 */
	public void remove(){
		this.boat.remove();
	}

	/**
	 * ボートに乗っているエンティティを返す
	 * @return 乗っていなければnull?
	 */
	public Entity getPassenger(){
		return this.boat.getPassenger();
	}

	/**
	 * ボートの座標を返す
	 * @return
	 */
	public Location getLocation(){
		return this.boat.getLocation();
	}

	/**
	 * ボートが地上(何らかのブロックの上)にあるかどうかを返す
	 * @return 地上ならtrue
	 */
	public boolean isOnGround(){
		Block underBlock = this.boat.getWorld().getBlockAt(
				this.boat.getLocation().getBlockX(),
				this.boat.getLocation().getBlockY() - 1,
				this.boat.getLocation().getBlockZ()
				);

		return (underBlock.getType() != Material.AIR) &&
				(underBlock.getType() != Material.STATIONARY_WATER) &&
				(underBlock.getType() != Material.WATER);
	}
	/**
	 * ボートが動いている状態かどうかを返す
	 * @return 動いていればtrue
	 */
	public boolean isMoving(){
		Vector vec = this.boat.getVelocity();
		return (vec.getX() != 0.0D || vec.getY() != 0.0D || vec.getZ() != 0.0D);
	}

	/**
	 * ボートに新たなベクトルを設定する
	 * @param newVec 設定するベクトル
	 */
	public void setVelocity(Vector newVec){
		this.boat.setVelocity(newVec);
	}
	public void setVelocity(double x, double y, double z){
		setVelocity(new Vector(forceRange(x), y, forceRange(z)));
	}
	public void setVelocityX(double x){
		setVelocity(new Vector(
				forceRange(x),
				this.boat.getVelocity().getY(),
				this.boat.getVelocity().getZ()
				));
	}
	public void setVelocityY(double y){
		setVelocity(new Vector(
				this.boat.getVelocity().getX(),
				y,
				this.boat.getVelocity().getZ()
				));
	}
	public void setVelocityZ(double z){
		setVelocity(new Vector(
				this.boat.getVelocity().getX(),
				this.boat.getVelocity().getY(),
				forceRange(z)
				));
	}

	/**
	 * ボートの速度上限を超えないようにベクトルを制限する
	 * @param d 設定前のベクトル
	 * @return 制限済みのベクトル設定用Double
	 */
	public static double forceRange(double d){
		if (Math.abs(d) >= maxSpeed){
			if (d < 0.0D){
				d = -maxSpeed;
			}else{
				d = maxSpeed;
			}
		}
		return d;
	}

	public Vector getVelocity(){
		return this.boat.getVelocity().clone();
	}
	public double getVelocityX() {
		return this.boat.getVelocity().getX();
	}
	public double getVelocityY() {
		return this.boat.getVelocity().getY();
	}
	public double getVelocityZ() {
		return this.boat.getVelocity().getZ();
	}

	public boolean isEmpty() {
		return this.boat.isEmpty();
	}

	/**
	 * ボートを加速させる
	 * @param factor
	 */
	public void boostXZ(int factor) {
		// 今のxzベクトル取得
		double curX = getVelocityX();
		double curZ = getVelocityZ();

		double newX = curX * factor;
		if (Math.abs(newX) > 0.4D) {
			if (newX < 0.0D)
				newX = -0.4D;
			else {
				newX = 0.4D;
			}
			double newZ = 0.0D;

			if (curZ != 0.0D) {
				newZ = 0.4D / Math.abs(curX / curZ);
				if (curZ < 0.0D) {
					newZ *= -1.0D;
				}
			}
			setVelocity(newX, getVelocityY(), newZ);
			return;
		}

		double newZ = curZ * factor;
		if (Math.abs(newZ) > 0.4D) {
			if (newZ < 0.0D){
				newZ = -0.4D;
			}else{
				newZ = 0.4D;
			}
			newX = 0.0D;
			if (curX != 0.0D) {
				newX = 0.4D / (curZ / curX);
				if (curX < 0.0D) {
					newX *= -1.0D;
				}
			}
			setVelocity(newX, getVelocityY(), newZ);
			return;
		}

		setVelocity(newX, getVelocityY(), newZ);
	}
}
