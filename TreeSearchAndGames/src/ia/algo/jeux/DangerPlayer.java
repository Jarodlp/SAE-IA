package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

public class DangerPlayer extends Player {

    /**
     * Represente un joueur
     *
     * @param g            l'instance du jeux
     * @param player_one   si joueur 1
     */
    public DangerPlayer(Game g, boolean player_one) {
        super(g, player_one);
    }
    @Override
    public Action getMove(GameState state) {
        double meilleurDanger = this.player == PLAYER1 ? GameState.P2_WIN : GameState.P1_WIN;
        Action meilleurCoup = null;

        for (Action coup : game.getActions(state)) {
            GameState stateSuivant = (GameState) game.doAction(state, coup);

            if (stateSuivant.isFinalState()){
                return coup;
            }

            for (Action coupAdverse : game.getActions(stateSuivant)) {
                GameState stateAdverse = (GameState) game.doAction(stateSuivant, coupAdverse);
                if (game.endOfGame(stateAdverse) && stateAdverse.getGameValue() != 0) {
                    return coupAdverse;
                }
            }

            double danger = stateSuivant.getGameValue();
            if (this.player == PLAYER1 && danger > meilleurDanger || this.player == PLAYER2 && danger < meilleurDanger) {
                meilleurDanger = danger;
                meilleurCoup = coup;
            }
            incStateCounter();
        }

        return meilleurCoup;
    }
}
