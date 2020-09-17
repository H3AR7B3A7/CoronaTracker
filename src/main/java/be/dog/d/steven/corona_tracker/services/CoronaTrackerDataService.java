package be.dog.d.steven.corona_tracker.services;

import be.dog.d.steven.corona_tracker.model.LocationConfirmedStats;
import be.dog.d.steven.corona_tracker.model.LocationDeathStats;
import be.dog.d.steven.corona_tracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaTrackerDataService {

    private static String CONFIRMED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private List<LocationConfirmedStats> allConfirmedDataList = new ArrayList<>();
    private List<LocationDeathStats> allDeathDataList = new ArrayList<>();
    private List<LocationStats> allStatsDataList = new ArrayList<>();

    public List<LocationConfirmedStats> getAllConfirmedDataList() {
        return allConfirmedDataList;
    }

    public List<LocationDeathStats> getAllDeathDataList() {
        return allDeathDataList;
    }

    public List<LocationStats> getAllStatsDataList() { return allStatsDataList; }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {
        // Confirmed Cases
        List<LocationConfirmedStats> newConfirmedDataList = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CONFIRMED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> confirmedRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : confirmedRecords) {
            LocationConfirmedStats locationConfirmedStats = new LocationConfirmedStats();
            locationConfirmedStats.setState(record.get("Province/State"));
            locationConfirmedStats.setRegion(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int previousDayCases = Integer.parseInt(record.get(record.size()-2));
            locationConfirmedStats.setLatestTotalCases(latestCases);
            locationConfirmedStats.setDeltaFromPreviousCases(latestCases - previousDayCases);
            // System.out.println(locationConfirmedStats);
            newConfirmedDataList.add(locationConfirmedStats);
        }
        this.allConfirmedDataList = newConfirmedDataList;

        // Deaths
        List<LocationDeathStats> newDeathDataList = new ArrayList<>();
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder().uri(URI.create(DEATH_DATA_URL)).build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> deathRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : deathRecords) {
            LocationDeathStats locationDeathStats = new LocationDeathStats();
//            locationDeathStats.setState(record.get("Province/State"));
//            locationDeathStats.setRegion(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int previousDayCases = Integer.parseInt(record.get(record.size()-2));
            locationDeathStats.setLatestTotalDeath(latestCases);
            locationDeathStats.setDeltaFromPreviousDeaths(latestCases - previousDayCases);
            // System.out.println(locationDeathStats);
            newDeathDataList.add(locationDeathStats);
        }
        this.allDeathDataList = newDeathDataList;


        List<LocationStats> newStatsList = new ArrayList<>();
        for (int x = 0; x < newDeathDataList.size(); x++) {
            LocationStats temp = new LocationStats();
            temp.setState(newConfirmedDataList.get(x).getState());
            temp.setRegion(newConfirmedDataList.get(x).getRegion());
            temp.setDeltaFromPreviousCases(newConfirmedDataList.get(x).getDeltaFromPreviousCases());
            temp.setDeltaFromPreviousDeaths(newDeathDataList.get(x).getDeltaFromPreviousDeaths());
            temp.setLatestTotalCases(newConfirmedDataList.get(x).getLatestTotalCases());
            temp.setLatestTotalDeath(newDeathDataList.get(x).getLatestTotalDeath());
            // System.out.println(temp);
            newStatsList.add(temp);
        }
        this.allStatsDataList = newStatsList;

    }
}
