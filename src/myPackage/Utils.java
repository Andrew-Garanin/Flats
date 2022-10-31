package myPackage;

public class Utils {
    public static boolean isEnteredNumberExist(int enteredNumber, int arrayListSize){
        return enteredNumber >= 0 && enteredNumber < arrayListSize;
    }
}