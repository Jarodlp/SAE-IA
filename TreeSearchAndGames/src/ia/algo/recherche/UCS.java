package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class UCS extends TreeSearch {

    public UCS(SearchProblem prob, State initial_state) {
        super(prob, initial_state);
    }

    public boolean solve() {
        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(
                (node1, node2) -> Double.compare(node1.getCost(), node2.getCost())
        );
        HashMap<State, Double> costs = new HashMap<>();

        SearchNode node = SearchNode.makeRootSearchNode(initial_state);
        frontier.add(node);
        costs.put(node.getState(), node.getCost());

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            State state = node.getState();

            if (problem.isGoalState(state)) {
                end_node = node;
                return true;
            }

            ArrayList<Action> actions = problem.getActions(state);
            for (Action action : actions) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, action);
                State newState = child.getState();
                double newCost = child.getCost();

                // Check if this state is not visited or if a better cost is found
                if (!costs.containsKey(newState) || newCost < costs.get(newState)) {
                    costs.put(newState, newCost);
                    frontier.add(child);
                }
            }
        }
        return false;
    }

}
