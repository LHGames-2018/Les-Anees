package io.polyhx.lhgames.game.bot;

import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.action.IAction;
import io.polyhx.lhgames.game.point.Point;

import java.util.List;

public class Bot extends BaseBot {
    enum Evenement{
            VOLER,
            DEPLACER,
            ATTAQUER,
            RAMASSER,
            ACHETER
        }
    IAction ancienneAction;
    Point point=Point.RIGHT;
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        
        Evenement event= Evenement.DEPLACER;
        IAction action=null;
        map.getTile(player.getX()+1, player.getY());
        
        switch(event){
            case DEPLACER:
                if(point==Point.RIGHT){
                    point = Point.LEFT;
                }
                else{
                    point = Point.RIGHT;
                }
            action = createMoveAction(point);
            case RAMASSER:
            action = createCollectAction(point);
            break;
        }
        ancienneAction=action;
        return action;
    }
}
