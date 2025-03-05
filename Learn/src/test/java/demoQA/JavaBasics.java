package demoQA;

public class JavaBasics {

    private char[] uppercaseLetters;
    private char[] lowercaseLetters;
    private char[] numbers;
    private int equation;


    // Constructor
    public JavaBasics() {
    	
        // Uppercase Letters (A-Z)
        uppercaseLetters = new char[26];
        for (int i = 0; i < 26; i++) {uppercaseLetters[i] = (char) ('A' + i);}
        System.out.print("Uppercase Letters: ");
        for (char letter : uppercaseLetters) {System.out.print(letter + " ");}
        System.out.println("\n✅ A-Z alphabets printed in uppercase.");
        assert uppercaseLetters.length == 26 : "Error: Uppercase array size is incorrect!";

        // Lowercase Letters (a-z)
        lowercaseLetters = new char[26];
        for (int i = 0; i < 26; i++) {lowercaseLetters[i] = (char) ('a' + i);}
        System.out.print("Lowercase Letters: ");
        for (char letter : lowercaseLetters) {System.out.print(letter + " ");}
        System.out.println("\n✅ a-z alphabets printed in lowercase.");
        assert lowercaseLetters.length == 26 : "Error: Lowercase array size is incorrect!";      

        // Numbers (0-9)
        numbers = new char[10];
        for (int i = 0; i < 10; i++) {numbers[i] = (char) ('0' + i);}
        System.out.print("Numbers: ");
        for (char num : numbers) {System.out.print(num + " ");}
        System.out.println("\n✅ Numbers 0-9 printed successfully.");
        assert numbers.length == 10 : "Error: Numbers array size is incorrect!";
        
        //Equation
        equation = 11 + 12 + 2 * 11 *12;
        int expectedValue = 11 + 12 + 2 * 11 * 12;
        System.out.println("Equation result: "+expectedValue);
		assert equation == expectedValue : "Error: Equation calculation is incorrect!";
        System.out.println("Equation verified successfully.");
    }



    
    public static void main(String[] args) {
    	new JavaBasics();
       

    }
}
