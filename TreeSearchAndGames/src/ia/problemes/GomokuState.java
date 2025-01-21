package ia.problemes;

import java.util.Arrays;
import java.util.ArrayList;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.common.ArgParse;

/**
 * Représente un état du jeux Gomoku
 */

public class GomokuState extends AbstractMnkGameState {

    public GomokuState() {
        super(15, 15, 5);
    }

    public GomokuState cloneState() {
        GomokuState new_s = new GomokuState();
        new_s.board = this.board.clone();
        new_s.player_to_move = this.player_to_move;
        new_s.game_value = this.game_value;
        if (this.last_action != null)
            new_s.last_action = this.last_action.clone();
        for (Pair p : this.winning_move)
            new_s.winning_move.add(p.clone());

        return new_s;
    }

    /**
     * {@inheritDoc}
     *
     * @return la valeur du jeu
     **/
    protected double evaluationFunction() {
        int pos_x = this.possibleLines(X);
        int pos_o = this.possibleLines(O);

        double value = pos_x - pos_o;

        // System.out.println("Possibilities: X = "+pos_x+", O = "+pos_o+
        //". Value = "+value);
        return value;
    }

    // compte le nombre de lignes possibles pour player
    private int possibleLines(int player) {

        return this.possibleVerticalLines(player) +
                this.possibleHorizontalLines(player) +
                this.possibleDiagonalLines(player);

    }


    // compte le nombre de lignes verticales possibles pour player
    private int possibleVerticalLines(int player) {

        // une ligne possible pour player est un ligne :
        // * vide
        // * avec une seule pièce de player
        // * avec deux pièces de player
        // Si la ligne contient une pièce de l'adversaire
        // elle n'est pas possible

        int res = 0;
        for (int c = 0; c < this.cols; c++)
            for (int r = 0; r <= this.rows - this.streak; r++) {
                int counter = 0;
                for (int k = 0; k < this.streak; k++) {
                    if (this.getValueAt(r + k, c) == this.otherPlayer(player)) {
                        counter = 0;
                        break; // plus besoin de continuer
                    } else if (this.getValueAt(r + k, c) == player)
                        counter++;
                    else // vide
                        counter++;
                }
                if (counter > 0)
                    res++; // une ligne possible de plus
            }

        //System.out.println("Possible vertical for "+ (char) player+ " : "+res);
        return res;
    }

    // compte le nombre de lignes horizontales possibles pour player
    private int possibleHorizontalLines(int player) {

        // même raisonnement que pour les lignes verticales

        int res = 0;
        for (int r = 0; r < this.rows; r++)
            for (int c = 0; c <= this.cols - this.streak; c++) {
                int counter = 0;
                for (int k = 0; k < this.streak; k++) {
                    if (this.getValueAt(r, c + k) == this.otherPlayer(player)) {
                        counter = 0;
                        break; // plus besoin de continuer
                    } else if (this.getValueAt(r, c + k) == player)
                        counter++;
                    else // vide
                        counter++;
                }
                if (counter > 0)
                    res++; // une ligne possible de plus
            }
        //System.out.println("Possible horizontal for "+ (char) player+ " : "+res);
        return res;
    }

    // compte le nombre de lignes diagonales possibles pour player
    private int possibleDiagonalLines(int player) {

        // même raisonnement que pour les lignes verticales

        int res = 0;

        // 45 deg
        for (int c = 0; c <= this.cols - this.streak; c++)
            for (int r = 0; r <= this.rows - this.streak; r++) {
                int counter = 0;
                for (int k = 0; k < this.streak; k++) {
                    if (this.getValueAt(r + k, c + k) == this.otherPlayer(player)) {
                        counter = 0;
                        break; // plus besoin de continuer
                    } else if (this.getValueAt(r + k, c + k) == player)
                        counter++;
                    else // vide
                        counter++;
                }
                if (counter > 0)
                    res++; // une ligne possible de plus
            }

        // -45 deg
        for (int c = 0; c <= this.cols - this.streak; c++)
            for (int r = this.streak - 1; r < this.rows; r++) {
                int counter = 0;
                for (int k = 0; k < this.streak; k++) {
                    if (this.getValueAt(r - k, c + k) == this.otherPlayer(player)) {
                        counter = 0;
                        break; // plus besoin de continuer
                    } else if (this.getValueAt(r - k, c + k) == player)
                        counter++;
                    else // vide
                        counter++;
                }
                if (counter > 0)
                    res++; // une ligne possible de plus
            }
        //System.out.println("Possible diagonal for "+ (char) player+ " : "+res);
        return res;
    }

}
