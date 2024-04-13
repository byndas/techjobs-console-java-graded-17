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

        // TODO - implement this method
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
