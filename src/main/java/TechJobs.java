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
    if search selected, asks for search term
    performs request on JobData class public method & displays results
    repeats until user ends program

    simulates query to external source:
        asks method for non-Java sourced data
        method then parses, filters, presents formatted data
*/

//      next two hashMaps contain menu info to display

//      initializes field map with key/name pairs
        HashMap <String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // creates top-level menu options
        HashMap <String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

//      allows user to search until they quit program
        while (true) {

            String actionChoice = getUserSelection(
                    "View jobs by (type 'x' to quit):",
                    actionChoices
            );

            if ( actionChoice == null ) { break; }

//          user chooses LIST
            else if ( actionChoice.equals("list") ) {

                String columnChoice =
                        getUserSelection("List", columnChoices);

                if ( columnChoice.equals("all") ) { printJobs(JobData.findAll()); }

                else {
                    ArrayList <String> results = JobData.findAll(columnChoice);

                    System.out.println(
                            "\n*** All "
                            + columnChoices.get(columnChoice)
                            + " Values ***"
                    );

//                  prints list of all position types or employers or locations or skills
                    for (String item : results) { System.out.println(item); }
                }
            }

            else { // user chooses SEARCH
                String searchField = getUserSelection(
                        "Search by:", columnChoices
                );

//              user chooses search term
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if ( searchField.equals("all") ) {
                    printJobs(JobData.findByValue(searchTerm));
                }
                else { // user chooses search menu option other than "all"
                    printJobs(
                        JobData.findByColumnAndValue(searchField, searchTerm)
                    );
//                    if (JobData.findByColumnAndValue(searchField, searchTerm)) {
//                        System.out.println("No Results");
//                    }
                }
            }
        }
    }

//  displays choice menu, returns chosen item key from choices Dictionary
//  actionChoice, columnChoice, & searchField call getUserSelection()
    private static String getUserSelection(
            String menuHeader,
            HashMap <String, String> choices
    ) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

//      pairs each menu option with incrementing integer
        int i = 0;

        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            System.out.println("\n" + menuHeader);
//          prints available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println(
                    j + " - " + choices.get(choiceKeys[j])
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
//          validates user input for chosen menu #
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            }
            else { validChoice = true; }
        }
        while(!validChoice);
        return choiceKeys[choiceIdx];
    }

//  prints testPrintJobs.txt formatted list of all 98 jobs
    private static void printJobs(
            ArrayList <HashMap <String, String> > someJobs
    ) {
        int count = 0;

        for (HashMap <String, String> job : someJobs) {
//          System.out.println(job);
            for (Map.Entry <String, String> item : job.entrySet()){
//              System.out.println(item);
                if (count == 0) {
                    System.out.println("*****");
                    count++;
                }
                else if (count < 6) {
                    System.out.println(
                        item.getKey() + ": " + item.getValue()
                    );
                    count++;
                }
                else {
                    System.out.println("*****" + "\n");
                    count = 0;
                }
           }
        }
    }
}

/*  TASK 3: MAKE SEARCH METHODS CASE-INSENSITIVE

        example: Watchtower Security job, line 31, CSV file
            JavaScript & Javascript must be equivalent
//////////////////////////////////////////////////////////////////////////////////
Ask yourself:

    Searching calls which methods?

    How to compare a search string with job HashMap object field values?

    How to ignore string cases during comparisons
        without altering capitalization of items in allJobs
            so that data prints as in job_data.csv?
//////////////////////////////////////////////////////////////////////////////////

    Review string methods in Data Types chapter

//////////////////////////////////////////////////////////////////////////////////

    App must:

        Print each job field during search & when listing all columns
        If no search results, display descriptive message
        Allow user to search for string across all columns
        Return case-insensitive results
        Run auto-grading tests to ensure all tests pass

    Once completed, submit assignment

//////////////////////////////////////////////////////////////////////////////////

    Bonus Missions:

    Alphabetically sort prop list results_

    Create a copy of allJobs for JobData.findAll() to return
        instead of the default allJobs static prop
            since anyone calling findAll() can alter the allJobs data_

    Study constructors in Oracle ArrayList documentation_
*/