package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.ArgParse;
import ia.framework.common.Misc;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends TreeSearch {

    public BFS(SearchProblem prob, State initial_state) {
        super(prob, initial_state);
    }

    public boolean solve() {
        // Stratégie FIFO, first in first out
        Queue<SearchNode> frontier = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();

        // On commence à l'état initial
        SearchNode node = SearchNode.makeRootSearchNode(initial_state);
        State state = node.getState();

        // Ajouter l'état initial à la frontière et à l'ensemble des états visités
        frontier.add(node);
        visited.add(state);

        if (ArgParse.DEBUG) {
            System.out.print("[\n" + state);
        }

        // Tant qu'il y a des états à explorer dans la frontière
        while (!frontier.isEmpty()) {
            // Retirer le premier élément de la frontière (FIFO)
            node = frontier.poll();
            state = node.getState();

            // Si c'est un état but, on arrête
            if (problem.isGoalState(state)) {
                end_node = node;
                if (ArgParse.DEBUG) {
                    System.out.println("]");  // Affichage de la solution
                }
                return true;
            }

            // Récupérer les actions possibles depuis cet état
            ArrayList<Action> actions = problem.getActions(state);

            if (ArgParse.DEBUG) {
                System.out.print("Actions Possibles : {");
                System.out.println(Misc.collection2string(actions, ','));
            }

            // Explorer les nouveaux états générés
            for (Action a : actions) {
                // Appliquer l'action et obtenir l'état suivant
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                State newState = child.getState();

                // Si l'état n'a pas encore été visité
                if (!visited.contains(newState)) {
                    // Ajouter le nouvel état à la frontière et aux états visités
                    frontier.add(child);
                    visited.add(newState);

                    if (ArgParse.DEBUG) {
                        System.out.println(" + " + a + "] -> [" + newState);
                    }
                }
            }
        }

        return false;  // Si la frontière est vide et que l'objectif n'est pas atteint
    }
}
