package com.example.livapp.util;

public class ConvertUtil {

        private static final String monney = " Dinars Algériens";

        private static final String[] unités = {
                "", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix",
                "Onze", "Douze", "Treize", "Quatorze", "Quinze", "Seize"
        };

        private static final String[] dizaines = {
                "", "", "Vingt", "Trente", "Quarante", "Cinquante", "Soixante"
        };

        public static String convert(double number) {
            long entier = (long) number;
            long decimal = Math.round((number - entier) * 100);

            String entierEnLettres = convertEntier(entier);
            String decimalEnLettres = (decimal > 0) ? " virgule " + convertEntier(decimal) : "";

            return entierEnLettres + decimalEnLettres + monney;
        }

        private static String convertEntier(long number) {
            if (number < 0) return "moins " + convertEntier(-number);
            if (number < 17) return unités[(int) number];
            if (number < 20) return "Dix-" + unités[(int) (number - 10)];
            if (number < 70) {
                int dizaine = (int) (number / 10);
                int unité = (int) (number % 10);
                String sep = (unité == 1 && dizaine != 8) ? " et " : (unité > 0 ? "-" : "");
                return dizaines[dizaine] + sep + (unité > 0 ? unités[unité] : "");
            }
            if (number < 80) {
                return "Soixante" + (number == 71 ? " et Onze" : "-" + convertEntier(number - 60));
            }
            if (number < 100) {
                return "Quatre-vingt" + ((number == 80) ? "s" : "-" + convertEntier(number - 80));
            }
            if (number < 200) {
                return "Cent" + ((number > 100) ? " " + convertEntier(number - 100) : "");
            }
            if (number < 1000) {
                long centaine = number / 100;
                long reste = number % 100;
                return unités[(int) centaine] + " Cent" + ((reste > 0) ? " " + convertEntier(reste) : (reste == 0 && centaine > 1 ? "s" : ""));
            }
            if (number < 2000) {
                return "Mille" + ((number > 1000) ? " " + convertEntier(number - 1000) : "");
            }
            if (number < 1000000) {
                long mille = number / 1000;
                long reste = number % 1000;
                return convertEntier(mille) + " Mille" + ((reste > 0) ? " " + convertEntier(reste) : "");
            }
            if (number < 2000000) {
                return "Un Million" + ((number > 1000000) ? " " + convertEntier(number - 1000000) : "");
            }
            if (number < 1000000000) {
                long million = number / 1000000;
                long reste = number % 1000000;
                return convertEntier(million) + " Millions" + ((reste > 0) ? " " + convertEntier(reste) : "");
            }
            if (number < 2000000000) {
                return "Un Milliard" + ((number > 1000000000) ? " " + convertEntier(number - 1000000000) : "");
            }
            if (number <= 999999999999L) {
                long milliard = number / 1000000000;
                long reste = number % 1000000000;
                return convertEntier(milliard) + " Milliards" + ((reste > 0) ? " " + convertEntier(reste) : "");
            }

            return "nombre trop grand";
        }




    }




