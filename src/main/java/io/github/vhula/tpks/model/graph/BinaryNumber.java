package io.github.vhula.tpks.model.graph;

import java.util.ArrayList;

/**
 *
 */
public class BinaryNumber {

    public int length;

    public int value;

    public BinaryNumber(int value, int length) {
        this.value = value;
        this.length = length;
    }

    public ArrayList<Integer> getNeighbors() {
        ArrayList<Integer> neighs = new ArrayList<Integer>();
        String binStr = toBinaryString();
        char[] bits = binStr.toCharArray();
        for (int i = 0; i < length; i++) {
            if (bits[i] == '0') {
                bits[i] = '1';
            } else if (bits[i] == '1') {
                bits[i] = '0';
            } else {
                throw new IllegalArgumentException();
            }
            String buf = bitsToString(bits);
            neighs.add(stringToNumber(buf));
            bits[i] = (bits[i] == '1') ? '0' : '1';
        }
        return neighs;
    }

    protected int stringToNumber(String binString) {
        int res = 0;
        char[] bits = binString.toCharArray();
        for (int i = 0; i < binString.length(); i++) {
            int pow = (int) Math.pow(2, i);
            int bit = (bits[bits.length - i - 1] == '1') ? 1 : 0;
            res += pow * bit;
        }
        return res;
    }

    public String bitsToString(char[] bits) {
        String res = "";
        for (int i = 0; i < bits.length; i++) {
            res += "" + bits[i];
        }
        return res;
    }

    public String toBinaryString() {
        String binStr = Integer.toBinaryString(value);
        for (int i = binStr.length(); i < length; i++) {
            binStr = "0" + binStr;
        }
        return binStr;
    }

    public String toBinaryString(int number, int size) {
        String binStr = Integer.toBinaryString(number);
        for (int i = binStr.length(); i < size; i++) {
            binStr = "0" + binStr;
        }
        return binStr;
    }

    public int getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

}
