import java.util.*;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Caesar Cipher");

        System.out.println("Enter the String that you want to encrypt:");
        String rawString = input.nextLine();

        // length of the shift
        System.out.println("Enter the shift length (negative value allowed): ");
        int shiftLength = input.nextInt();

        // length of each group
        System.out.println("Enter the split length: ");
        int splitLength = input.nextInt();

        // Print encrypted text
        String cypherText = encryptString(rawString, shiftLength, splitLength);
        System.out.println("Result: " + cypherText);

        String decryptedText = decryptString(cypherText, shiftLength);
        System.out.println("Decrypted String: " + decryptedText);
    }

    // remove white spaces and other punctuation marks
    public static String normalizeText(String input) {
        String result = input.replaceAll("[\\s+\\.+\\,+\\:+\\;+\\'+\\\"+\\!+\\?+\\(+\\)+]", "");
        return result.toUpperCase();
    }

    // shift characters by shift length
    public static String caesarify(String input, int shift) {
        String result = "";
        int charCode = 0;
        for (int i = 0; i < input.length(); i++) {
            charCode = input.codePointAt(i) + shift;
            if (charCode > (int) 'Z') {
                charCode -= 26;
            }
            if (charCode < (int) 'A') {
                charCode += 26;
            }
            result += (char) charCode;
        }
        return result;
    }

    // split result into chunks of length size
    public static String groupify(String input, int length) {
        String result = "";
        int pointer = length;
        for (int i = 0; i < input.length() % length; i++) {
            input += "x";
        }
        for (int i = 0; i < input.length(); i++) {
            if (pointer == 0) {
                result += " ";
                pointer = length;
            }
            result += input.charAt(i);
            pointer--;
        }
        return result;
    }

    // combine above methods to encrypt data
    public static String encryptString(String input, int shift, int length) {
        String result = normalizeText(input);
        result = caesarify(result, shift);
        result = groupify(result, length);
        return result;
    }


    // revert the groupify method
    public static String ungroupify(String input) {
        return input.replaceAll("[\\s+x]", "");
    }

    // decrypt data
    public static String decryptString(String input, int shift) {
        String result = ungroupify(input);
        result = caesarify(result, -shift);
        return result;
    }
}
