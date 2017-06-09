package sample;

public class Validator {

    public static boolean numberPreValidation(String numberString){
        return numberString.matches("^[1-9]+[0-9]{15}$");
    }

    public static boolean numberValidation(String numberString){

        int sum = 0;
        int temp;

        for(int i = numberString.length() - 1; i >= 0; i--){

            temp = Character.getNumericValue(numberString.charAt(i));

            if(i%2 == 0){
                temp = temp * 2;

                if(temp > 9){
                    temp -= 9;
                }
            }

                sum += temp;
        }

        return sum % 10 == 0;
    }

}
