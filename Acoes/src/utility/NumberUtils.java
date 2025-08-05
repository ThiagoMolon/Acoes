package utility;

import javax.swing.JFormattedTextField;

public class NumberUtils {
    public static double getDoubleFromFormattedField(JFormattedTextField field) {
        if (field.getValue() != null) {
            return ((Number) field.getValue()).doubleValue();
        }

        String text = field.getText()
            .replace("R$", "")
            .replace("%", "")
            .replace(",", ".")
            .trim();

        if (!text.isEmpty()) {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }
}