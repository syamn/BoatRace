package syam.BoatRace.Command;

import syam.BoatRace.Race.Race;
import syam.BoatRace.Util.Actions;

public class ReadyCommand extends BaseCommand {
	public ReadyCommand(){
		bePlayer = false;
		name = "ready";
		argLength = 1;
		usage = "<game> <- ready game";
	}

	@Override
	public boolean execute() {
		// flagadmin ready - ゲームを開始準備中にする
		if (args.size() == 0){
			Actions.message(sender, null, "&cゲーム名を入力してください！ /fg ready (name)");
			return true;
		}

		Race race = plugin.getGame(args.get(0));
		if (race == null){
			Actions.message(sender, null, "&cレースゲーム'"+args.get(0)+"'が見つかりません");
			return true;
		}

		// ready
		race.ready(sender);
		return true;
	}

	@Override
	public boolean permission() {
		return sender.hasPermission("flag.admin.ready");
	}
}
