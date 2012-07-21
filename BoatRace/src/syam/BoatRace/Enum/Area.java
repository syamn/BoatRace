package syam.BoatRace.Enum;

/**
 * ゲーム内で使用するエリアデータ
 * @author syam
 */
public enum Area {
	START ("スタート"), // スタート地点
	GOAL ("ゴール"), // ゴール地点
	CHECKPOINT ("チェックポイント"), // チェックポイント
	;

	private String areaName;

	Area(String areaName){
		this.areaName = areaName;
	}

	/**
	 * エリア名を返す
	 * @return
	 */
	public String getAreaName(){
		return areaName;
	}


}
