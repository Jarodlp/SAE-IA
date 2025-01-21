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
        int scoreJoueur = weightedPossibleLines(X) + centerWeightedScore(X);
        int scoreAdversaire = weightedPossibleLines(O) + centerWeightedScore(O);

        // Calcul final : avantage du joueur - avantage de l'adversaire
        return scoreJoueur - scoreAdversaire;
    }

    private int centerWeightedScore(int player) {
        int score = 0;
        int centerColumn = this.cols / 2;

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                char cell = this.getValueAt(row, col);

                if (cell == player) {
                    // Ajouter une pondération pour les positions centrales
                    int distanceToCenter = Math.abs(centerColumn - col);
                    score += (this.cols - distanceToCenter); // Plus proche du centre = score plus élevé
                }
            }
        }

        return score;
    }


    private int weightedPossibleLines(int player) {
        int score = 0;
        score += countAlignments(player, 4) * 1000; // Ligne complète = victoire
        score += countAlignments(player, 3) * 50;  // Ligne de 3 = potentiellement décisif
        score += countAlignments(player, 2) * 5;       // Ligne de 2 = potentiel futur
        return score;
    }

    // Méthode principale pour compter les alignements et leur potentiel
    private int countAlignments(int player, int length) {
        return countHorizontalAlignments(player, length) +
                countVerticalAlignments(player, length) +
                countDiagonalAlignments(player, length);
    }

    // Compter les alignements horizontaux avec potentiel
    private int countHorizontalAlignments(int player, int length) {
        int count = 0;

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col <= this.cols - length; col++) {
                int playerCells = 0;
                int emptyCells = 0;

                for (int k = 0; k < length; k++) {
                    char cell = this.getValueAt(row, col + k);

                    if (cell == player) {
                        playerCells++;
                    } else if (cell == EMPTY) {
                        emptyCells++;
                    } else {
                        // Une cellule adverse bloque l'alignement
                        playerCells = 0;
                        emptyCells = 0;
                        break;
                    }
                }

                // Un alignement est valable s'il contient des jetons du joueur et des cellules vides
                if (playerCells > 0 && emptyCells > 0) {
                    count++;
                }
            }
        }

        return count;
    }

    // Compter les alignements verticaux avec potentiel
    private int countVerticalAlignments(int player, int length) {
        int count = 0;

        for (int col = 0; col < this.cols; col++) {
            for (int row = 0; row <= this.rows - length; row++) {
                int playerCells = 0;
                int emptyCells = 0;

                for (int k = 0; k < length; k++) {
                    char cell = this.getValueAt(row + k, col);

                    if (cell == player) {
                        playerCells++;
                    } else if (cell == EMPTY) {
                        emptyCells++;
                    } else {
                        // Une cellule adverse bloque l'alignement
                        playerCells = 0;
                        emptyCells = 0;
                        break;
                    }
                }

                // Un alignement est valable s'il contient des jetons du joueur et des cellules vides
                if (playerCells > 0 && emptyCells > 0) {
                    count++;
                }
            }
        }

        return count;
    }

    // Compter les alignements diagonaux avec potentiel
    private int countDiagonalAlignments(int player, int length) {
        return countDiagonalAlignmentsUp(player, length) +
                countDiagonalAlignmentsDown(player, length);
    }

    // Compter les diagonales montantes (↗) avec potentiel
    private int countDiagonalAlignmentsUp(int player, int length) {
        int count = 0;

        for (int row = 0; row <= this.rows - length; row++) {
            for (int col = 0; col <= this.cols - length; col++) {
                int playerCells = 0;
                int emptyCells = 0;

                for (int k = 0; k < length; k++) {
                    char cell = this.getValueAt(row + k, col + k);

                    if (cell == player) {
                        playerCells++;
                    } else if (cell == EMPTY) {
                        emptyCells++;
                    } else {
                        // Une cellule adverse bloque l'alignement
                        playerCells = 0;
                        emptyCells = 0;
                        break;
                    }
                }

                if (playerCells > 0 && emptyCells > 0) {
                    count++;
                }
            }
        }

        return count;
    }

    // Compter les diagonales descendantes (↘) avec potentiel
    private int countDiagonalAlignmentsDown(int player, int length) {
        int count = 0;

        for (int row = length - 1; row < this.rows; row++) {
            for (int col = 0; col <= this.cols - length; col++) {
                int playerCells = 0;
                int emptyCells = 0;

                for (int k = 0; k < length; k++) {
                    char cell = this.getValueAt(row - k, col + k);

                    if (cell == player) {
                        playerCells++;
                    } else if (cell == EMPTY) {
                        emptyCells++;
                    } else {
                        // Une cellule adverse bloque l'alignement
                        playerCells = 0;
                        emptyCells = 0;
                        break;
                    }
                }

                if (playerCells > 0 && emptyCells > 0) {
                    count++;
                }
            }
        }

        return count;
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
