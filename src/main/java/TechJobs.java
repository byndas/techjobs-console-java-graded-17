import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TechJobs {

    static Scanner in = new Scanner(System.in);
//  presents menus depending on user choices
    public static void main (String[] args) {
/*
main:
    asks user which column to apply list or search
    if search, asks for search term
    performs request on JobData class public method & displays results
    repeats until user ends program

    simulates query to external source:
        asks method for non-Java sourced data
        method then parses, filters, presents formatted data
*/
        // initializes field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // creates top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // allows user to search until they quit program
        while (true) {

            String actionChoice = getUserSelection(
                    "View jobs by (type 'x' to quit):",
                    actionChoices
            );

            if ( actionChoice == null ) { break; }

            else if ( actionChoice.equals("list") ) {

                String columnChoice =
                        getUserSelection("List", columnChoices);

                if ( columnChoice.equals("all") ) { printJobs(JobData.findAll()); }

                else {
                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println(
                            "\n*** All "
                            + columnChoices.get(columnChoice)
                            + " Values ***"
                    );

                    // prints list of skills, employers, etc
                    for (String item : results) { System.out.println(item); }
                }
            }
            else { // user chooses "search"
                // user chooses to search by skill or employer
                String searchField = getUserSelection(
                        "Search by:", columnChoices
                );

                // user chooses search term
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if ( searchField.equals("all") ) {
                    printJobs(JobData.findByValue(searchTerm));
                }
                else {
                    printJobs(
                        JobData.findByColumnAndValue(searchField, searchTerm)
                    );
                }
            }
        }
    }

//  displays choice menu, returns chosen item key from choices Dictionary
    private static String getUserSelection(
            String menuHeader,
            HashMap<String, String> choices
    ) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // puts choices in order, pairing each with integer
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            System.out.println("\n" + menuHeader);
            // prints available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println(
                    "" + j + " - " + choices.get(choiceKeys[j])
                );
            }
            if ( in.hasNextInt() ) {
                choiceIdx = in.nextInt();
                in.nextLine();
            }
            else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");

                if (shouldQuit) { return null; }
            }
            // validates user input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            }
            else { validChoice = true; }
        }
        while(!validChoice);
        return choiceKeys[choiceIdx];
    }

    // prints formatted job list
    private static void printJobs(
            ArrayList<HashMap<String,
            String>> someJobs
    ) {

//  iterate over arrayList & grab all 98 hashMap job entries
//  use two for-loops --> arrayList then hashMap
//  for-each Entry --> copy/paste from book or studio
//  testPrintJobs.txt shows format to print all entries:
    //  *****
    //  position type: -- /n name: -- /n employer: -- /n
    //  location: -- /n core competency: --
    //  *****
//  position type is already in the hashMap, no need to type that out
//  focus first on listing all jobs in hashMap, then handle search term
        System.out.println("printJobs is not implemented yet");
    }
}
