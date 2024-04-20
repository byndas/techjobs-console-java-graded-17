import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList <HashMap <String, String>> allJobs;
//  returns ArrayList of jobs from column(s) matching search term, no duplicates_
    public static ArrayList <String>
    findAll(String field) {
//      param field is a column_
        loadData(); // loads data, if not already loaded_
//      storage for job entries that match search term
        ArrayList <String> values = new ArrayList<>();
//      adds jobs containing search term to values ArrayList
        for (HashMap <String, String> row : allJobs) {
            String aValue = row.get(field);
            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }
        return values;
    }
//  returns all job entries
    public static ArrayList <HashMap <String, String>>
    findAll() {
        loadData(); // loads data, if not already loaded_

//      BONUS #2: cloning allJobs ArrayList
//          to clone any singular object data type,
//              new DataType(originalDataTypeInstance)
        ArrayList <HashMap <String, String>> allJobsClone = new ArrayList<>();

        for( HashMap<String, String> job : allJobs ) {
            HashMap<String, String> jobClone = new HashMap<>(job);
            allJobsClone.add(jobClone);
        }

        return allJobsClone;
    }
//  returns ArrayList of all key/value jobs containing search term_
    public static ArrayList <HashMap <String, String>>
    findByColumnAndValue(String column, String value) {
//      param value is the search term_
        loadData(); // loads data, if not already loaded_
        ArrayList <HashMap <String, String> > jobs = new ArrayList<>();
        for (HashMap <String, String> row : allJobs) {
            String aValue = row.get(column);
//          TASK 3: MAKE SEARCH METHODS CASE-INSENSITIVE
            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }
        return jobs;
    }
//  TASK TWO:
//      searches all columns for search term value, omits duplicate jobs_
    public static ArrayList <HashMap <String, String>>
    findByValue(String searchTerm) {
        loadData(); // loads data, if not already loaded_

        ArrayList <HashMap <String, String>> matchingJobs = new ArrayList<>();

        String searchTermLowerCase = searchTerm.toLowerCase();

        for (HashMap <String, String> job : allJobs) {
            for (String item : job.values()) {
//              TASK 3: MAKE SEARCH METHODS CASE-INSENSITIVE
                if (item.toLowerCase().contains(searchTermLowerCase)) {
                    matchingJobs.add(job);
                    break; // next job object, prevents adding duplicate jobs
                }
            }
        }
        return matchingJobs;
    }
//  loads & stores CSV file data into allJobs ArrayList_
    private static void loadData() {
        if (isDataLoaded) { return; } // only loads data once_
        try { // opens CSV file, sets up getting column-header info & records_
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            int numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            for (CSVRecord record : records) { // formats records_
                HashMap <String, String> newJob = new HashMap<>();
                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }
                allJobs.add(newJob);
            }
            isDataLoaded = true; // prevents re-loading data_
        }
        catch (IOException e) { // e is instance of IOException
            System.out.println("Failed to load job data");
//            intelliJ warns logging not robust enough
//            e.printStackTrace(); // for debugging error
        }
    }
}