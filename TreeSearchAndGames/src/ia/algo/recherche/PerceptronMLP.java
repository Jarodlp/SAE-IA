package ia.algo.recherche;

import ia.algo.recherche.Hyperbolique;
import ia.algo.recherche.Sigmoide;
import ia.algo.recherche.TransferFunction;

import java.util.*;
import java.util.stream.IntStream;

public class PerceptronMLP {

    public static void mlp(int[] layers, double learningRate, TransferFunction fun, double[][] entree, double[][] sortieVoulu) {
        MLP mlp = new MLP(layers, learningRate, fun);

        double totalErreur = 0;  // Utiliser un double pour l'erreur totale
        int maxIteration = 1000000;
        int i = 0;

        // Mélanger les données avant de commencer l'entraînement
        List<double[]> inputsList = new ArrayList<>();
        List<double[]> outputsList = new ArrayList<>();
        for (int j = 0; j < entree.length; j++) {
            inputsList.add(entree[j]);
            outputsList.add(sortieVoulu[j]);
        }

        // Mélanger les entrées et sorties de manière synchrone
        List<Integer> indices = new ArrayList<>(IntStream.range(0, entree.length).boxed().toList());
        Collections.shuffle(indices);

        // Réorganiser les entrées et sorties selon les indices mélangés
        List<double[]> shuffledInputs = new ArrayList<>();
        List<double[]> shuffledOutputs = new ArrayList<>();
        for (int idx : indices) {
            shuffledInputs.add(inputsList.get(idx));
            shuffledOutputs.add(outputsList.get(idx));
        }

        // Convertir les listes mélangées en tableaux 2D
        double[][] shuffledEntree = new double[shuffledInputs.size()][];  // Entrées mélangées
        double[][] shuffledSortie = new double[shuffledOutputs.size()][]; // Sorties mélangées
        for (int j = 0; j < shuffledInputs.size(); j++) {
            shuffledEntree[j] = shuffledInputs.get(j);
            shuffledSortie[j] = shuffledOutputs.get(j);
        }

        while (i < maxIteration) {
            totalErreur = 0; // Réinitialiser l'erreur totale pour chaque itération

            for (int j = 0; j < shuffledEntree.length; j++) {
                double[] input = shuffledEntree[j];
                double[] outputVoulu = shuffledSortie[j];

                // Exécution du perceptron pour l'entrée donnée
                double[] output = mlp.execute(input);

                // Calcul de l'erreur quadratique
                double erreur = 0;
                for (int k = 0; k < outputVoulu.length; k++) {
                    erreur += Math.pow(outputVoulu[k] - output[k], 2);
                }
                totalErreur += erreur;  // Ajouter l'erreur de cet exemple à l'erreur totale

                // Backpropagation de l'erreur pour ajuster les poids
                mlp.backPropagate(input, outputVoulu);

                // Log des résultats de l'exécution de chaque exemple
                System.out.println("Exemple " + (j + 1) + ": Entrée = " + Arrays.toString(input) + ", Sortie attendue = " + Arrays.toString(outputVoulu) + ", Sortie générée = " + Arrays.toString(output) + ", Erreur = " + erreur);
            }

            // Log de l'erreur totale pour l'itération actuelle
            System.out.println("Erreur totale à l'itération " + i + ": " + totalErreur);

            // Vérifier si l'erreur totale est suffisamment faible (convergence)
            if (totalErreur == 0) {
                System.out.println("Tous les exemples ont été correctement classifiés.");
                break;
            }

            // Log l'erreur à chaque itération (optionnel)
            if (i % 100 == 0) {
                System.out.println("Erreur à l'itération " + i + ": " + totalErreur);
            }

            i++;
        }

        // Log final si l'apprentissage a terminé
        if (i >= maxIteration) {
            System.out.println("Le nombre maximal d'itérations a été atteint.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean funOk = false;
        TransferFunction transferFunction = null;
        while (!funOk) {
            System.out.println("Choisir la fonction de transfert (1: Sigmoid, 2: Hyperbolique): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                transferFunction = new Sigmoide();
                funOk = true;
            } else if (choice == 2) {
                transferFunction = new Hyperbolique();
                funOk = true;
            } else {
                System.out.println("Veuillez choisir 1 (Sigmoide) ou 2 (Hyperbolique)");
            }
        }

        System.out.println();

        boolean sortOk = false;
        int dimSortie = Integer.MAX_VALUE;
        while (!sortOk) {
            System.out.println("Choisir maintenant la dimension de sortie (1 : 1D, 2: 2D)");
            dimSortie = scanner.nextInt();
            if (dimSortie == 1 || dimSortie == 2)
                sortOk = true;
        }

        System.out.println();
        System.out.println("Choisir le taux d'apprentissage (ex: 0.1, 0.5, 0.6)");
        double learningRate = scanner.nextDouble();

        int[] layers = {2, 3, 1};

        double[][] andInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] andExpectedOutputs = {{0}, {0}, {0}, {1}};

        double[][] orInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] orExpectedOutputs = {{0}, {1}, {1}, {1}};

        double[][] xorInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] xorExpectedOutputs = {{0}, {1}, {1}, {0}};

        int prob = Integer.MAX_VALUE;
        if (dimSortie == 1) {
            System.out.println("Choisir le problème (1: AND, 2: OR, 3: XOR)");
            prob = scanner.nextInt();
            switch (prob) {
                case 1:
                    System.out.println("Lancement du perceptron pour la table AND");
                    mlp(layers, learningRate, transferFunction, andInputs, andExpectedOutputs);
                    break;
                case 2:
                    System.out.println("Lancement du perceptron pour la table OR");
                    mlp(layers, learningRate, transferFunction, orInputs, orExpectedOutputs);
                    break;
                case 3:
                    System.out.println("Lancement du perceptron pour la table XOR");
                    mlp(layers, learningRate, transferFunction, xorInputs, xorExpectedOutputs);
                    break;
            }
        }
    }
}
