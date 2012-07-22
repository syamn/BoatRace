package syam.BoatRace.Enum;

public enum Configables {
	START("スタート地点", ConfigType.MANAGER), // スタート地点
	GOAL("ゴールエリア",ConfigType.AREA), // ゴールエリア
	CHECKPOINT("チェックポイント",ConfigType.AREA), // チェックポイントエリア
	;

	private String configName;
	private ConfigType configType;

	Configables(String configName, ConfigType ctype){
		this.configName = configName;
		this.configType = ctype;
	}

	/**
	 * 設定名を返す
	 * @return
	 */
	public String getConfigName(){
		return configName;
	}

	/**
	 * 設定種類を返す
	 * @return
	 */
	public ConfigType getConfigType(){
		return configType;
	}
}
