package ia.algo.recherche;

import java.util.*;

import ia.framework.common.State;
import ia.framework.common.Action;

import ia.framework.recherche.TreeSearch;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.SearchNode;

public class Astar extends TreeSearch {

    public Astar(SearchProblem prob, State initial_state){
        super(prob, initial_state);
    }

    public boolean solve(){

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(
                (node1, node2) -> Double.compare(node1.getCost() + node1.getHeuristic(), node2.getCost() + node2.getHeuristic())
        );
        SearchNode node = SearchNode.makeRootSearchNode(initial_state);
        HashSet<State> visited = new HashSet<>();
        frontier.add(node);

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            State state = node.getState();

            visited.add(state);

            if (problem.isGoalState(state)) {
                end_node = node;
                return true;
            }

            ArrayList<Action> actions = problem.getActions(state);

            for (Action action : actions) {
                SearchNode childNode = SearchNode.makeChildSearchNode(problem, node, action);

                boolean nodeUpdated = false;
                for (SearchNode frontierNode : frontier) {
                    if (frontierNode.getState().equals(childNode.getState()) &&
                            childNode.getCost() + childNode.getHeuristic() < frontierNode.getCost() + frontierNode.getHeuristic()) {

                        frontier.remove(frontierNode);
                        frontier.add(childNode);
                        nodeUpdated = true;
                        break;
                    }
                }

                if (!nodeUpdated && !visited.contains(childNode.getState())) {
                    frontier.add(childNode);
                }
            }
        }
        return false;
    }
}




