package io.github.vhula.tpks.model.graph;

import io.github.vhula.tpks.model.ConditionalElement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class which represents connection between two states.
 */
public class Connection {
    /**
     * Start state.
     */
    private State fromState;
    /**
     * End state.
     */
    private State toState;
    /**
     * Connection between states.
     */
    private ArrayList<ConditionalElement> connection = new ArrayList<ConditionalElement>();
    /**
     * Transition of connection.
     */
    private HashMap<ConditionalElement, Boolean> transitionFlags = new HashMap<ConditionalElement, Boolean>();

    public Connection(State from, State to,
                      ArrayList<ConditionalElement> connection,
                      HashMap<ConditionalElement, Boolean> transitionFlags) {
        this.connection = connection;
        this.fromState = from;
        this.toState = to;
        this.transitionFlags = transitionFlags;
    }

    public Connection(State fromState) {
        this.fromState = fromState;
    }

    /**
     * Returns start state of connection.
     * @return - Start state.
     */
    public State getStartState() {
        return fromState;
    }

    public boolean isStart(State state) {
        if (state == fromState)
            return true;
        return false;
    }

    /**
     * Returns numbers of conditions. More then zero if true condition, otherwise below then zero.
     * @return
     */
    public int[] getConnectionsNumbers() {
        int[] res = new int[connection.size()];
        for (int i = 0; i < connection.size(); i++) {
            if (transitionFlags.get(connection.get(i)) == true) {
                res[i] = connection.get(i).getIndexes()[0];
            } else {
                res[i] = -connection.get(i).getIndexes()[0];
            }
        }
        return res;
    }

    public State getEndState() {
        return toState;
    }

    public ArrayList<ConditionalElement> getConnection() {
        return connection;
    }

    public HashMap<ConditionalElement, Boolean> getTransitionFlags() {
        return transitionFlags;
    }

    /**
     * Adds condition to connection.
     * @param element - Condition.
     * @param flag - True if condition is true, false if condition is false.
     */
    public void addConnection(ConditionalElement element, boolean flag) {
        connection.add(element);
        transitionFlags.put(element, flag);
    }

    public void setEndState(State endState) {
        this.toState = endState;
    }

    /**
     * Returns clone of this connection.
     * @return - Clone.
     */
    public Connection clone() {
        ArrayList<ConditionalElement> connect = (ArrayList<ConditionalElement>)connection.clone();
        HashMap<ConditionalElement, Boolean> bufFlags = new HashMap<ConditionalElement, Boolean>();
        for (int i = 0; i < connect.size(); i++) {
            bufFlags.put(connect.get(i), transitionFlags.get(connection.get(i)));
        }
        Connection res = new Connection(fromState, toState, connect, bufFlags);
        return res;
    }

    public String toString() {
        String res = fromState.toString() + " | ";
        for (int i = 0; i < connection.size(); i++) {
            if (transitionFlags.get(connection.get(i)) == true) {
                res += connection.get(i).toString();
            } else {
                res += "not" + connection.get(i).toString();
            }
            res += " ";
        }
        res += " | " + toState.toString();
        return res;
    }

    public String toStringConnection() {
        String res = "";
        for (int i = 0; i < connection.size(); i++) {
            if (transitionFlags.get(connection.get(i)) == true) {
                res += connection.get(i).toString();
            } else {
                res += "not" + connection.get(i).toString();
            }
            res += " ";
        }
        return res;
    }



}
