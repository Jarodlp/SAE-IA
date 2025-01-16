package ia.algo.recherche;

import java.util.*;

import ia.framework.common.State;
import ia.framework.common.Action;
import ia.framework.common.Misc;
import ia.framework.common.ArgParse;

import ia.framework.recherche.*;

public class GFS extends TreeSearch {

    public GFS(SearchProblem prob, State initial_state){
        super(prob, initial_state);
    }

    public boolean solve(){

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(
                (node1, node2) -> Double.compare(node1.getHeuristic(), node2.getHeuristic())
        );
        HashSet<State> visited = new HashSet<>();

        SearchNode node = SearchNode.makeRootSearchNode(initial_state);
        frontier.add(node);

        while(!frontier.isEmpty()){

            node = frontier.poll();
            State state = node.getState();
            visited.add(state);

            if(problem.isGoalState(state)) {
                end_node = node;
                return true;
            }

                ArrayList<Action> actions = problem.getActions(state);

                for (Action action : actions) {
                    SearchNode childNode = SearchNode.makeChildSearchNode(problem, node, action);

                    if (!visited.contains(childNode.getState())) {
                        frontier.add(childNode);
                    }
                }
            }

        return false;
    }
}
