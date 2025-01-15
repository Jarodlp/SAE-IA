package ia.algo.recherche;

import java.util.*;

import ia.framework.common.State;
import ia.framework.common.Action;
import ia.framework.common.Misc;
import ia.framework.common.ArgParse;

import ia.framework.recherche.TreeSearch;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.SearchNode;

public class DFS extends TreeSearch {

    public DFS(SearchProblem prob, State initial_state) {
        super(prob, initial_state);
    }

    public boolean solve() {

        // Stratégie LIFO, Last in first out
        Deque<SearchNode> stack = new ArrayDeque<>();
        SearchNode root = SearchNode.makeRootSearchNode(initial_state);
        stack.push(root);

        Set<State> visitedStates = new HashSet<>();

        while (!stack.isEmpty()) {
            SearchNode node = stack.pop();
            State state = node.getState();

            // Evite de visiter des états déjà exploré
            if(visitedStates.contains(state)){
                continue;
            }

            visitedStates.add(state);

            if(problem.isGoalState(state)){
                end_node = node;
                if (ArgParse.DEBUG)
                    System.out.print("[\n" +  node.getState() + "goal state trouvé");
                return true;
            }

            ArrayList<Action> actions = problem.getActions(state);

            for(Action action : actions){
                SearchNode childnode = SearchNode.makeChildSearchNode(problem, node, action);
                stack.push(childnode);
            }
        }

        if (ArgParse.DEBUG)
            System.out.println("Pas de solution trouvé");

        return false;
    }
}