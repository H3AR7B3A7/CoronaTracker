package be.dog.d.steven.corona_tracker.services;

import be.dog.d.steven.corona_tracker.model.LocationConfirmedStats;
import be.dog.d.steven.corona_tracker.model.LocationDeathStats;
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

    public List<LocationConfirmedStats> getAllConfirmedDataList() {
        return allConfirmedDataList;
    }

    public List<LocationDeathStats> getAllDeathDataList() {
        return allDeathDataList;
    }

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
            locationConfirmedStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
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
            locationDeathStats.setState(record.get("Province/State"));
            locationDeathStats.setRegion(record.get("Country/Region"));
            locationDeathStats.setLatestTotalDeath(Integer.parseInt(record.get(record.size()-1)));
            // System.out.println(locationDeathStats);
            newDeathDataList.add(locationDeathStats);
        }
        this.allDeathDataList = newDeathDataList;
    }
}
