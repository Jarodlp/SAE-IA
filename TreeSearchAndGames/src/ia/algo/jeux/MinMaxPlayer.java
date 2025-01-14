package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

public class MinMaxPlayer extends Player {
    /**
     * profondeur max pour minmax
     */
    private final int profondeurMax;

    /**
     * Represente un joueur
     *
     * @param g            l'instance du jeux
     * @param player_one   si joueur 1
     * @param profondeur   profondeur max pour minmax (sans limite par défaut)
     */
    public MinMaxPlayer(Game g, boolean player_one, int profondeur) {
        super(g, player_one);
        this.profondeurMax = profondeur;
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair action;
        // Joueur 1 = MAX, Joueur 2 = MIN
        if (this.player == PLAYER1){
             action = MaxValeur(state, 0);
        } else {
             action = MinValeur(state, 0);
        }
        return action.getAction();
    }

    private ActionValuePair MaxValeur(GameState state, int profondeur) {
        if (game.endOfGame(state) || profondeur == this.profondeurMax) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double Vmax = GameState.P2_WIN;
        Action coupMax = null;

        for ( Action coup : game.getActions(state)) {
            GameState stateSuivant = (GameState) game.doAction(state, coup);
            ActionValuePair action = MinValeur(stateSuivant, profondeur + 1);
            if (action.getValue() > Vmax) {
                Vmax = action.getValue();
                coupMax = coup;
            }
            incStateCounter();
        }

        return new ActionValuePair(coupMax, Vmax);
    }

    private ActionValuePair MinValeur(GameState state, int profondeur) {

        if (game.endOfGame(state) || profondeur == this.profondeurMax) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double Vmin = GameState.P1_WIN;
        Action coupMin = null;

        for ( Action coup : game.getActions(state)) {
            GameState stateSuivant = (GameState) game.doAction(state, coup);
            ActionValuePair action = MaxValeur(stateSuivant, profondeur + 1);
            if (action.getValue() < Vmin) {
                Vmin = action.getValue();
                coupMin = coup;
            }
            incStateCounter();
        }

        return new ActionValuePair(coupMin, Vmin);
    }
}
