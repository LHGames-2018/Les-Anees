package io.polyhx.lhgames.game.bot;

import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.action.IAction;
import io.polyhx.lhgames.game.action.StealAction;
import io.polyhx.lhgames.game.point.Point;
import io.polyhx.lhgames.game.tile.Tile;

import java.util.List;
import java.util.Random;

public class Bot extends BaseBot {
    enum Evenement{
            VOLER,
            DEPLACER,
            ATTAQUER,
            RAMASSER,
            ACHETER,
            DETRUIRE
        }
    IAction ancienneAction;
    Point pointVerifier=Point.UP;
    Point pointDeplacer=Point.RIGHT;
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {

        return createMeleeAttackAction(Point.UP);
    }

    public Evenement regarderDevant(Player player, Map map){
        Point pointCase=new Point(player.getX()+pointVerifier.getX(),player.getY()+pointVerifier.getY());
        Tile tile = map.getTile(pointCase);
        if(tile.isResource())
        {
            if(player.getTotalResource() < player.getResourceCapacity()){
                return Evenement.RAMASSER;
            }
        }
        else if(tile.isHouse())
        {
            if(tile.getPosition()!=player.getHousePosition()) {
                if(!(ancienneAction instanceof StealAction)){
                    return Evenement.VOLER;
                }
            }
        }
        else if(tile.isWall())
        {
            return Evenement.DETRUIRE;
        }
        else if (tile.isPlayer())
        {
            return Evenement.ATTAQUER;
        }
        return Evenement.DEPLACER;
    }


    Evenement reviens(Player player)
    {
        Point positionActuelle = player.getPosition();
        Point positionMaison = player.getHousePosition();

        int decalageX = positionActuelle.getX() - positionMaison.getX();
        int decalageY = positionActuelle.getY() - positionMaison.getY();

        Random deplacementAleatoire= new Random();

        if(deplacementAleatoire.nextInt()%2 == 0)
        {
            System.out.println("verifierX");
            System.out.println(decalageX);
            if(decalageX > 0)
            {
                pointDeplacer= Point.LEFT;
                return Evenement.DEPLACER;
            }
            else if(decalageX < 0)
            {
                pointDeplacer = Point.RIGHT;
                return Evenement.DEPLACER;
            }
            else{
                pointDeplacer = Point.RIGHT;

                return Evenement.DEPLACER;
            }
        }
        else
        {
            System.out.println("verifierY");
            System.out.println(decalageY);
            if(decalageY > 0)
            {
                pointDeplacer = Point.UP;
                return Evenement.DEPLACER;
            }
            else if(decalageY < 0)
            {
                pointDeplacer = Point.DOWN;
                return Evenement.DEPLACER;
            }
            else{
                pointDeplacer = Point.DOWN;
                return Evenement.DEPLACER;
            }
        }
    }
}