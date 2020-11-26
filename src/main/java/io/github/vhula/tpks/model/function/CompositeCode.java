package io.github.vhula.tpks.model.function;

/**
 * Class which represent composite code.
 * This code can consists of other codes.
 */
public class CompositeCode extends BasicCode {

    public String getStringRepresentation() {
        String res = "";
        for (int i = 0; i < inversions; i++) {
            res += "!";
        }
        res += "(";
        for (BasicCode code : children) {
            res += code.getStringRepresentation() + " & ";
        }
        res = res.substring(0, res.length() - 3);
        res += ")";
        return res;
    }

}
