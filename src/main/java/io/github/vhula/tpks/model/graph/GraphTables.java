package io.github.vhula.tpks.model.graph;

import io.github.vhula.tpks.model.*;

import java.util.ArrayList;

/**
 * Class which encapsulates graph tables.
 */
public class GraphTables {
    /**
     * Table of states transitions.
     */
    private int[][] transitionTable;
    /**
     * Table for matching states and signals.
     */
    private int[][] matchTable;

    private int[][] neighborCode;

    public GraphTables() {

    }

    /**
     * Method for generating tables from lsa.
     * @param graph - Graph of lsa.
     */
    public void generateTables(Graph graph) {
        generateTransitionTable(graph);
        generateMatchTable(graph);
        generateNeighborCodesTable(graph);
    }

    /**
     * Generates transition table.
     * @param graph - Graph of lsa.
     */
    protected void generateTransitionTable(Graph graph) {
        ArrayList<Element> operators = graph.getOperators();
        Algorithm algorithm = FileModel.getInstance().getAlgorithm();
        ArrayList<Element> conditions = algorithm.getConditions();

        ArrayList<Connection> allConnections = graph.getConnections();
        transitionTable = new int[allConnections.size()][];

        ArrayList<State> states = graph.getStates();
        for (int i = 0; i < allConnections.size(); i++) {
            Connection connection = allConnections.get(i);
            State start = connection.getStartState();
            State end = connection.getEndState();
            int startIdx = graph.getStateIndex(start);
            int endIdx = graph.getStateIndex(end);
            int plusSize = connection.getConnection().size();
            transitionTable[i] = new int[states.size() +((plusSize > 0)? plusSize : 1)];
            transitionTable[i][startIdx] = endIdx;
            int[] numbers = connection.getConnectionsNumbers();
            if (numbers.length > 0) {
                for (int j = states.size(); j < transitionTable[i].length; j++) {
                    transitionTable[i][j] = numbers[j - states.size()];
                }
            } else {
                transitionTable[i][states.size()] = 0;
            }
        }

    }

    /**
     * Generates table of signals and states matching.
     * @param graph - Graph of lsa.
     */
    protected void generateMatchTable(Graph graph) {
        ArrayList<Element> operators = graph.getOperators();
        ArrayList<Integer> signalsNumbers = new ArrayList<Integer>();
        for (int i = 0; i < operators.size(); i++) {
            Element element = operators.get(i);
            int[] idxs = element.getIndexes();
            for (int j = 0; j < idxs.length; j++) {
                if (!signalsNumbers.contains(idxs[j])) {
                    signalsNumbers.add(idxs[j]);
                }
            }
        }
        matchTable = new int[signalsNumbers.size() + 1][graph.statesCount() + 1];
        fillByNumber(matchTable, 0);
        initMatchHeaders(signalsNumbers);
        fillMatchTable(graph);

    }

    protected void generateNeighborCodesTable(Graph graph) {
        neighborCode = new int[2][graph.statesCount()];
        if (graph.getState(0).getCode() == null)
            return;
        for (int i = 0; i < graph.statesCount(); i++) {
            neighborCode[0][i] = graph.getState(i).getCode().getValue();
            neighborCode[1][i] = graph.getState(i).getNumber();
        }
    }

    /**
     * Method for generating match table values.
     * @param graph - Graph of lsa.
     */
    protected void fillMatchTable(Graph graph) {
        for (int i = 1; i < matchTable.length; i++) {
            for (int j = 1; j < matchTable[i].length; j++) {
                if (graph.getState(matchTable[0][j]).hasSignal(matchTable[i][0])) {
                    matchTable[i][j] = 1;
                }
            }
        }
    }

    /**
     * Initialise headers of match table.
     * @param signalsNumbers - Signals of graph.
     */
    protected void initMatchHeaders(ArrayList<Integer> signalsNumbers) {
        for (int i = 1; i < matchTable.length; i++) {
            matchTable[i][0] = signalsNumbers.get(i - 1);
        }
        for (int i = 1; i < matchTable[0].length; i++) {
            matchTable[0][i] = i - 1;
        }
    }

    /**
     * Method for filling table with number.
     * @param table - Table for filling.
     * @param number - Number for filling.
     */
    public void fillByNumber(int[][] table, int number) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = number;
            }
        }
    }

    public void printTransitionTable() {
        System.out.print(" ");
        for (int i = 0; i < transitionTable[0].length - 1; i++) {
            System.out.printf("   %d", i);
        }
        System.out.println();
        for (int i = 0; i < transitionTable.length; i++) {
            System.out.printf("%d", i);
            for (int j = 0; j < transitionTable[i].length; j++) {
                System.out.printf("   %d", transitionTable[i][j]);
            }
            System.out.println();
        }
    }

    public void printMatchTable() {
        System.out.println();
        for (int i = 0; i < matchTable.length; i++) {
            for (int j = 0; j < matchTable[i].length; j++) {
                System.out.printf("   %d", matchTable[i][j]);
            }
            System.out.println();
        }
    }

    public void printNeighborTable() {
        System.out.println();
        for (int i = 0; i < neighborCode.length; i++) {
            for (int j = 0; j < neighborCode[i].length; j++) {
                System.out.printf("   %d", neighborCode[i][j]);
            }
            System.out.println();
        }
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < transitionTable.length; i++) {
            for (int j = 0; j < transitionTable[i].length; j++) {
                res += ("" + transitionTable[i][j]);
                res += " ";
            }
            res = res.substring(0, res.length() - 1);
            res += "\n";
        }
        res += ":";
        for (int i = 0; i < matchTable.length; i++) {
            for (int j = 0; j < matchTable[i].length; j++) {
                res += ("" + matchTable[i][j]);
                res += " ";
            }
            res = res.substring(0, res.length() - 1);
            res += "\n";
        }
        if (neighborCode != null) {
            res += ":";
            for (int i = 0; i < neighborCode.length; i++) {
                for (int j = 0; j < neighborCode[i].length; j++) {
                    res += ("" + neighborCode[i][j]);
                    res += " ";
                }
                res = res.substring(0, res.length() - 1);
                res += "\n";
            }
        }
        res = res.substring(0, res.length() - 1);
        return res;
    }

    public int[][] getTransitionTable() {
        return transitionTable;
    }

    public int[][] getMatchTable() {
        return matchTable;
    }

    public int[][] getNeighborCode() {
        return neighborCode;
    }

    public void setTransitionTable(int[][] transitionTable) {
        this.transitionTable = transitionTable;
    }

    public void setMatchTable(int[][] matchTable) {
        this.matchTable = matchTable;
    }

    public void setNeighborCode(int[][] neighborCode) {
        this.neighborCode = neighborCode;
    }

}
