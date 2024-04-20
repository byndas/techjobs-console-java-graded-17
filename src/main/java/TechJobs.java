import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;

public class TechJobs {
    static Scanner in = new Scanner(System.in);
//  displays menu options depending on user input
    public static void main (String[] args) {
/*  main:
        asks user to choose list or search
        if search chosen, asks user for search term
        searches JobData class public method,
            displays ArrayList of job matches
        repeats until user ends program

        simulates query to external source:
            fetches non-Java sourced data
                to then parse, filter, & display formatted data
*/
//      initializes two HashMaps with String key/name pairs
//          then populates them with menu info to display
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

//              intelliJ warns searchField can be null 
                if(columnChoice != null) {
                    if (columnChoice.equals("all")) {
                        printJobs(JobData.findAll());
                    } else {
                        ArrayList<String> results = JobData.findAll(columnChoice);

//                  BONUS #1: alphabetically sorts results via mutation
                        results.sort(Comparator.naturalOrder());

                        System.out.println(
                            "\n*** All " + columnChoices.get(columnChoice) + " Values ***"
                        );

//                  prints list of: position types, employers, locations, or skills
                        for (String item : results) {
                            System.out.println(item);
                        }
                    }
                }
            }
            else { // user chooses SEARCH
                String searchField = getUserSelection(
                        "Search by:", columnChoices
                );
//              user chooses search term
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

//              intelliJ warns searchField can be null
                if (searchField != null) {
                    ArrayList<HashMap <String, String>> searchResults;

                    if (searchField.equals("all")) {
                        searchResults = JobData.findByValue(searchTerm);
                    }
                    else { // user chooses search menu option other than "all"
                        searchResults =
                            JobData.findByColumnAndValue(searchField, searchTerm);
                    }
                    if (searchResults.isEmpty()) {
                        System.out.print("No Results");
                    }
                    else { printJobs(searchResults); }
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
        boolean validChoice = false;
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
//          validates user input for chosen menu item #
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            }
            else { validChoice = true; }
        }
        while(!validChoice);
        return choiceKeys[choiceIdx];
    }
//  TASK ONE: prints FORMATTED list of all jobs in testPrintJobs.txt
    private static void printJobs(
            ArrayList <HashMap <String, String>> someJobs
    ) {
        for (HashMap <String, String> job : someJobs) {

            System.out.println("\n" + "*****");
            
            for (Map.Entry <String, String> item : job.entrySet()){
                System.out.println(
                    item.getKey() + ": " + item.getValue()
                );
            }
            System.out.println("*****");
        }
    }
}
/*
//////////////////////////////////////////////////////////////////////////////////

Searching calls which methods?

How to compare a search term string against job HashMap object field values?

How to ignore string cases during comparisons
    without altering capitalization of items in allJobs
        so that data prints as in job_data.csv?
//////////////////////////////////////////////////////////////////////////////////

Review string methods in Data Types chapter_

//////////////////////////////////////////////////////////////////////////////////

Must:
    Print each job field during search & when listing all columns_
    If no search results, display descriptive message_
    Allow user to search for string across all columns_
    Return case-insensitive results_
    Run auto-grading tests to ensure all tests pass_

Once completed, submit assignment_

//////////////////////////////////////////////////////////////////////////////////

Bonus Missions:

Alphabetically .sort() prop list results_

Create a copy of allJobs for JobData.findAll() to return
    instead of the default allJobs static prop
        since anyone calling findAll() can alter the allJobs data_

Study constructors in Oracle ArrayList documentation_

*/