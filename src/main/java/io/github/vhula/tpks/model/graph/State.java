package io.github.vhula.tpks.model.graph;

import io.github.vhula.tpks.model.Element;

/**
 * Class which represents state in a graph.
 */
public class State {
    /**
     * Number of state.
     */
    private int number;
    /**
     * Signals in this state.
     */
    private Element signals;

    private BinaryNumber code;

    /**
     * Constructor of State.
     * @param number - Number of state.
     * @param signals - Signals.
     */
    public State(int number, Element signals) {
        setNumber(number);
        setSignals(signals);
    }

    /**
     * Method for setting number of state.
     * @param number - New number of state.
     */
    protected void setNumber(int number) {
        this.number = number;
    }

    /**
     * Mehtod for setting signals of state.
     * @param signals - New signals of state.
     */
    protected void setSignals(Element signals) {
        this.signals = signals;
    }

    /**
     * Method for getting number of state.
     * @return - Number of state.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Method for getting signals of state.
     * @return - Signals of state.
     */
    public Element getSignals() {
        return signals;
    }

    /**
     * Method for checking if current state has signal.
     * @param signal - Number of signal.
     * @return - True if has signal, otherwise - False.
     */
    public boolean hasSignal(int signal) {
        for (int i = 0; i < signals.getIndexes().length; i++) {
            if (signals.getIndexes()[i] == signal) {
                return true;
            }
        }
        return false;
    }

    public void setCode(BinaryNumber code) {
        this.code = code;
    }

    public BinaryNumber getCode() {
        return code;
    }

    public boolean hasCode() {
        if (code == null)
            return false;
        else
            return true;
    }

    public String toString() {
        String res = null;
        if (signals == null) {
            res = "Z" + number + "/" + "-" + " / " + ((code != null) ? code.toBinaryString() : "");
        } else {
            res = "Z" + number + "/" + signals.toString() + " / " + ((code != null) ? code.toBinaryString() : "");
        }
        return res;
    }

}
