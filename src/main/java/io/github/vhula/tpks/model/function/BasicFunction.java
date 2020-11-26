package io.github.vhula.tpks.model.function;

import java.util.ArrayList;

/**
 * Class which represents function.
 */
public class BasicFunction  {
    /**
     * Boolean function.
     */
    private Function booleanFunction;

    private BasicCode functionCode = new CompositeCode();

    public BasicFunction(Function booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    /**
     * Method for transforming from boolean function to basic function.
     */
    public void transform() {
        ArrayList<Code> codes = booleanFunction.getMinimized();
        for (Code code : codes) {
            int inputsCount = code.getInputsCount();
            if (inputsCount <= 4) {
                Code bufCode = new Code(code.toString());
                bufCode.addInversion();
                functionCode.addChild(bufCode);
            } else {
                int[] numbers = code.getCode();
                String str = "";
                Code bufCode = null;
                CompositeCode compositeCode = new CompositeCode();
                for (int i = 0; i < numbers.length; i++) {
                    if (i % 4 == 0 && i != 0) {
                        bufCode = new Code(str);
                        bufCode.addInversion();
                        bufCode.addInversion();
                        compositeCode.addChild(bufCode);
                        str = "";
                    }
                    str += numbers[i];
                }
                if (str.length() != 0) {
                    bufCode = new Code(str);
                    bufCode.addInversion();
                    bufCode.addInversion();
                    compositeCode.addChild(bufCode);
                }
                compositeCode.addInversion();
                functionCode.addChild(compositeCode);
            }
        }
        functionCode.addInversion();
    }

    public String getCodesString() {
        return functionCode.toString();
    }

    public String toString() {
        String res = booleanFunction.name + "=!(";
        for (int i = 0; i < functionCode.children.size(); i++) {
            BasicCode code = functionCode.children.get(i);
            if (code instanceof Code) {
                String resBuf = "";
                for (int j = 0; j < booleanFunction.signs.size(); j++) {
                     if (((Code) code).getCode()[j] == 0) {
                         resBuf += (j == 0 || resBuf.equals("") ? "!" + booleanFunction.signs.get(j) : "& " + "!" + booleanFunction.signs.get(j))  + " ";
                     } else if (((Code) code).getCode()[j] == 1) {
                         resBuf += (j == 0  || resBuf.equals("") ? booleanFunction.signs.get(j) : "& " + booleanFunction.signs.get(j))  + " ";
                     } else if (((Code) code).getCode()[j] == -1) {
                        continue;
                     }
                }
                if (!res.equals(booleanFunction.name + "=!("))
                    res += "&";
                res += "!(";
                res += resBuf;
                res += ")";

            } else {
                int signIndex = 0;
                if (!res.equals(booleanFunction.name + "=!("))
                    res += "&";
                res += "!(";
                for (int k = 0; k < code.children.size(); k++) {
                    Code codeBuf = (Code) code.children.get(k);
                    String resBuf = "";
                    for (int j = 0; j < codeBuf.getSize(); j++) {
                        if (((Code) codeBuf).getCode()[j] == 0) {
                            resBuf += (j == 0 || resBuf.equals("") ? "!" + booleanFunction.signs.get(signIndex) : "& " + "!" + booleanFunction.signs.get(signIndex))  + " ";
                        } else if (((Code) codeBuf).getCode()[j] == 1) {
                            resBuf += (j == 0  || resBuf.equals("") ? booleanFunction.signs.get(signIndex) : "& " + booleanFunction.signs.get(signIndex))  + " ";
                        } else if (((Code) codeBuf).getCode()[j] == -1) {
                            signIndex++;
                            continue;
                        }
                        signIndex++;
                    }
                    if (k != 0)
                        res += "&";
                    res += "!!(";
                    res += resBuf;
                    res += ")";
                }
                res += ")";
            }
        }
        res += ")";
        return res;
    }

    /**
     * Method for creating VHDL string of the function.
     * @return string VHDL
     */
    public String toStringVHDL() {
        String res = booleanFunction.name + " <= not (";
        for (int i = 0; i < functionCode.children.size(); i++) {
            BasicCode code = functionCode.children.get(i);
            if (code instanceof Code) {
                String resBuf = "";
                for (int j = 0; j < booleanFunction.signs.size(); j++) {
                    if (((Code) code).getCode()[j] == 0) {
                        resBuf += (j == 0 || resBuf.equals("") ? " not " + booleanFunction.signs.get(j) : " and " + " not " + booleanFunction.signs.get(j))  + " ";
                    } else if (((Code) code).getCode()[j] == 1) {
                        resBuf += (j == 0  || resBuf.equals("") ? booleanFunction.signs.get(j) : " and " + booleanFunction.signs.get(j))  + " ";
                    } else if (((Code) code).getCode()[j] == -1) {
                        continue;
                    }
                }
                if (!res.equals(booleanFunction.name + " <= not ("))
                    res += " and ";
                res += " not (";
                res += resBuf;
                res += ")";

            } else {
                int signIndex = 0;
                if (!res.equals(booleanFunction.name + " <= not ("))
                    res += " and ";
                res += " not (";
                for (int k = 0; k < code.children.size(); k++) {
                    Code codeBuf = (Code) code.children.get(k);
                    String resBuf = "";
                    for (int j = 0; j < codeBuf.getSize(); j++) {
                        if (((Code) codeBuf).getCode()[j] == 0) {
                            resBuf += (j == 0 || resBuf.equals("") ? " not " + booleanFunction.signs.get(signIndex) : " and " + " not" + booleanFunction.signs.get(signIndex))  + " ";
                        } else if (((Code) codeBuf).getCode()[j] == 1) {
                            resBuf += (j == 0  || resBuf.equals("") ? booleanFunction.signs.get(signIndex) : " and " + booleanFunction.signs.get(signIndex))  + " ";
                        } else if (((Code) codeBuf).getCode()[j] == -1) {
                            signIndex++;
                            continue;
                        }
                        signIndex++;
                    }
                    if (k != 0)
                        res += " and ";
                    res += " not not (";
                    res += resBuf;
                    res += ")";
                }
                res += ")";
            }
        }
        res += ")";
        return res;
    }

    /**
     * Return string representation of the code.
     * @return
     */
    public String getStringRepresentation() {
        return functionCode.getStringRepresentation();
    }

    /**
     * Returns count of elements in function.
     * @return
     */
    public String getElementCount() {
        String res = "";
        int count = 0;
        for (BasicCode code : functionCode.children) {
            if (code instanceof Code) {
                count++;
            } else {
                count += code.children.size();
            }
        }
        res += "Count of elements: " + count + "\n";
        return res;
    }

    /**
     * Returns amount of inputs in function
     * @return
     */
    public String getInputsCount() {
        String res = "";
        int count = 0;
        for (BasicCode code : functionCode.children) {
            if (code instanceof Code) {
                count += ((Code) code).getInputsCount();
            } else {
                for (BasicCode codebuf : code.children) {
                    count += ((Code) codebuf).getInputsCount();
                }
            }
        }
        res += "Count of inputs: " + count + "\n";
        return res;
    }

    /**
     * Return critical path length.
     * @return
     */
    public String criticalPathLength() {
        String res = "";
        res += "Length of critical path: " + (functionCode.children.size() > 1 ? 4 : 2) + "\n";
        return res;
    }

    /**
     * Returns name of function
     * @return
     */
    public String getName() {
        return booleanFunction.getName();
    }

    public ArrayList<String> getActualSigns() {
        return booleanFunction.getActualSigns();
    }

    public String toStringActualSigns() {
        return booleanFunction.toStringActualSigns();
    }

}
