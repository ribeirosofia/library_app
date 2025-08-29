package library;

public class Fees {
    public static double calculateFee(long daysLate){
        return daysLate * 2.50;
    }
}
