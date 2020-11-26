package io.github.vhula.tpks.model.function;

import java.util.ArrayList;

/**
 * Class which represents boolean function.
 */
public class Function {
    /**
     * Codes in boolean function.
     */
    private ArrayList<Code> codes = new ArrayList<Code>();
    /**
     * Minimized function.
     */
    private ArrayList<Code> minimized = new ArrayList<Code>();
    /**
     * Name of function.
     */
    protected String name;
    /**
     * Size of codes in function.
     */
    private int codeSize;
    /**
     * Sign in function.
     */
    protected ArrayList<String> signs = new ArrayList<String>();

    private Function() {
    }

    public static class FunctionBuilder {

        /**
         * Codes in boolean function.
         */
        private ArrayList<Code> codes = new ArrayList<Code>();
        /**
         * Name of function.
         */
        private String name = "";
        /**
         * Size of codes in function.
         */
        private int codeSize = -1;
        /**
         * Sign in function.
         */
        private ArrayList<String> signs = new ArrayList<String>();

        /**
         * Sets codes value.
         * @param strCodes Codes values.
         */
        public FunctionBuilder codes(ArrayList<String> strCodes) {
            parseCodes(strCodes);
            return this;
        }

        /**
         * Sets name of function.
         * @param name - Name of function.
         */
        public FunctionBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets size of code.
         * @param codeSize Size of codes.
         */
        public FunctionBuilder codeSize(int codeSize) {
            this.codeSize = codeSize;
            return this;
        }

        /**
         * Sets signs of codes.
         * @param signs Signs of codes.
         */
        public FunctionBuilder signs(ArrayList<String> signs) {
            this.signs = signs;
            return this;
        }

        /**
         * Returns new function with current parameters.
         * @return Object of Function class.
         */
        public Function build() {
            try {
                checkParameters();
                Function function = new Function();
                function.codeSize = this.codeSize;
                function.name = this.name;
                function.codes = this.codes;
                function.signs = this.signs;
                return function;
            } catch (IllegalArgumentException exc) {
                exc.printStackTrace();
                return null;
            }
        }

        /**
         * Checks parameters of function.
         * @throws IllegalArgumentException If parameters illegal.
         */
        private void checkParameters () throws IllegalArgumentException {
            if(codeSize < 0) {
                throw new IllegalArgumentException("Codes Size must be > 0");
            } else if (name.equals("")) {
                throw new IllegalArgumentException("Name must be");
            } else if (codes.size() == 0) {
                throw new IllegalArgumentException("Empty function");
            } else if (signs.size() != codeSize) {
                throw new IllegalArgumentException("Wrong signs");
            }
        }

        /**
         * Parse codes from string representation.
         * @param strCodes Codes in strings.
         */
        private void parseCodes(ArrayList<String> strCodes) {
            for (String str : strCodes) {
                Code code = new Code(str);
                codes.add(code);
            }
        }
    }

    /**
     * Method for minimizing function.
     */
    public void minimize() {
        ArrayList<ArrayList<Code>> minCodes = new ArrayList<ArrayList<Code>>();
        minCodes.add(new ArrayList<Code>(codes));
        boolean flag = true;
        //agglutination
        while (flag) {
            flag = false;
            ArrayList<Code> bufferCodes = new ArrayList<Code>();
            ArrayList<Code> stickCodes = minCodes.get(minCodes.size() - 1);
            for (int i = 0; i < stickCodes.size(); i++) {
                Code code = stickCodes.get(i);
                for (int j = i + 1; j < stickCodes.size(); j++) {
                    Code stick = code.stickTogether(stickCodes.get(j));
                    if (stick != null && !isExists(bufferCodes, stick)) {
                        bufferCodes.add(stick);
                    }
                }
            }
            if (bufferCodes.size() > 0) {
                flag = true;
                minCodes.add(bufferCodes);
            }
        }
        //agglutination ends

        // acquisitions
        for (int i = 1; i < minCodes.size(); i++) {
            ArrayList<Code> bufferCodes = minCodes.get(i);
            ArrayList<Code> forRemoving = new ArrayList<Code>();
            ArrayList<Code> prevCodes = minCodes.get(i - 1);
            for (int j = 0; j < bufferCodes.size(); j++) {
                Code code = bufferCodes.get(j);
                for (int k = 0; k < prevCodes.size(); k++) {
                    if (code.isAdsorb(prevCodes.get(k))) {
                        forRemoving.add(prevCodes.get(k));
                    }
                }
            }
            for (int j = 0; j < forRemoving.size(); j++) {
                minCodes.get(i - 1).remove(forRemoving.get(j));
            }
        }
        // acquisitions ends
        for (int i = 0; i < minCodes.size(); i++) {
            for (int j = 0; j < minCodes.get(i).size(); j++) {
                minimized.add(minCodes.get(i).get(j));
            }
        }
        makeCovering();
        System.out.println(minCodes.size());
        for (int i = 0; i < minCodes.size(); i++) {
            System.out.println(minCodes.get(i).size());
            for (int j = 0; j < minCodes.get(i).size(); j++) {
                System.out.println(minCodes.get(i).get(j).toString());
            }
            System.out.println();
        }
        minCodes = null;
    }

    private void makeCovering() {
        ArrayList<Code> necessaryCodes = new ArrayList<Code>();
        ArrayList<ArrayList<Code>> necessary = new ArrayList<ArrayList<Code>>();
        ArrayList<Code> cloneCodes = new ArrayList<Code>(codes);
        ArrayList<Code> cloneMinimized = new ArrayList<Code>(minimized);
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < cloneCodes.size(); i++) {
                necessary.add(new ArrayList<Code>());
                for (int j = 0; j < cloneMinimized.size(); j++) {
                    if (cloneMinimized.get(j).isAdsorb(cloneCodes.get(i))) {
                        necessary.get(i).add(cloneMinimized.get(j));
                    }
                }
            }
            boolean isAddedNecessary = false;
            for (int i = 0; i < necessary.size(); i++) {
                if (necessary.get(i).size() == 1 && !necessaryCodes.contains(necessary.get(i).get(0))) {
                    necessaryCodes.add(necessary.get(i).get(0));
                    isAddedNecessary = true;
                }
            }
            if (!isAddedNecessary) {
                ArrayList<Integer> buf = new ArrayList<Integer>();
                for (int i = 0; i < cloneMinimized.size(); i++) {
                    buf.add(0);
                    for (int j = 0; j < cloneCodes.size(); j++) {
                        if (cloneMinimized.get(i).isAdsorb(cloneCodes.get(j))) {
                            buf.set(i, (buf.get(i) + 1));
                        }
                    }
                }
                int max = 0;
                for (int i = 1; i < buf.size(); i++) {
                    if (buf.get(max) < buf.get(i)) {
                        max = i;
                    }
                }
                necessaryCodes.add(cloneMinimized.get(max));
            }
            ArrayList<Code> forRem = new ArrayList<Code>();
            ArrayList<Code> forRemMin = new ArrayList<Code>();
            for (int i = 0; i < necessaryCodes.size(); i++) {
                for (int j = 0; j < cloneCodes.size(); j++) {
                    if (necessaryCodes.get(i).isAdsorb(cloneCodes.get(j)) && !forRem.contains(cloneCodes.get(j))) {
                        forRem.add(cloneCodes.get(j));
                    }
                    if (necessaryCodes.get(i).isAdsorb(cloneCodes.get(j)) && !forRemMin.contains(necessaryCodes.get(i))) {
                        forRemMin.add(necessaryCodes.get(i));
                    }
                }
            }
            for (Code c : forRem) {
                cloneCodes.remove(c);
            }
            for (Code c : forRemMin) {
                cloneMinimized.remove(c);
            }
            if (cloneMinimized.size() == 0 || cloneCodes.size() == 0) {
                flag = false;
            }
        }
        minimized = new ArrayList<Code>(necessaryCodes);
        System.out.println();
    }

    public String getElementCountMinimization() {
        String res = "";
        res += "Count of elements before minimization: " + codes.size() + "\n";
        res += "Count of elements after minimization: " + minimized.size() + "\n";
        return res;
    }

    public String getInputsCountMinimization() {
        String res = "";
        int inputsBefore = 0;
        for (Code code : codes) {
            inputsBefore += code.getInputsCount();
        }
        int inputsAfter = 0;
        for (Code code : minimized) {
            inputsAfter += code.getInputsCount();
        }
        res += "Count of inputs before minimization: " + inputsBefore + "\n";
        res += "Count of inputs after minimization: " + inputsAfter + "\n";
        return res;
    }

    public String criticalPathLength() {
        String res = "";
        res += "Length of critical path: " + (minimized.size() > 1 ? 2 : 1) + "\n";
        return res;
    }

    private boolean isExists(ArrayList<Code> checkCodes, Code checkCode) {
        for (Code cod : checkCodes) {
            if (cod.equals(checkCode)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getCodesString() {
        String res = "";
        for (Code code : codes) {
            res += code.toString();
            res += " or ";
        }
        res = res.substring(0, res.length() - 4);
        return res;
    }

    public String getMinimizedString() {
        String res = "";
        for (Code code : minimized) {
            res += code.toString();
            res += " or ";
        }
        res = res.substring(0, res.length() - 4);
        return res;
    }

    public String toStringMinimized() {
        String res = name + "=";
        for (Code code : minimized) {
            for (int i = 0; i < codeSize; i++) {
                int num = code.getNumberAt(i);
                switch (num) {
                    case -1:
                        break;
                    case 0:
                        res += (i == 0 || res.equals(name + "=") ? "!" + signs.get(i) : "& " + "!" + signs.get(i))  + " ";
                        break;
                    case 1:
                        res += (i == 0  || res.equals(name + "=") ? signs.get(i) : "& " + signs.get(i))  + " ";
                        break;
                }
            }
            res += ( !code.equals(minimized.get(minimized.size() - 1)) ? "  OR  " : "");
        }
        return res;
    }

    public String toString() {
        String res = name + "=";
        for (Code code : codes) {
            for (int i = 0; i < codeSize; i++) {
                int num = code.getNumberAt(i);
                switch (num) {
                    case -1:
                        break;
                    case 0:
                        res += (i == 0 ? "!" + signs.get(i) : "& " + "!" + signs.get(i))  + " ";
                        break;
                    case 1:
                        res += (i == 0 ? signs.get(i) : "& " + signs.get(i))  + " ";
                        break;
                }
            }
            res += ( !code.equals(codes.get(codes.size() - 1)) ? "  OR  " : "");
        }
        return res;
    }

    public ArrayList<Code> getMinimized() {
        return minimized;
    }

    public ArrayList<String> getActualSigns() {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < signs.size(); i++) {
            if (toStringMinimized().contains(signs.get(i))) {
                if (!res.contains(signs.get(i))) {
                    res.add(signs.get(i));
                }
            }
        }
        return res;
    }

    public String toStringActualSigns() {
        ArrayList<String> buf = getActualSigns();
        String res = "";
        for (String str : buf) {
            res += str + " ";
        }
        return res;
    }

}
