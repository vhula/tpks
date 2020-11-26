package io.github.vhula.tpks.model.function;

import java.util.ArrayList;

/**
 * Class which represents code of function.
 */
public abstract class BasicCode {

    protected BasicCode parent = null;

    protected ArrayList<BasicCode> children = new ArrayList<BasicCode>();

    protected int inversions;

    /**
     * Method for adding child code to the code.
     * @param child - child code
     */
    public void addChild(BasicCode child) {
        children.add(child);
    }

    /**
     * Method for removing child from code.
     * @param child child code
     * @return <tt>true</tt> if successfully deleted.
     */
    public boolean removeChild(BasicCode child) {
        return children.remove(child);
    }

    /**
     * Method for removing child from code.
     * @param index index of child code
     * @return Link to the deleted child
     */
    public BasicCode removeChild(int index) {
        return children.remove(index);
    }

    public void setParent(BasicCode parent) {
        this.parent = parent;
    }

    public BasicCode getParent() {
        return parent;
    }

    /**
     * Add inversion to the code.
     */
    public void addInversion() {
        inversions++;
    }

    /**
     * Remove inversion from the code.
     */
    public void removeInversion() {
        if (inversions > 0) {
            inversions--;
        } else {
            throw new IllegalArgumentException("There is no inversions");
        }
    }

    /**
     * @return String representation of the code.
     */
    public abstract String getStringRepresentation();

    public String toString() {
        return getStringRepresentation();
    }



}
