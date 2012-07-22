package syam.BoatRace.Enum;

public enum Configables {
	START(ConfigType.MANAGER), // スタート地点
	GOAL(ConfigType.AREA), // ゴールエリア
	CHECKPOINT(ConfigType.AREA), // チェックポイントエリア
	;

	private ConfigType configType;

	Configables(ConfigType ctype){
		this.configType = ctype;
	}

	/**
	 * 設定種類を返す
	 * @return
	 */
	public ConfigType getConfigType(){
		return configType;
	}
}
