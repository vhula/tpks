package io.github.vhula.tpks.model.function;

/**
 * Class which represents code from function.
 */
public class Code extends BasicCode {
    /**
     * Code from function.
     */
    private int[] code;

    public Code(String numbers) {
        parseCode(numbers);
    }

    /**
     * Parse code from string.
     * @param numbers - Code in string.
     */
    protected void parseCode(String numbers) {
        String nStr = numbers.replaceAll("-", "");
        code = new int[nStr.length()];
        int pos = 0;
        for (int i = 0; i < numbers.length(); i++) {
            String ch = "" + numbers.charAt(i);
            if (ch.equals("-")) {
                i++;
                ch += numbers.charAt(i);
            }
            code[pos] = Integer.parseInt(ch);
            if (code[pos] != 0 && code[pos] != 1 && code[pos] != -1) {
                code = null;
                throw new IllegalArgumentException("Wrong code! It must has only: -1, 0 and 1!");
            }
            pos++;
        }
    }

    /**
     * Checks if another code adsorbs with this code.
     * @param testCode - Tested code.
     * @return - True if adsorbs.
     * @throws IllegalArgumentException If codes has not equal size.
     */
    public boolean isAdsorb(Code testCode) throws IllegalArgumentException{
        if (testCode.getSize() == code.length) {
            for (int i = 0; i < code.length; i++) {
                if (code[i] != -1) {
                    if (this.getNumberAt(i) != testCode.getNumberAt(i)) {
                        return false;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Codes must have equal size.");
        }
        return true;
    }

    /**
     * Method for sticking together two codes of function.
     * @param stickCode - Code for sticking with this code.
     * @return Result of stiked codes.
     * @throws IllegalArgumentException If codes has not equal size.
     */
    public Code stickTogether(Code stickCode) throws IllegalArgumentException{
        Code resCode = new Code(toString());
        boolean flag = true;
        if (stickCode.getSize() == code.length) {
            for (int i = 0; i < code.length; i++) {
                if (this.getNumberAt(i) == stickCode.getNumberAt(i)) {
                    resCode.setNumberAt(getNumberAt(i), i);
                } else {
                    if (flag) {
                        flag = false;
                        resCode.setNumberAt(-1, i);
                    } else {
                        return null;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Codes must have equal size.");
        }
        return resCode;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < code.length; i++) {
            res += code[i];
        }
        return res;
    }

    public String getString() {
        String res = "";
        for (int i = 0; i < inversions; i++) {
            res += "!";
        }
        res += "(";
        for (int i = 0; i < code.length; i++) {
            res += code[i] + " ";
        }
        res = res.substring(0, res.length() - 1);
        res += ")";
        return res;
    }

    public String getStringRepresentation() {
        return getString();
    }

    /**
     * Checks if number is a 1, 0 or -1.
     * @param num - Number to check.
     * @return - True if 1, 0 or -1. Otherwise false.
     */
    protected boolean isNumber(int num) {
        if (num == 0 || num == 1 || num == -1) {
            return true;
        }
        return false;
    }

    /**
     * Sets number at position.
     * @param num - Number.
     * @param pos - Position.
     */
    public void setNumberAt(int num, int pos) {
        if (pos >= code.length || pos < 0 || !isNumber(num)) {
            throw new IllegalArgumentException("Code.setNumberAt()");
        } else {
            code[pos] = num;
        }
    }

    /**
     * Returns number at position.
     * @param pos - Position.
     * @return - Number.
     */
    public int getNumberAt(int pos) {
        if (pos >= code.length || pos < 0) {
            throw new IllegalArgumentException("Code.getNumberAt()");
        } else {
            return code[pos];
        }
    }

    /**
     * Returns code.
     * @return - Code.
     */
    public int[] getCode() {
        return code;
    }

    /**
     * Returns size of code.
     * @return - Size.
     */
    public int getSize() {
        return code.length;
    }

    /**
     * Returns count of inputs.
     * @return Count of inputs.
     */
    public int getInputsCount() {
        int count = 0;
        for (int el : code) {
            if (el != -1) {
                count++;
            }
        }
        return count;
    }

    public boolean equals(Object obj) {
        Code eqCode = (Code) obj;
        int[] eqIntCode = eqCode.getCode();
        if (eqIntCode.length != code.length) {
            return false;
        }
        for (int i = 0; i < eqIntCode.length; i++) {
            if (eqIntCode[i] != code[i]) {
                return false;
            }
        }
        return true;
    }

    public void addChild(BasicCode leftChild) {
        throw new IllegalArgumentException("Leaf doesn't have children.");
    }

    public boolean removeChild(BasicCode child) {
        return false;
    }

    public BasicCode removeChild(int index) {
        return null;
    }

}
