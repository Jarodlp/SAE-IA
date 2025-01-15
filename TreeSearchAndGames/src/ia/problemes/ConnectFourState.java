package ia.problemes;

import java.util.Random;
import java.util.Arrays;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.common.Misc;
import ia.framework.jeux.GameState;

public class ConnectFourState extends AbstractMnkGameState {
    
	public ConnectFourState() {
        super(6, 7, 4);
    }

    public ConnectFourState cloneState() {
		ConnectFourState new_s = new ConnectFourState();
        new_s.board = this.board.clone();
        new_s.player_to_move = this.player_to_move;
        new_s.game_value = this.game_value;
        if( this.last_action != null )
            new_s.last_action = this.last_action.clone();
        for (Pair p: this.winning_move)
            new_s.winning_move.add(p.clone());
        
        return new_s;
    }
 


    /**
     * 
     * {@inheritDoc}
     * <p>Surchargé car les actions ne sont pas standards<p>
     * @param idx indice de la colonne
     * @return vrai si encore de l'espace dans cette colonne
     */ 
    public boolean isLegal(int idx) {
        return this.getFreeRow(idx) != -1;
    }


    /**
     * {@inheritDoc}
     * <p>Pour ce jeu on choisi la colonne, et la pièce tombe.</p>
     * 
     * @param col l'index de la case
     */
    
	public void play(int col) {
                
        // récupérer la première ligne vide a cette colonne 
        int row = this.getFreeRow(col);
        
        // poser la pièce
        this.board[getPositionAt(row,col)] = (char) player_to_move;

        // enregistrer le coup (pour l'affichage) 
        this.last_action = new Pair (row, col);
        
        // Mettre a jour la valeur du jeu 
        this.updateGameValue();
        
        // change de joueur
        this.player_to_move = ( this.player_to_move == X ? O : X); 
    }

    /**
     * {@inheritDoc} 
     * Un fonction d'évaluation trop bête 
     *
     * 
     * @return la valeur du jeux pour le joueur courant 
     **/
    @Override
    protected double evaluationFunction() {
        int posX = this.possibleLines(X); // Nombre de lignes possibles pour X
        int posO = this.possibleLines(O); // Nombre de lignes possibles pour O


        double value = posX - posO; // Différence entre les possibilités pour X et O
        return value;
    }

    // Compte le nombre de lignes possibles pour un joueur donné
    private int possibleLines(int player) {


        return this.possibleVerticalLines(player) +
                this.possibleHorizontalLines(player) +
                this.possibleDiagonalLines(player);
    }

    // Compte le nombre de lignes verticales possibles pour un joueur donné
    private int possibleVerticalLines(int player) {
        int res = 0;

        for (int col = 0; col < this.cols; col++) {
            // Vérifier si la colonne est jouable
            if (!this.isLegal(col)) {
                continue;
            }

            for (int row = 0; row <= this.rows - this.streak; row++) {
                int counter = 0;

                for (int k = 0; k < this.streak; k++) {
                    char cell = this.getValueAt(row + k, col);

                    if (cell == this.otherPlayer(player)) {
                        counter = 0;
                        break; // Ligne bloquée
                    } else if (cell == player || cell == EMPTY) {
                        counter++;
                    }
                }

                if (counter > 0) res++; // Ligne verticale possible trouvée
            }
        }

        return res;
    }

    // Compte le nombre de lignes horizontales possibles pour un joueur donné
    private int possibleHorizontalLines(int player) {
        int res = 0;

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col <= this.cols - this.streak; col++) {
                // Vérifier si au moins une colonne est jouable dans cette séquence
                boolean legalFound = false;
                for (int k = 0; k < this.streak; k++) {
                    if (this.isLegal(col + k)) {
                        legalFound = true;
                        break;
                    }
                }

                if (!legalFound) continue;

                int counter = 0;

                for (int k = 0; k < this.streak; k++) {
                    char cell = this.getValueAt(row, col + k);

                    if (cell == this.otherPlayer(player)) {
                        counter = 0;
                        break; // Ligne bloquée
                    } else if (cell == player || cell == EMPTY) {
                        counter++;
                    }
                }

                if (counter > 0) res++; // Ligne horizontale possible trouvée
            }
        }

        return res;
    }

    // Compte le nombre de lignes diagonales possibles pour un joueur donné
    private int possibleDiagonalLines(int player) {
        int res = 0;

        // Diagonales montantes (\)
        for (int row = 0; row <= this.rows - this.streak; row++) {
            for (int col = 0; col <= this.cols - this.streak; col++) {
                // Vérifier si au moins une colonne est jouable dans cette séquence
                boolean legalFound = false;
                for (int k = 0; k < this.streak; k++) {
                    if (this.isLegal(col + k)) {
                        legalFound = true;
                        break;
                    }
                }

                if (!legalFound) continue;

                int counter = 0;

                for (int k = 0; k < this.streak; k++) {
                    char cell = this.getValueAt(row + k, col + k);

                    if (cell == this.otherPlayer(player)) {
                        counter = 0;
                        break; // Ligne bloquée
                    } else if (cell == player || cell == EMPTY) {
                        counter++;
                    }
                }

                if (counter > 0) res++; // Ligne diagonale montante possible trouvée
            }
        }

        // Diagonales descendantes (/)
        for (int row = this.streak - 1; row < this.rows; row++) {
            for (int col = 0; col <= this.cols - this.streak; col++) {
                // Vérifier si au moins une colonne est jouable dans cette séquence
                boolean legalFound = false;
                for (int k = 0; k < this.streak; k++) {
                    if (this.isLegal(col + k)) {
                        legalFound = true;
                        break;
                    }
                }

                if (!legalFound) continue;

                int counter = 0;

                for (int k = 0; k < this.streak; k++) {
                    char cell = this.getValueAt(row - k, col + k);

                    if (cell == this.otherPlayer(player)) {
                        counter = 0;
                        break; // Ligne bloquée
                    } else if (cell == player || cell == EMPTY) {
                        counter++;
                    }
                }

                if (counter > 0) res++; // Ligne diagonale descendante possible trouvée
            }
        }

        return res;
    }


    // l'API privée 
        
	/**
	 * Retourne la premier case vide de la colonne col 
     * -1 si c'est full
     */
	private int getFreeRow(int col) {
        if (col>=0 && col <this.cols)
            for (int r=0; r<this.rows; r++)
                if(this.getValueAt(r, col) == EMPTY)
                    return r;
        return -1;
    }
}
