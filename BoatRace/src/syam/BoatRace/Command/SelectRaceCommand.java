package syam.BoatRace.Command;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Race.Race;
import syam.BoatRace.Race.RaceManager;
import syam.BoatRace.Util.Actions;

public class SelectRaceCommand extends BaseCommand {
	public SelectRaceCommand(){
		bePlayer = true;
		name = "race";
		argLength = 1;
		usage = "[race] <- select exist race";
	}

	@Override
	public boolean execute() {
		// boat race (ゲーム名) - 選択
		Race race = plugin.getGame(args.get(0));
		if (race != null){
			RaceManager.setSelectedRace(player, race);
			Actions.message(null, player, "&aレースゲーム'"+race.getName()+"'を選択しました！");
		}else{
			Actions.message(null, player, "&cレースゲーム'"+args.get(0)+"'が見つかりません！");
		}

		return true;
	}

	@Override
	public boolean permission() {
		return sender.hasPermission("boat.admin.select");
	}


}
