package io.github.vhula.tpks.model.graph;

import io.github.vhula.tpks.model.ConditionalElement;
import io.github.vhula.tpks.model.Element;
import io.github.vhula.tpks.model.function.BasicFunction;
import io.github.vhula.tpks.model.function.Function;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Represents table of transitions and trigger signals.
 */
public class StructureTriggerTable {
    /**
     * Headers of a table.
     */
    public String[] headers = {"Перехід", "Код ПС", "Код СП", "Логічні умови", "Керуючі сигнали", "Функції збудження тригерів"};
    /**
     * Table of transitions and trigger signals.
     */
    private Integer table[][];
    /**
     * Graph.
     */
    private Graph graph;
    /**
     * Size of code.
     */
    public int codeSize;
    /**
     * Amount of operators.
     */
    public int operatorsCount;
    /**
     * Amount of triggers.
     */
    public int triggersCount;
    /**
     * Amount of different conditions.
     */
    public int conditionsCount;
    /**
     * Operators signals.
     */
    public ArrayList<Integer> idxs;
    /**
     * Conditionals signals.
     */
    public ArrayList<Integer> conditions;
    /**
     * Functions of operators.
     */
    private ArrayList<Function> yFunctions = new ArrayList<Function>();

    private ArrayList<BasicFunction> yBasicFunctions = new ArrayList<BasicFunction>();
    /**
     * Functions of triggers.
     */
    private ArrayList<Function> tFunctions = new ArrayList<Function>();

    private ArrayList<BasicFunction> tBasicFunctions = new ArrayList<BasicFunction>();

    public StructureTriggerTable(Graph graph) {
        this.graph = graph;
    }

    /**
     * Returns table.
     * @return - table.
     */
    public Integer[][] getTable() {
        return table;
    }

    /**
     * Generates the table of transitions and triggers functions.
     */
    public void generateTable() {
        ArrayList<Connection> connections = graph.getConnections();
        ArrayList<State> states = graph.getStates();
        int tableHeight = connections.size() + 1;
        GraphTables graphTables = graph.getTables();
        codeSize = graph.getCodeSize(); //graphTables.getMatchTable().length;
        operatorsCount = graphTables.getMatchTable().length - 2;
        triggersCount = codeSize;
        ArrayList<Element> operators = graph.getOperators();

        //Operators generating
        idxs = new ArrayList<Integer>();
        for (int k = 1; k < operators.size() - 1; k++) {
            Element element = operators.get(k);
            int[] elIndxs = element.getIndexes();
            for (int m = 0; m < elIndxs.length; m++) {
                if (!idxs.contains(elIndxs[m])) {
                    idxs.add(elIndxs[m]);
                }
            }
        }
        idxs = sort(idxs);
        if (idxs.get(0) == 0) {
            idxs.remove(0);
        }

        ArrayList<ConditionalElement> conditions = graph.getConditions();//FileModel.getInstance().getAlgorithm().getConditions();
        int[][] transitionTable = graphTables.getTransitionTable();
        int maxSize = -1;
        ArrayList<Integer> conds = new ArrayList<Integer>();
        for (int i = 0; i < transitionTable.length; i++) {
            for (int k = states.size(); k < transitionTable[i].length; k++) {
                if (!conds.contains(Math.abs(transitionTable[i][k])) && transitionTable[i][k] != 0) {
                     conds.add(transitionTable[i][k]);
                }
            }
        }
        conds = sort(conds);
        this.conditions = conds;
        conditionsCount = conds.size();
        int tableWidth = 2 + codeSize * 2 + conditionsCount + operatorsCount + codeSize;
        table = new Integer[tableHeight][tableWidth];
        fillTable();
        for (int i = 2; i < 2 + codeSize; i++) {
            table[0][i] = codeSize - i + 2;
            table[0][i + codeSize] = codeSize - i + 2;
        }
        for (int i = 0; i < conditionsCount; i++) {
            table[0][i + 2 + 2 * codeSize] = conds.get(i);
        }
        for (int i = 0; i < operatorsCount; i++) {
            table[0][i + 2 + 2 * codeSize + conditionsCount] = idxs.get(i);
        }
        for (int i = 0; i < codeSize; i++) {
            table[0][i + 2 + 2 * codeSize + conditionsCount + operatorsCount] = codeSize - i;
        }
        // Generating transitions.
        int idx = 1;
        for (int i = 0; i < states.size(); i++) {
            State startState = states.get(i);
            for (int j = 0; j < connections.size(); j++) {
                Connection connection = connections.get(j);
                if (connection.isStart(startState)) {
                    State endState = connection.getEndState();
                    int[] nums = connection.getConnectionsNumbers();
                    System.out.println("num + " + i + " :: " + connection.getConnection().toString() + " -- " + nums.length);

                    //Convert nums to indexes in the table
                    int[] indexes = new int[nums.length];
                    for (int k = 0; k < nums.length; k++) {
                        indexes[k] = 2 + codeSize * 2 + Math.abs(nums[k]) - 1;
                        System.out.println(indexes[k]);
                    }

                    table[idx][0] = startState.getNumber();
                    table[idx][1] = endState.getNumber();
                    String startBinStr = startState.getCode().toBinaryString();
                    String endBinStr = endState.getCode().toBinaryString();
                    for (int k = 0; k < codeSize; k++) {
                        table[idx][k + 2] = Integer.parseInt(startBinStr.substring(k, (k + 1)));
                        table[idx][k + codeSize + 2] = Integer.parseInt(endBinStr.substring(k, (k + 1)));
                        if (table[idx][k + 2] == table[idx][k + codeSize + 2]) {
                            table[idx][table[idx].length - codeSize + k] = 0;
                        } else {
                            table[idx][table[idx].length - codeSize + k] = 1;
                        }
                    }
                    //Conditions generating
                    for (int k = 0; k < conditionsCount; k++) {
                        table[idx][2 + codeSize * 2 + k] = -1;
                    }
                    for (int k = 0; k < indexes.length; k++) {
                        table[idx][indexes[k]] = nums[k] > 0 ? 1 : 0;
                    }


                    System.out.println("size "+idxs.size());
                    for (int k = 0; k < idxs.size(); k++) {
                        if (startState.hasSignal(idxs.get(k))) {
                            table[idx][2 + codeSize * 2 + conditionsCount + idxs.get(k) - 1] = 1;
                        }
                    }
                    idx++;
                }
            }
        }
        System.out.println("Make functions");
        createFunctions();
    }

    ArrayList<String> signs;

    /**
     * Method for creating functions.
     */
    protected void createFunctions() {
        int startIdx = 2 + 2 * codeSize + conditionsCount;
        int qIdx = 2;
        int condIdx = 2 + 2 * codeSize;
        for (int i = startIdx; i < startIdx + operatorsCount; i++) {
            ArrayList<String> signs = new ArrayList<String>();
            ArrayList<String> codes = new ArrayList<String>();
            for (int j = 1; j < table.length; j++) {
                if (table[j][i] == 1) {
                    String strCode = "";
                    for (int k = qIdx; k < qIdx + codeSize; k++) {
                        strCode += table[j][k];
                        if (signs.size() < codeSize) {
                            signs.add("Q" + table[0][k]);
                        }
                    }
                    for (int k = condIdx; k < condIdx + conditionsCount; k++) {
                        strCode += table[j][k];
                        if (signs.size() < codeSize + conditionsCount) {
                            signs.add("X" + table[0][k]);
                        }
                    }
                    codes.add(strCode);
                }
            }
            this.signs = signs;
            Function f = new Function.FunctionBuilder().name("Y" + table[0][i]).codes(codes).signs(signs).codeSize(codeSize + conditionsCount).build();
            f.minimize();
            yFunctions.add(f);
            BasicFunction bf = new BasicFunction(f);
            bf.transform();
            yBasicFunctions.add(bf);
        }
        startIdx += operatorsCount;
        System.out.println();
        for (int i = startIdx; i < startIdx + triggersCount; i++) {
            ArrayList<String> signs = new ArrayList<String>();
            ArrayList<String> codes = new ArrayList<String>();
            for (int j = 1; j < table.length; j++) {
                if (table[j][i] == 1) {
                    String strCode = "";
                    for (int k = qIdx; k < qIdx + codeSize; k++) {
                        strCode += table[j][k];
                        if (signs.size() < codeSize) {
                            signs.add("Q" + table[0][k]);
                        }
                    }
                    for (int k = condIdx; k < condIdx + conditionsCount; k++) {
                        strCode += table[j][k];
                        if (signs.size() < codeSize + conditionsCount) {
                            signs.add("X" + table[0][k]);
                        }
                    }
                    codes.add(strCode);
                }
            }
            Function f = new Function.FunctionBuilder().name("T" + table[0][i]).codes(codes).signs(signs).codeSize(codeSize + conditionsCount).build();
            f.minimize();
            tFunctions.add(f);
            BasicFunction bf = new BasicFunction(f);
            bf.transform();
            tBasicFunctions.add(bf);
        }
    }

    /**
     * Filling table with zeros.
     */
    private void fillTable() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = 0;
            }
        }
    }

    /**
     * Sorts array.
     * @param array - Array for sorting.
     */
    private ArrayList<Integer> sort(ArrayList<Integer> array) {
        ArrayList<Integer> bufArray = (ArrayList<Integer>) array.clone();
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < array.size() - 1; i++) {
                if (bufArray.get(i) > bufArray.get(i + 1)) {
                    int buf = array.get(i);
                    bufArray.set(i, bufArray.get(i + 1));
                    bufArray.set(i + 1, buf);
                    flag = true;
                }
            }
        }
        return bufArray;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                res += ("" + table[i][j]);
                res += "\t";
            }
            res += "\n";
        }
        return res;
    }

    /**
     * Method for saving table.
     * @param filename - Name of file.
     */
    public void save(String filename) {
        try {
            File file = new File(filename + ".table");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(table);
            oos.flush();
            oos.close();
        } catch (IOException exc) {
            return;
        }
    }

    public void saveFunctions(String filename) {
        try {
            File file = new File(filename + ".func");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(toStringFunctions());
            bw.flush();
            bw.close();
        } catch (IOException exc) {
            return;
        }
    }

    private void parseTable() {
        System.out.println(toString());
        int buf = table[0][2];
        codeSize = 1;
        int i = 3;
        while (buf != 1) {
            codeSize++;
            buf = table[0][i];
            i++;
        }

        triggersCount = codeSize;

        operatorsCount = 0;
        for (i = table[0].length - codeSize; table[0][i] != 1; i--) {
            operatorsCount++;
        }
        conditionsCount = 1;
        for (i = table[0].length - codeSize - operatorsCount - 1; table[0][i] != 1; i--) {
            conditionsCount++;
        }
        createFunctions();
    }


    public void open(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            table = (Integer[][]) ois.readObject();
            parseTable();
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not opened!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not opened!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String toStringFunctions() {
        String res = "Transition functions:\n";
        for (int i = 0; i < yFunctions.size(); i++) {
            res += "\n";
            res += "------------------------------------------------------------------------\n";
            res += yFunctions.get(i).toString();
            res += "\nMinimized:\n";
            res += yFunctions.get(i).toStringMinimized();
            res += "\n";
            res += "Basic Function:\n";
            res += yBasicFunctions.get(i).toString() + "\n";
            res += yBasicFunctions.get(i).getElementCount();
            res += yBasicFunctions.get(i).getInputsCount();
            res += yBasicFunctions.get(i).criticalPathLength();
            res += "------------------------------------------------------------------------\n";
            res += yBasicFunctions.get(i).getStringRepresentation() + "\n";
            res += "------------------------------------------------------------------------\n";
            res += yFunctions.get(i).getElementCountMinimization();
            res += yFunctions.get(i).getInputsCountMinimization();
            res += yFunctions.get(i).criticalPathLength();
            res += "------------------------------------------------------------------------\n\n";
            res += "------------------------------------------------------------------------\n\n";
        }
        res += "========================================================================\n\n";
        res += "Triggers functions:\n";
        for (int i = 0; i < tFunctions.size(); i++) {
            res += "\n";
            res += "------------------------------------------------------------------------\n";
            res += tFunctions.get(i).toString();
            res += "\nMinimized:\n";
            res += tFunctions.get(i).toStringMinimized();
            res += "\n";
            res += "Basic Function:\n";
            res += tBasicFunctions.get(i).toString() + "\n";
            res += tBasicFunctions.get(i).getElementCount();
            res += tBasicFunctions.get(i).getInputsCount();
            res += tBasicFunctions.get(i).criticalPathLength();
            res += "------------------------------------------------------------------------\n";
            res += tBasicFunctions.get(i).getStringRepresentation() + "\n";
            res += "------------------------------------------------------------------------\n";
            res += tFunctions.get(i).getElementCountMinimization();
            res += tFunctions.get(i).getInputsCountMinimization();
            res += tFunctions.get(i).criticalPathLength();
            res += "------------------------------------------------------------------------\n\n";
            res += "------------------------------------------------------------------------\n\n";
        }
        return res;
    }

    public void saveVHDLFunction(String filename) {
        try {
            File file = new File(filename + ".vhd");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(generateSourceCode());
            bw.flush();
            bw.close();
        } catch (IOException exc) {
            return;
        }
    }

    public String generateSourceCode() {
        String result = "";
        result += "\n";
        result += "entity MyEntity is\n";
        result += "\tport (\n";
        for (String sign : signs) {
            result += "\t\t" + sign + " : in bit;\n";
        }
        result += "\n";
        for (Function func : yFunctions) {
            result += "\t\t";
            result += func.getName() + " : out bit;";
            result += "\n";
        }
        for (Function func : tFunctions) {
            result += "\t\t";
            result += func.getName() + " : out bit;";
            if (tFunctions.indexOf(func) == tFunctions.size() - 1) {
                result = result.substring(0, result.length() - 1);
            }
            result += "\n";
        }
        result += "\t);\n";
        result += "end entity MyEntity;\n\n";
        result += "architecture MyArchitecture of MyEntity is\n";
        result += "begin\n";
        int index = 0;
        for (BasicFunction bf : yBasicFunctions) {
            result += "\t--" + bf.getName() + "\n";
            result += "\tP_" + index + ": process(";
            ArrayList<String> signs = bf.getActualSigns();
            for (String sign : signs) {
                result += sign;
                if (signs.indexOf(sign) < signs.size() - 1) {
                    result += ",";
                }
            }
            result += ")\n";
            result += "\tbegin\n";
            result += "\t\t" + bf.toStringVHDL() + ";\n";
            result += "\tend process " + "P_" + index + ";\n";
            index++;
        }
        for (BasicFunction bf : tBasicFunctions) {
            result += "\t--" + bf.getName() + "\n";
            result += "\tP_" + index + ": process(";
            ArrayList<String> signs = bf.getActualSigns();
            for (String sign : signs) {
                result += sign;
                if (signs.indexOf(sign) < signs.size() - 1) {
                    result += ", ";
                }
            }
            result += ")\n";
            result += "\tbegin\n";
            result += "\t\t" + bf.toStringVHDL() + ";\n";
            result += "\tend process " + "P_" + index + ";\n";
            index++;
        }
        result += "end architecture MyArchitecture;\n";
        return result;
    }
}
