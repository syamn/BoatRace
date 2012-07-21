package syam.BoatRace.Race;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Location;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Util.Cuboid;

public class Race{
	// Logger
	public static final Logger log = BoatRace.log;
	private static final String logPrefix = BoatRace.logPrefix;
	private static final String msgPrefix = BoatRace.msgPrefix;

	private final BoatRace plugin;

	/* ***** レースデータ ***** */
	private String GameID; // 一意なゲームID ログ用
	private String fileName; // ゲームデータのファイル名
	private String gameName; // ゲーム名
	private int teamPlayerLimit = 8; // 各チームの最大プレイヤー数
	private int gameTimeInSeconds = 600; // 1ゲームの制限時間
	private int remainSec = gameTimeInSeconds; // 1ゲームの制限時間
	private int timerThreadID = -1; // タイマータスクのID
	private int starttimerInSec = 10;
	private int starttimerThreadID = -1;
	private boolean ready = false; // 待機状態フラグ
	private boolean started = false; // 開始状態フラグ

	private int award = 1000; // 賞金
	private int entryFee = 100; // エントリー料

	// 参加プレイヤー
	private HashMap<String, BRBoat> players = new HashMap<String, BRBoat>();
	// 開始ポジション :複数必要
	private Set<Location> startPos = new HashSet<Location>();
	// ゴールゾーン :1ゾーン
	private Cuboid goalZone = null;
	// チェックポイント :複数ゾーン可
	private Set<Cuboid> checkpoints = new HashSet<Cuboid>();

	/**
	 * コンストラクタ
	 * @param plugin
	 * @param name
	 */
	public Race(final BoatRace plugin, final String name){
		this.plugin = plugin;

		// ゲームデータ設定
		this.gameName = name;

		// ファイル名設定
		this.fileName = this.gameName + ".yml";

		// ゲームをメインクラスに登録
		plugin.games.put(this.gameName, this);
	}

	public void init(){
		// プレイヤーリスト初期化
		players.clear();

		// タイマー関係初期化
		//cancelTimerTask();
		timerThreadID = -1;
		remainSec = gameTimeInSeconds;

		// フラグ初期化
		started = false;
		ready = false;
	}

}

