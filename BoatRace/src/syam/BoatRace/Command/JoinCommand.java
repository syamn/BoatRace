package syam.BoatRace.Command;

import syam.BoatRace.BoatRace;
import syam.BoatRace.Race.Race;
import syam.BoatRace.Util.Actions;

public class JoinCommand extends BaseCommand {
	public JoinCommand(){
		bePlayer = true;
		name = "join";
		argLength = 1;
		usage = "<game> <- join the game";
	}

	@Override
	public boolean execute() {
		Race race = plugin.getGame(args.get(0));
		// レース存在確認
		if (race == null){
			Actions.message(null, player, "&cレースゲーム'"+args.get(0)+"'が見つかりません");
			return true;
		}

		// レースの状態チェック
		if (race.isStarting()){
			Actions.message(null, player, "&cレースゲーム'"+args.get(0)+"'は既に始まっています！");
			return true;
		}
		if (!race.isReady()){
			Actions.message(null, player, "&cレースゲーム'"+args.get(0)+"'は現在参加受付中ではありません");
			return true;
		}

		// 参加状態チェック
		if (race.isJoined(player)){
			Actions.message(null, player, "&cあなたは既にこのレースにエントリーしています！");
			return true;
		}
		for (Race check : plugin.races.values()){
			if (check.isJoined(player)){
				Actions.message(null, player, "&cあなたは他のレースゲーム'"+check.getName()+"'にエントリーしています！");
				return true;
			}
		}

		// 人数チェック
		int limit = race.getPlayerLimit();
		if (limit > race.getStartPos().size())
			limit = race.getStartPos().size();
		if (race.getPlayersSet().size() >= limit){
			Actions.message(null, player, "&cこのレースゲームは参加可能な定員("+limit+"人)に達しています！");
			return true;
		}

		// join
		race.addPlayer(player);

		// アナウンス
		Actions.broadcastMessage(msgPrefix+"&aプレイヤー'&6"+player.getName()+"&a'がレース("+race.getName()+")にエントリーしました！");

		// 参加後に人数チェックして定員通知
		if (race.getPlayersSet().size() >= limit){
			Actions.message(null, player, "&aレースゲーム'"+race.getName()+"'が定員("+limit+"人)に達しました！");
		}

		return true;
	}

	@Override
	public boolean permission() {
		return sender.hasPermission("boat.user.join");
	}

}
