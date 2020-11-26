package io.github.vhula.tpks.model.graph;

import io.github.vhula.tpks.model.*;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which represents graph of states.
 */
public class Graph {
    /**
     * States of graph.
     */
    private ArrayList<State> states = new ArrayList<State>();
    /**
     * Table represent of graph.
     */
    private GraphTables tables;
    /**
     * Signals of states.
     */
    private ArrayList<Element> operators = new ArrayList<Element>();

    private ArrayList<ConditionalElement> conditionals = new ArrayList<ConditionalElement>();
    /**
     * Connections between states.
     */
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    public Graph(boolean  flag) {

    }

    public Graph() {
        /*operators = FileModel.getInstance().getAlgorithm().getOperators();
        generateStates(FileModel.getInstance().getAlgorithm());
        generateConnections();*/
    }

    public void initGraph() {
        operators = FileModel.getInstance().getAlgorithm().getOperators();
        generateStates(FileModel.getInstance().getAlgorithm());
        generateConnections();
    }

    /**
     * Method for parsing tables and creation object structure.
     */
    private void parseTables() {
        states = new ArrayList<State>();
        operators = new ArrayList<Element>();
        connections = new ArrayList<Connection>();
        int[][] transitionTable = tables.getTransitionTable();
        int[][] matchTable = tables.getMatchTable();
        int[][] neighborTable = tables.getNeighborCode();
        int count = 0;
        int statesCount = 0;
        for (int i = 0; i < transitionTable.length; i++) {
            if (transitionTable[i][transitionTable[0].length - 1] == 0) {
                statesCount = transitionTable[0].length - 1;
                break;
            }
        }
        System.out.println("States Count = " + statesCount);
        int[] signs = {0};
        Element element = BeginElement.getInstance();
        states.add(new State(count, element));
        operators.add(element);
        count++;
        for (int i = 2; i < matchTable[0].length - 1; i++) {
            ArrayList<Integer> signals = new ArrayList<Integer>();
            for (int j = 1; j < matchTable.length; j++) {
                if (matchTable[j][i] == 1) {
                    signals.add(matchTable[j][0]);
                }
            }
            int[] intSignals = new int[signals.size()];
            String[] names = new String[signals.size()];
            for (int k = 0; k < signals.size(); k++) {
                intSignals[k] = signals.get(k);
                names[k] = "Y";
            }
            element = new OperatorElement(names, intSignals);
            operators.add(element);
            states.add(new State(count, element));
            count++;
        }
        element = EndElement.getInstance();
        operators.add(element);
        states.add(new State(count, element));
        for (int i = 0; i < states.size(); i++) {
            System.out.println(states.get(i));
        }

        for (int i = 0; i < statesCount; i++) {
            ArrayList<ConditionalElement> conditionals = new ArrayList<ConditionalElement>();
            for (int j = 0; j < transitionTable.length; j++) {
                if (transitionTable[j][i] != 0) {
                    Connection connection = new Connection(states.get(i));
                    connection.setEndState(states.get(transitionTable[j][i]));
                    for (int k = statesCount; k < transitionTable[j].length; k++) {
                        if (transitionTable[j][k] != 0) {
                            String[] names = {"X"};
                            signs = new int[]{transitionTable[j][k] > 0 ? transitionTable[j][k] : -transitionTable[j][k]};
                            boolean flag =  transitionTable[j][k] > 0 ? true : false;
                            ConditionalElement conditionalElement = new ConditionalElement(names, signs);
                            conditionals.add(conditionalElement);
                            connection.addConnection(conditionalElement, flag);
                        }
                    }
                    connections.add(connection);
                }
            }
        }
        int codesSize = getCodeSize();
        for (int i = 0; i < neighborTable[0].length; i++) {
            BinaryNumber bin = new BinaryNumber(neighborTable[0][i], codesSize);
            states.get(neighborTable[1][i]).setCode(bin);
        }
        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i));
        }
    }

    /**
     * Method for opening graph from file.
     * @param filename - Name of file.
     */
    public void openGraph(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tables = new GraphTables();
            int[][] matchTable = (int[][]) ois.readObject();
            int[][] transitionTable = (int[][]) ois.readObject();
            int[][] neighborCode = (int[][]) ois.readObject();
            tables.setTransitionTable(transitionTable);
            tables.setMatchTable(matchTable);
            tables.setNeighborCode(neighborCode);
            parseTables();
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not opened!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not opened!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method for generating tables of graph.
     */
    public void generateTables() {
        tables = new GraphTables();
        tables.generateTables(this);
    }

    /**
     * Mehtod for saving graph tables.
     * @param filename - File for saving.
     */
    public void saveGraph(String filename) {
        generateTables();
        String text = tables.toString();
        try {
            File file = new File(filename + ".graph");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tables.getMatchTable());
            oos.writeObject(tables.getTransitionTable());
            oos.writeObject(tables.getNeighborCode());
            oos.flush();
            oos.close();
        } catch (IOException exc) {
            return;
        }
    }

    /**
     * Generates states for algorithm(lsa).
     * @param algorithm - LSA.
     */
    public void generateStates(Algorithm algorithm) {
        int count = 0;
        for (int i = 0; i < algorithm.size(); i++) {
            Element element = algorithm.getElement(i);
            if (algorithm.isBegin(element) ||
                    algorithm.isEnd(element) ||
                    algorithm.isOperator(element)) {
                states.add(new State(count, element));
                count++;
            }
        }
    }

    public void neighborCoding() {
        addEmptyStates();
        int codeSize = getCodeSize();
        int codesAmount = (int) Math.pow(2, codeSize);
        System.out.println("Code Size = " + codeSize);
        states.get(0).setCode(new BinaryNumber(0, codeSize));
        for (int i = 1; i < statesCount(); i++) {
            int code = selectNeighborCode(i);
            states.get(i).setCode(new BinaryNumber(code, codeSize));
        }
        checkCoding();
    }

    public void checkCoding() {
        boolean flag = false;
        for (int i = 0; i < connectionsCount(); i++) {
            Connection connection = connections.get(i);
            State start = connection.getStartState();
            State end = connection.getEndState();
            BinaryNumber startBin = start.getCode();
            BinaryNumber endBin = end.getCode();
            int endBinValue = endBin.getValue();
            ArrayList<Integer> neighs = startBin.getNeighbors();
            if (!neighs.contains(endBinValue) && !start.equals(end)) {
                System.out.println("============================= " + connection.toString());
                String[] names = {""};
                int[] signs = {0};
                State newState = new State(states.size(), new OperatorElement(names, signs));
                states.add(newState);
                connections.set(i, new Connection(start, newState, connection.getConnection(), connection.getTransitionFlags()));
                connections.add(i + 1, new Connection(newState, end, new ArrayList<ConditionalElement>(), new HashMap<ConditionalElement, Boolean>()));
                flag = true;
                break;
            }
        }
        if (flag) {
            clearCoding();
            neighborCoding();
        }

    }

    protected void clearCoding() {
        for (int i = 0; i < statesCount(); i++) {
            states.get(i).setCode(null);
        }
    }

    public void addEmptyStates() {
        for (int i = 0; i < connectionsCount(); i++) {
            Connection connection = connections.get(i);
            State start = connection.getStartState();
            State end = connection.getEndState();
            if (start.equals(end)) {
                String[] names = {""};
                int[] signs = {0};
                State newState = new State(states.size(), new OperatorElement(names, signs));
                states.add(newState);
                connections.set(i, new Connection(start, newState, connection.getConnection(), connection.getTransitionFlags()));
                connections.add(i + 1, new Connection(newState, end, new ArrayList<ConditionalElement>(), new HashMap<ConditionalElement, Boolean>()));
            }
        }
    }

    boolean flag = true;

    public int selectNeighborCode(int stateNumber) {
        int res = -1;
        BinaryNumber binaryNumber = states.get(stateNumber).getCode();
        ArrayList<Integer> stateNeighbors = getStatesConnectedWith(stateNumber);
        int size = stateNeighbors.size();
        ArrayList<Integer> forRemove = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            if (states.get(stateNeighbors.get(i)).hasCode() == false) {
                forRemove.add(stateNeighbors.get(i));
            }
        }
        for (int i = 0; i < forRemove.size(); i++) {
            stateNeighbors.remove(forRemove.get(i));
        }
        ArrayList<ArrayList<Integer>> codesOfNeighbors = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < stateNeighbors.size(); i++) {
            int stateNum = stateNeighbors.get(i);
            State curState = states.get(stateNum);
            System.out.println("Current state :: " + stateNum);
            BinaryNumber curBin = curState.getCode();
            ArrayList<Integer> neighs = curBin.getNeighbors();
            codesOfNeighbors.add(neighs);
        }
        size = codesOfNeighbors.size();
        ArrayList<ArrayList<Integer>> forRem = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < size; i++) {
            forRem.add(new ArrayList<Integer>());
            for (int j = 0; j < codesOfNeighbors.get(i).size(); j++) {
                for (int k = 0; k < statesCount(); k++) {
                    if (states.get(k).hasCode()) {
                        if (states.get(k).getCode().getValue() == codesOfNeighbors.get(i).get(j)) {
                            forRem.get(i).add(j);
                        }
                    }
                }
            }
        }
        System.out.println();
        for (int i = 0; i < forRem.size(); i++) {
            for (int j = 0; j < forRem.get(i).size(); j++) {
                int buf = forRem.get(i).get(j);
                codesOfNeighbors.get(i).remove(buf - j);
            }
        }
        if (stateNeighbors.size() == 1) {
            for (int i = codesOfNeighbors.get(0).size() - 1; i >= 0; i--) {
                if (flag)
                    if (codesOfNeighbors.get(0).get(i) > states.get(stateNeighbors.get(0)).getCode().getValue()) {
                        res = codesOfNeighbors.get(0).get(i);
                        flag = !flag;
                        break;
                    }
                else
                    if (codesOfNeighbors.get(0).get(i) < states.get(stateNeighbors.get(0)).getCode().getValue()) {
                        res = codesOfNeighbors.get(0).get(i);
                        flag = !flag;
                        break;
                    }
            }
            if (res == -1 && codesOfNeighbors.get(0).size() > 0) {
                res = codesOfNeighbors.get(0).get(0);
            }
        } else {
            for (int i = 0; i < codesOfNeighbors.get(0).size(); i++) {
                boolean flag = true;
                for (int j = 1; j < codesOfNeighbors.size(); j++) {
                    if (!codesOfNeighbors.get(j).contains(codesOfNeighbors.get(0).get(i))) {
                        flag = false;
                    }
                }
                if (flag) {
                    res = codesOfNeighbors.get(0).get(i);
                    return res;
                }
            }
            if (res == -1)
                res = codesOfNeighbors.get(0).get(0);

        }
        return res;
    }

    public ArrayList<Integer> getStatesConnectedWith(int stateNumber) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < connectionsCount(); i++) {
            Connection connection = connections.get(i);
            int start = connection.getStartState().getNumber();
            int end = connection.getEndState().getNumber();
            if (start == stateNumber) {
                res.add(end);
            } else if (end == stateNumber) {
                res.add(start);
            }
        }
        return res;
    }

    public int getCodeSize() {
        int[] counts = new int[states.size()];
        for (int i = 0; i < connectionsCount(); i++) {
            Connection connection = connections.get(i);
            if (connection.getStartState().equals(connection.getEndState()))
                counts[connection.getStartState().getNumber()]--;
            for (int j = 0; j < connectionsCount(); j++) {
                Connection con = connections.get(j);
                if (con.getStartState().equals(connection.getEndState())) {
                    if (con.getEndState().equals(connection.getStartState())) {
                        counts[connection.getStartState().getNumber()]--;
                    }
                }
            }
            counts[connection.getStartState().getNumber()]++;
            counts[connection.getEndState().getNumber()]++;
        }
        int max = counts[0];
        for (int i = 0; i < counts.length; i++) {
            if (max < counts[i])
                max = counts[i];
        }
        if (max < ((int) (Math.log(states.size()) / Math.log(2)) + 1))
            max = (int) (Math.log(states.size()) / Math.log(2)) + 1;
        return max;
    }

    /**
     * Method for generating connections from lsa to graph.
     */
    public void generateConnections() {
        for (int i = 0; i < operators.size() - 1; i++) {
            connections.addAll(genConnections(operators.get(i), i, new Connection(states.get(i))));
        }
    }

    /**
     * Method for getting all connections from start element.
     * @param element - Start element.
     * @param stateNumber - Number of state.
     * @param connection - Connection.
     * @return - Bunch of connections.
     */
    public ArrayList<Connection> genConnections(Element element, int stateNumber, Connection connection) {
        boolean flag = true;
        Algorithm algorithm = FileModel.getInstance().getAlgorithm();
        ArrayList<Connection> res = new ArrayList<Connection>();
        Element nextElement = algorithm.getNext(element);
        if (nextElement.getClass().equals(UpArrowElement.class)) {
            res.addAll(genConnections(algorithm.lookFor(DownArrowElement.class, nextElement.getIndexes()[0]), stateNumber, connection));
            flag = false;
        } else if (nextElement.getClass().equals(DownArrowElement.class)) {
            res.addAll(genConnections(nextElement, stateNumber, connection));
            flag = false;
        } else if (nextElement.getClass().equals(OperatorElement.class)) {
            int end = operators.indexOf(nextElement);
            connection.setEndState(states.get(end));
        } else if (nextElement.getClass().equals(EndElement.class)) {
            int end = operators.indexOf(nextElement);
            connection.setEndState(states.get(end));
        } else if (nextElement.getClass().equals(ConditionalElement.class)) {
            int idx = algorithm.getNext(nextElement).getIndexes()[0];
            Element el = algorithm.lookFor(DownArrowElement.class, idx);

            Connection buf = connection.clone();
            Connection buf2 = connection.clone();
            buf.addConnection((ConditionalElement)nextElement, true);
            res.addAll(genConnections(el, stateNumber, buf));

            buf2.addConnection((ConditionalElement)nextElement, false);
            el = algorithm.getNext(nextElement);
            res.addAll(genConnections(el, stateNumber, buf2));

            flag = false;
        }
        if (flag) {
            res.add(connection);
        }
        return res;
    }

    public State getState(int idx) {
        return states.get(idx);
    }

    public ArrayList<Element> getOperators() {
        return operators;
    }

    public ArrayList<ConditionalElement> getConditions() {
        return conditionals;
    }

    public int statesCount() {
        return states.size();
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    /**
     * Return all connections for start state.
     * @param start - Start state.
     * @return - Bunch of connections.
     */
    public ArrayList<Connection> getConnectionsByStart(State start) {
        ArrayList<Connection> res = new ArrayList<Connection>();
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).isStart(start)) {
                res.add(connections.get(i));
            }
        }
        return res;
    }

    public int getStateIndex(State state) {
        return states.indexOf(state);
    }

    public void printConnections() {
        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i).toString());
        }
    }

    public int getStatesCount() {
        return states.size();
    }

    public int connectionsCount() {
        return connections.size();
    }

    public GraphTables getTables() {
        return tables;
    }


}
