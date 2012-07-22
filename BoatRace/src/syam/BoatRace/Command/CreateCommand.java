package syam.BoatRace.Command;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Race.Race;
import syam.BoatRace.Race.RaceManager;
import syam.BoatRace.Util.Actions;

public class CreateCommand extends BaseCommand{
	public CreateCommand(){
		bePlayer = false;
		name = "create";
		argLength = 1;
		usage = "<name> <- create new race";
	}

	@Override
	public boolean execute() {
		if (args.size() == 0){
			Actions.message(sender, null, "&cゲーム名を入力してください！ /boat create (name)");
			return true;
		}
		Race race = plugin.getGame(args.get(0));
		if (race != null){
			Actions.message(sender, null, "&cそのゲーム名は既に存在します！");
			return true;
		}

		// 新規ゲーム登録
		race = new Race(plugin, args.get(0));
		RaceManager.setSelectedRace(player, race);

		Actions.message(sender, null, "&a新規ゲーム'"+race.getName()+"'を登録して選択しました！");
		return true;
	}

	@Override
	public boolean permission() {
		return sender.hasPermission("boat.admin");
	}
}
