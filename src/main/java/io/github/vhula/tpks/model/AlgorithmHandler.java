package io.github.vhula.tpks.model;

import java.util.ArrayList;

/**
 * Class which looks for all pahts, all cycles and all eternal cycles.
 */
public class AlgorithmHandler {
    /**
     * All paths
     */
    private final ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
    private final ArrayList<ArrayList<Integer>> cycles = new ArrayList<ArrayList<Integer>>();
    private final ArrayList<ArrayList<Integer>> eternalCycles = new ArrayList<ArrayList<Integer>>();


    public AlgorithmHandler() {
    }

    /**
     * Method looks for all pahts.
     */
    public void findAllPaths() {
        paths.clear();
        alls.clear();
        AlgoMatrix algo = AlgoMatrix.getInstance();
        algo.parseConnectionMatrix();
        algo.parseSignalsMatrix();
        for (int i = 0; i < AlgoMatrix.getInstance().getConnectionMatrix().length; i++) {
            bfs(i, new ArrayList<Integer>());
        }
        for (ArrayList<Integer> buf : alls) {
            if (buf.get(0) == 0)
                paths.add(buf);
        }
        for (ArrayList<Integer> buf : alls) {
            if (!isPathsContain(buf.get(0)))
                paths.add(buf);
        }
        alls.clear();
    }

    /**
     * Checks if one of paths contain node.
     * @param idx - node
     * @return - True if contains, else false.
     */
    private boolean isPathsContain(int idx) {
        for (ArrayList<Integer> buf : paths) {
            if (buf.contains(idx))
                return true;
        }
        return false;
    }

    private ArrayList<ArrayList<Integer>> alls = new ArrayList<ArrayList<Integer>>();

    /**
     * Looks for all paths from one node to the last.
     * @param from - Begin node.
     * @param visited - already visited nodes.
     * @return - Paths.
     */
    public ArrayList<ArrayList<Integer>> bfs(int from, ArrayList<Integer> visited) {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> buf = new ArrayList<ArrayList<Integer>>();
        queue.add(from);
        if (visited.contains(from)) {
            queue.clear();
        }
        visited.add(from);
        int q = from;
        int[] next = null;
        boolean flag = true;
        while (!queue.isEmpty()) {
            q = queue.get(0);
            queue.remove(queue.get(0));
            next = AlgoMatrix.getInstance().getNext(q);
            if (next == null)
                break;
            if (next.length == 2) {
                flag = false;
                for (int i = 0; i < next.length; i++) {
                    bfs(next[i], new ArrayList<Integer>(visited));
                }
                break;
            }
            for (int i = 0; i < next.length; i++) {
                if (!visited.contains(next[i])) {
                    visited.add(next[i]);
                    queue.add(next[i]);
                } else {
                    if (visited.get(0) == next[0] && next.length == 1)
                        visited.add(next[i]);
                }
            }
        }
        if (flag) {
            alls.add(visited);
        }
        return alls;
    }

    /**
     * Looks if pahts is similar.
     * @param first - First path.
     * @param second - Second path.
     * @return - True if similar, else False.
     */
    private boolean isSimilar(ArrayList<Integer> first, ArrayList<Integer> second) {
        if (first.size() != second.size())
            return false;
        for (int i = 0; i < first.size(); i++) {
            if (first.get(i) != second.get(i))
                return false;
        }
        return true;
    }

    /**
     * Checks cycles.
     * @param cycle - Cycle.
     * @return - True if first node of paths the biggest.
     */
    private boolean isWrong(ArrayList<Integer> cycle) {
        int first = cycle.get(0);
        boolean flag = false;
        for (int i = 1; i < cycle.size(); i++) {
            if (first < cycle.get(i)){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Looks for all cycles.
     * @param from - First node.
     * @param visited - Already visited nodes.
     */
    public void findAllCycles(int from, ArrayList<Integer> visited) {
        alls.clear();
        cycles.clear();
        for (int i = 0; i < AlgoMatrix.getInstance().getConnectionMatrix().length; i++) {
            bfs(i, new ArrayList<Integer>());
        }

        for (int i = 0; i < alls.size(); i++) {
            ArrayList<Integer> current = alls.get(i);
            if (current.get(0) == current.get(current.size() - 1) && current.size() > 1) {
                for (int j = 0; j < cycles.size(); j++) {
                    if (isSimilar(current, cycles.get(j)))
                        continue;
                }
                if (isWrong(current))
                    continue;
                cycles.add(current);
            }
        }

    }

    /**
     * Looks for all eternal cycles.
     */
    public void findEternalCycles() {
        eternalCycles.clear();
        for (int i = 0; i < cycles.size(); i++) {
            if (FileModel.getInstance().getAlgorithm().getNode(cycles.get(i).get(0)).getClass().equals(OperatorElement.class)) {
                eternalCycles.add(cycles.get(i));
            }
        }
        eternals = true;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        AlgoMatrix algo = AlgoMatrix.getInstance();
        Algorithm algorithm = FileModel.getInstance().getAlgorithm();
        algo.parseConnectionMatrix();
        algo.parseSignalsMatrix();
        int[][] conMatrix = algo.getConnectionMatrix();
        int[][] signalsMatrix = algo.getSignalsMatrix();
        int length = conMatrix.length;
        for (int i = 0; i < length - 1; i++) {
            int cur = i;
            int next = i + 1;
            if (algo.isCondition(cur)) {
                for (int j = cur + 1; j < length - 1; j++) {
                    if (algo.isOperator(j)) {
                        indexes.clear();
                        break;
                    }
                    if (algo.isCondition(j)) {
                        String strCur = algorithm.getNode(cur).toString();
                        String strJ = algorithm.getNode(j).toString();
                        if (strCur.equals(strJ)) {
                            boolean flag = true;
                            int[] curNext = algo.getNext(cur);
                            int[] jNext = algo.getNext(j);
                            if (curNext[0] > j) {
                                flag = false;
                            } else if (jNext[0] > j) {
                                flag = false;
                            }
                            for (int k = 0; k < indexes.size(); k++) {
                                if (indexes.get(k) > j)
                                    flag = false;
                            }
                            if (flag) {
                                eternals = true;
                                eternalCycles.add(getCycleWithBeginner(j));
                            }
                            indexes.clear();
                        } else {
                            int[] nextJ = algo.getNext(j);
                            indexes.add(nextJ[0]);
                            indexes.add(nextJ[1]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Return cycles with first node.
     * @param idx - Node.
     * @return - Cycle with Node.
     */
    private ArrayList<Integer> getCycleWithBeginner(int idx) {
        for (int i = 0; i < cycles.size(); i++) {
            if (cycles.get(i).get(0) == idx) {
                return cycles.get(i);
            }
        }
        return null;
    }

    /**
     * Return all paths, cycles and eternal cycles in string.
     * @return - String.
     */
    public String toString() {
        String res = "All Paths\n";
        Algorithm algorithm = FileModel.getInstance().getAlgorithm();
        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Integer> path = paths.get(i);
            for (int j = 0; j < path.size(); j++) {
                res += "  " + algorithm.getNode(path.get(j)).toString();
            }
            res += " :: ";
            res += paths.get(i);
            res += "\n";
        }
        res += "\n";
        res += "All Cycles\n";
        for (int i = 0; i < cycles.size(); i++) {
            ArrayList<Integer> cycle = cycles.get(i);
            for (int j = 0; j < cycle.size(); j++) {
                res += "  " + algorithm.getNode(cycle.get(j)).toString();
            }
            res += " :: ";
            res += cycles.get(i);
            res += "\n";
        }
        res += "\n";
        res += "Eternal Cycles\n";
        for (int i = 0; i < eternalCycles.size(); i++) {
            ArrayList<Integer> cycle = eternalCycles.get(i);
            for (int j = 0; j < cycle.size(); j++) {
                res += "  " + algorithm.getNode(cycle.get(j)).toString();
            }
            res += " :: ";
            res += eternalCycles.get(i);
            res += "\n";
        }
        return res;
    }

    public boolean eternals = true;

    /**
     *
     * @param idx
     * @return
     */
    public boolean isInEternal(int idx) {
        for (ArrayList<Integer> buf : eternalCycles) {
            for (int j = 0; j < buf.size(); j++) {
                if (buf.get(j) == idx) {
                    return true;
                }
            }
        }
        return false;
    }

}
