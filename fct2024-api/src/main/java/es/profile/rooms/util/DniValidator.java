package es.profile.rooms.util;

public class DniValidator {

    public static boolean isValidDni(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }

        String dniNumbers = dni.substring(0, 8);
        char dniLetter = dni.charAt(8);

        if (!dniNumbers.matches("\\d+")) {
            return false;
        }

        return dniLetter == calculateDniLetter(dniNumbers);
    }

    private static char calculateDniLetter(String dniNumbers) {
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumber = Integer.parseInt(dniNumbers);
        int index = dniNumber % 23;
        return letters.charAt(index);
    }
}
