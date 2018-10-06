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
        Evenement event= Evenement.DEPLACER;
        IAction action=null;
        map.getTile(player.getX()+1, player.getY());
        if(player.getTotalResource() >= player.getResourceCapacity()){
            System.out.println("action faite");
            event=reviens(player);
            pointVerifier=pointDeplacer;
            event=regarderDevant(player,map);
            System.out.println(event);
        }
        else{
            System.out.println("action faite2");
        do{

            event=regarderDevant(player, map);
            if(event!=Evenement.DEPLACER){
                break;
            }
            if(pointVerifier==Point.UP){
                pointVerifier=Point.RIGHT;
            }
            else if(pointVerifier==Point.RIGHT){
                pointVerifier=Point.DOWN;
            }
            else if(pointVerifier==Point.DOWN){
                pointVerifier=Point.LEFT;
            }
            else if(pointVerifier==Point.LEFT){
                pointVerifier=Point.UP;
            }
        }while(pointVerifier!=Point.UP);
        Random rdn=new Random();
        int aleatoire=rdn.nextInt()%4;
            switch(aleatoire){
                case 0:
                    pointDeplacer=Point.RIGHT;
                    break;
                case 1:
                    pointDeplacer=Point.DOWN;
                    break;
                case 2:
                    pointDeplacer=Point.LEFT;
                    break;
                case 3:
                    pointDeplacer=Point.UP;
                    break;
            }
        }

       switch (event) {
            case VOLER:
                action = createStealAction(pointVerifier);
                break;
            case DEPLACER:
                action = createMoveAction(pointVerifier);
                break;
            case ATTAQUER:
                action = createMeleeAttackAction(pointVerifier);
                break;
            case RAMASSER:
                action = createCollectAction(pointVerifier);
                break;
            case DETRUIRE:
                action = createMeleeAttackAction(pointVerifier);
                break;
            case ACHETER:
                //action = createPurchaseAction();
                break;
            default:
                break;
        }
        ancienneAction=action;
        return action;
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
        }
        else
        {
            if(decalageY > 0)
            {
                pointDeplacer = Point.DOWN;
                return Evenement.DEPLACER;
            }
            else if(decalageY < 0)
            {
                pointDeplacer = Point.UP;
                return Evenement.DEPLACER;
            }
        }
        return Evenement.DEPLACER;
    }
}