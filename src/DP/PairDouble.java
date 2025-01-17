package DP;

public class PairDouble {
    protected double first;
    protected double second;

    public PairDouble(double first, double second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        long firstBits = Double.doubleToLongBits(first);
        long secondBits = Double.doubleToLongBits(second);
        return (int) (31 * (firstBits ^ (firstBits >>> 32)) + (secondBits ^ (secondBits >>> 32)));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PairDouble) {
            PairDouble otherPair = (PairDouble) other;
            return Double.compare(this.first, otherPair.first) == 0 &&
                    Double.compare(this.second, otherPair.second) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
