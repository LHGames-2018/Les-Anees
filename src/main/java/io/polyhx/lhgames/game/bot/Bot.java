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
            DEPLACER_GAUCHE,
            DEPLACER_DROITE,
            DEPLACER_HAUT,
            DEPLACER_BAS,
            ATTAQUER,
            RAMASSER,
            ACHETER
        }
    IAction ancienneAction;
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        
        Evenement event= Evenement.DEPLACER_DROITE;
        IAction action=null;


        switch(event){
            case DEPLACER_DROITE:
            return createMoveAction(Point.RIGHT);
            case RAMASSER:
            action=createMoveAction(Point.RIGHT);
            break;
        }
        ancienneAction=action;
        return createMoveAction(Point.RIGHT);
    }
}
