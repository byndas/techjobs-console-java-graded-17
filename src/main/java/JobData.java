import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList <HashMap <String, String> > allJobs;

    /*
     * fetch list of all values in loaded data,
     *      without duplicates, for a given column_
     *
     * @param field --> column to retrieve values from_
     * @return --> lists all values in given field_
     */
    public static ArrayList <String> findAll(String field) {

        loadData(); // loads data, if not already loaded_

        ArrayList <String> values = new ArrayList<>();

        for (HashMap <String, String> row : allJobs) {

            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }
        return values;
    }

    public static ArrayList <HashMap <String, String> > findAll() {

        loadData(); // loads data, if not already loaded_
        return allJobs;
    }

    /*
     * returns job data search results by key/value, including search term_
     *
     * For example,
     *      searching employer "Enterprise" includes
     *          results with "Enterprise Holdings, Inc"_
     *
     * @param column --> column to be searched_
     * @param value --> field value to search for_
     * @return --> lists all jobs matching criteria_
     */
    public static ArrayList <HashMap <String, String> >
        findByColumnAndValue(String column, String value) {

        loadData(); // loads data, if not already loaded_

        ArrayList <HashMap <String, String> > jobs = new ArrayList<>();

        for (HashMap <String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }
        return jobs;
    }

    /*
     * search all columns for given term_
     *
     * @param value --> search term to look for_
     * @return --> lists all jobs with at least one field containing value_
     */
    public static ArrayList <HashMap <String, String> >
        findByValue(String value) {

        loadData(); // loads data, if not already loaded_
        // TASK 2: write & implement this method!


        return null;
    }


//  * read in data from CSV file & store in list_

    private static void loadData() {

        if (isDataLoaded) { return; } // only loads data once_

        try { // opens CSV file, set-up pull-out column-header info & records_
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            for (CSVRecord record : records) { // formats records_

                HashMap <String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }
//          flags data as loaded to avoid further loading_
            isDataLoaded = true;
        }
        catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }
}

/*  TASK 2: CREATE findByValue() METHOD
        that searches all columns for search term

findByValue() method in JobData class (line 85)
    contains none of the code needed to work,
        leave loadData() call as the first line

A few observations:

    Your code will contain no duplicate jobs

    If a listing has:
        position type: “Web - Front End”,
        name: “Front end web dev”,
            searching for “web” won't display that listing twice

    As with printJobs,
        if new column added to data,
            your code automatically searches new column too

    Do NOT call findByColumnAndValue() once for each column,
        instead utilize loops & collection methods as previously done

    Understand how findByColumnAndValue() works,
        your code will share some similarities

    Call findByValue() somewhere in main,
        searching all columns prints a message
            that helps you discern where to call findByValue(),
                re-run program to test your code
*/
