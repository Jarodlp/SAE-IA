package ia.algo.recherche;

import ia.algo.recherche.Hyperbolique;
import ia.algo.recherche.Sigmoide;
import ia.algo.recherche.TransferFunction;

import java.util.*;
import java.util.stream.IntStream;

public class PerceptronMLP {

    public static void mlp(int[] layers, double learningRate, TransferFunction fun, double[][] entree, double[][] sortieVoulu) {
        MLP mlp = new MLP(layers, learningRate, fun);

        double totalErreur;
        int maxIteration = 1000000;
        int i = 0;
        double tolerance = 0.05;

        List<double[]> inputsList = new ArrayList<>(Arrays.asList(entree));
        List<double[]> outputsList = new ArrayList<>(Arrays.asList(sortieVoulu));

        List<Integer> indices = new ArrayList<>(IntStream.range(0, entree.length).boxed().toList());
        Collections.shuffle(indices);

        double[][] shuffledEntree = new double[entree.length][];
        double[][] shuffledSortie = new double[sortieVoulu.length][];
        for (int j = 0; j < indices.size(); j++) {
            shuffledEntree[j] = inputsList.get(indices.get(j));
            shuffledSortie[j] = outputsList.get(indices.get(j));
        }

        while (i < maxIteration) {
            totalErreur = 0;
            boolean convergence = true;

            for (int j = 0; j < shuffledEntree.length; j++) {
                double[] input = shuffledEntree[j];
                double[] outputVoulu = shuffledSortie[j];
                double[] output = mlp.execute(input);

                double erreur = 0;
                for (int k = 0; k < outputVoulu.length; k++) {
                    if (Math.abs(outputVoulu[k] - output[k]) > tolerance) {
                        convergence = false;
                    }
                    erreur += Math.pow(outputVoulu[k] - output[k], 2);
                }
                totalErreur += erreur;

                mlp.backPropagate(input, outputVoulu);
            }

            System.out.println("Erreur totale à l'itération " + i + ": " + totalErreur);
            for (int j = 0; j < shuffledEntree.length; j++) {
                double[] output = mlp.execute(shuffledEntree[j]);
                double[] expectedOutput = shuffledSortie[j]; // Capture la valeur de shuffledSortie[j]

                System.out.println("Exemple " + (j + 1) + ": Entrée = " + Arrays.toString(shuffledEntree[j]) +
                        ", Sortie attendue = " + Arrays.toString(expectedOutput) +
                        ", Sortie générée = " + Arrays.toString(output) +
                        ", Erreur = " + Arrays.toString(Arrays.stream(output)
                        .map(k -> Math.pow(k - expectedOutput[0], 2)) // Utilisation de expectedOutput
                        .toArray()));
            }


            if (convergence) {
                System.out.println("Le modèle a convergé avec une tolérance de " + tolerance);
                break;
            }
            i++;
        }

        if (i >= maxIteration) {
            System.out.println("Le nombre maximal d'itérations a été atteint.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TransferFunction transferFunction = null;
        while (transferFunction == null) {
            System.out.println("Choisir la fonction de transfert (1: Sigmoid, 2: Hyperbolique): ");
            int choice = scanner.nextInt();

            if (choice == 1) transferFunction = new Sigmoide();
            else if (choice == 2) transferFunction = new Hyperbolique();
            else System.out.println("Veuillez choisir 1 (Sigmoide) ou 2 (Hyperbolique)");
        }

        System.out.println("Choisir le taux d'apprentissage (ex: 0.1, 0.5, 0.6)");
        double learningRate = scanner.nextDouble();

        int[] layers = {2, 3, 1};

        double[][] andInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] andExpectedOutputs = {{0}, {0}, {0}, {1}};

        double[][] orInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] orExpectedOutputs = {{0}, {1}, {1}, {1}};

        double[][] xorInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] xorExpectedOutputs = {{0}, {1}, {1}, {0}};

        System.out.println("Choisir le problème (1: AND, 2: OR, 3: XOR)");
        int prob = scanner.nextInt();

        switch (prob) {
            case 1 -> {
                System.out.println("Lancement du perceptron pour la table AND");
                mlp(layers, learningRate, transferFunction, andInputs, andExpectedOutputs);
            }
            case 2 -> {
                System.out.println("Lancement du perceptron pour la table OR");
                mlp(layers, learningRate, transferFunction, orInputs, orExpectedOutputs);
            }
            case 3 -> {
                System.out.println("Lancement du perceptron pour la table XOR");
                mlp(layers, learningRate, transferFunction, xorInputs, xorExpectedOutputs);
            }
            default -> System.out.println("Choix invalide");
        }
    }
}
