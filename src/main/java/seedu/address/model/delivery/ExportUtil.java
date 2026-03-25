package seedu.address.model.delivery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
<<<<<<< Updated upstream
import java.util.Map;
=======
>>>>>>> Stashed changes

import seedu.address.model.person.Person;

/**
 * Utility class for exporting delivery assignments to a formatted text file.
 * <p>
 * Provides methods to write all current driver-to-person assignments into
 * a human-readable file, grouping subscribers under each driver.
 */
public class ExportUtil {

    /**
     * Exports delivery assignments to a nicely formatted text file.
     * <p>
     * Each driver is printed as a heading, followed by their assigned subscribers.
     * <p>
     * @param assignments the map of drivers to assigned persons
     * @param filePath the file path to write the output to
     * @throws IOException if file writing fails
     */
<<<<<<< Updated upstream
    public static void exportAssignmentsFormatted(Map<Driver, List<Person>> assignments, String filePath)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Driver driver : assignments.keySet()) {
=======
    public static void exportAssignmentsFormatted(DeliveryAssignmentHashMap assignments, String filePath)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Driver driver : assignments.getDriversKeySet()) {
>>>>>>> Stashed changes
                // Driver header
                writer.write(driver.getName().toString() + " - " + driver.getPhone().toString());
                writer.newLine();
                writer.write("--------------------");
                writer.newLine();

<<<<<<< Updated upstream
                List<Person> people = assignments.get(driver);
=======
                List<Person> people = assignments.getDeliveryListFor(driver);
>>>>>>> Stashed changes
                for (Person p : people) {
                    String line = String.format("%s | %s | %s | %s | Boxes: %d",
                            p.getName(),
                            p.getPhone(),
                            p.getEmail(),
                            p.getAddress(),
                            p.getBoxes().size());
                    writer.write(line);
                    writer.newLine();
                }

                writer.newLine(); // space between drivers
            }
        }
    }
}
