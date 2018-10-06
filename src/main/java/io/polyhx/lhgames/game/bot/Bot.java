package io.polyhx.lhgames.game.bot;

import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.action.IAction;
import io.polyhx.lhgames.game.point.Point;
import io.polyhx.lhgames.game.tile.Tile;

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
    Point pointVerifier=Point.UP;
    Point pointDeplacer=Point.RIGHT;
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        
        Evenement event= Evenement.DEPLACER;
        IAction action=null;
        map.getTile(player.getX()+1, player.getY());
        do{
            Tile tile = map.getTileAboveOf(pointVerifier);
                if(tile.isResource())
                {
                   //event = COLLECTER_RESSOURCE;
                }
                else if(tile.isHouse())
                {
                    //event = VOLER;
                }
                else if(tile.isWall())
                {
                    // event = ? casse les arbres lol
                }
                else if (tile.isPlayer())
                {
                    // event = le tuer?
                }
            if(pointVerifier==Point.UP){
                pointVerifier=Point.RIGHT;
            }
            if(pointVerifier==Point.RIGHT){
                pointVerifier=Point.DOWN;
            }
            if(pointVerifier==Point.DOWN){
                pointVerifier=Point.LEFT;
            }
            if(pointVerifier==Point.LEFT){
                pointVerifier=Point.UP;
            }
        }while(pointVerifier!=Point.UP);
        
        switch(event){
            case DEPLACER:
                if(pointDeplacer==Point.RIGHT){
                    pointDeplacer = Point.LEFT;
                }
                else{
                    pointDeplacer = Point.RIGHT;
                }
            action = createMoveAction(pointDeplacer);
            break;
            case RAMASSER:
            action = createCollectAction(pointDeplacer);
            break;
        }
        ancienneAction=action;
        return action;
    }
}
