/**
 * Classe Perceptron.
 * Copyright (C) <2011> <Nicolas Rougier & Yann Boniface>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.*;
import java.util.*;

public class Perceptron {

    public static double[] appPerceptron(double[] perceptron, double [] exemple,
                                         double sortie, double pas) {
	/* 
	   mise a jour des poids du perceptron 
	   suivant la regle d'apprentissage 
	   du perceptron 
	*/

        /* poids */


        /* seuil */
        return perceptron;
    }

    public static  double[] hebb(double[] perceptron, double [] exemple,
                                 double pas) {
	/*
	  mise a jour des poids du perceptron 
	  suivant la loi de hebb 
	*/


        /* poids */

        /* seuil */

        return perceptron;
    }

    public static void affichePoids(double[] perceptron) {
        int i;

        for (i=0; i< perceptron.length -1 ; i++)
            System.out.printf("\t w%d =  %2.2f", i, perceptron[i]);
        System.out.printf("\t seuil =  %2.2f\n", perceptron[perceptron.length -1]);

    }


    public static void learn(double[][] exemples, String loi) {

        /* Deux poids sur les entrees [0][1], un seuil [2] */
        double[] perceptron = {Math.random(), Math.random(), Math.random()};

        boolean[] appris = {false, false, false,false};
        boolean apprentissage = false;
        /* vrai si tous les exemples passent sans erreur */

        affichePoids(perceptron);

        while (!(apprentissage)) {
            int i;
            double pas = .005;
            double sortie;

            /* tirer un des exemples au hasard */
            int courant = (int)(Math.random() * (exemples.length));

            /* calculer la sortie du perceptron sur cet exemple */
            /* sortie = -seuil + ent_0 * poids_0 + ent_1 * poids_1 */
            sortie = -perceptron[2];
            for (i = 0 ; i <   perceptron.length -1 ; i++) {
                sortie = sortie + exemples[courant][i] * perceptron[i];
            }

            /* sortie = 0 / 1 */
            if (sortie > 0)
                sortie = 1;
            else
                sortie = -1;
            System.out.printf("exemple[%d] : (%2.0f, %2.0f) -> %2.0f  \t sortie %2.0f\n",
                    courant, exemples[courant][0],
                    exemples[courant][1],  exemples[courant][2],
                    sortie);

            /* Besoin apprentissage ? */
            if (sortie != exemples[courant][2]) {
                System.out.print("\t Apprentissage");
                if (loi == "hebb") {
                    System.out.println(" hebbien");
                    /* modification des poids, loi de Hebb */
                    perceptron = hebb(perceptron, exemples[courant], pas);
                } else if (loi == "perceptron" ) {
                    System.out.println(" perceptron");
                    /* modification des poids, regle du perceptron */
                    perceptron = appPerceptron(perceptron, exemples[courant], sortie, pas);
                } else {
                    System.out.println(" Erreur apprentissage inconnu...");
                    System.exit(1);
                }
                affichePoids(perceptron);

                /* Tout a reapprendre... */
                for (i=0; i<appris.length; i++)
                    appris[i] = false;
            } else appris[courant] = true;

            apprentissage = true;
            for (i=0; i<appris.length; i++) {
                apprentissage = apprentissage && appris[i];
            }

        }
        System.out.println("\n Apprentissage fini...\n\n");
    }




    public static void main(String [] args) throws IOException {
        double[][] table = /* à compléter */;


        System.out.println("... table ...");
        learn(table, /* à compléter */);

    }
}