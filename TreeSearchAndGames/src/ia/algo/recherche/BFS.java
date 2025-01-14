package ia.algo.recherche;

import java.util.ArrayList;

import ia.framework.common.State;
import ia.framework.common.Action;
import ia.framework.common.Misc;
import ia.framework.common.ArgParse;

import ia.framework.recherche.TreeSearch;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.SearchNode;

public class BFS extends TreeSearch {

    public BFS(SearchProblem prob, State intial_state) {
        super(prob, intial_state);
    }

    public boolean solve() {
        // Stratégie FIFO, first in first out

        // On commence à létat initial
        SearchNode node = SearchNode.makeRootSearchNode(initial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("[\n" + state);

        while (!problem.isGoalState(state)) {
            // Les actions possibles depuis cette état
            ArrayList<Action> actions = problem.getActions(state);

            if (ArgParse.DEBUG) {
                System.out.print("Actions Possibles : {");
                System.out.println(Misc.collection2string(actions, ','));
            }

            // Choisir la première
            Action a = actions.get(0);
            if (ArgParse.DEBUG)
                System.out.println("Action choisie: " + a);


            // Executer et passer a l'état suivant
            node = SearchNode.makeChildSearchNode(problem, node, a);
            state = node.getState();

            if (ArgParse.DEBUG)
                System.out.print(" + " + a + "] -> [" + state);
        }

        // Enregistrer le noeud final
        end_node = node;

        if (ArgParse.DEBUG)
            System.out.println("]");

        return true;
    }
}