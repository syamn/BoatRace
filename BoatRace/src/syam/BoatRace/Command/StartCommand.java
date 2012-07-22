package syam.BoatRace.Command;

import syam.BoatRace.Race.Race;
import syam.BoatRace.Util.Actions;

public class StartCommand extends BaseCommand{
	public StartCommand(){
		bePlayer = false;
		name = "start";
		argLength = 1;
		usage = "<game> <- start the game";
	}

	@Override
	public boolean execute() {
		// flagadmin ready - ゲームを開始準備中にする
		if (args.size() == 0){
			Actions.message(sender, null, "&cゲーム名を入力してください！ /fg start (name)");
			return true;
		}

		Race race = plugin.getGame(args.get(0));
		if (race == null){
			Actions.message(sender, null, "&cレースゲーム'"+args.get(0)+"'が見つかりません");
			return true;
		}

		if (!race.isReady()){
			Actions.message(sender, null, "&cレースゲーム'"+args.get(0)+"'は参加受付状態ではありません");
			return true;
		}

		// start
		//game.start(sender);
		race.start_timer(sender);
		return true;
	}

	@Override
	public boolean permission() {
		return sender.hasPermission("flag.admin.start");
	}

}
