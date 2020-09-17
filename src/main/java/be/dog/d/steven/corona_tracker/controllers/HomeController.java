package be.dog.d.steven.corona_tracker.controllers;

import be.dog.d.steven.corona_tracker.model.LocationConfirmedStats;
import be.dog.d.steven.corona_tracker.model.LocationDeathStats;
import be.dog.d.steven.corona_tracker.model.LocationStats;
import be.dog.d.steven.corona_tracker.services.CoronaTrackerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaTrackerDataService coronaTrackerDataService;

    @GetMapping("/")
    public String home(Model model){
        /*List<LocationConfirmedStats> allConfirmedStats = coronaTrackerDataService.getAllConfirmedDataList();
        int totalConfirmed = allConfirmedStats.stream().mapToInt(confirmedStat -> confirmedStat.getLatestTotalCases()).sum();
        int totalNewConfirmed = allConfirmedStats.stream().mapToInt(confirmedStat -> confirmedStat.getDeltaFromPreviousCases()).sum();

        model.addAttribute("locationConfirmedStats",allConfirmedStats);
        model.addAttribute("totalConfirmed", totalConfirmed);
        model.addAttribute("totalNewConfirmed", totalNewConfirmed);

        List<LocationDeathStats> allDeathStats = coronaTrackerDataService.getAllDeathDataList();
        int totalDeath = allDeathStats.stream().mapToInt(deathStat -> deathStat.getLatestTotalDeath()).sum();
        int totalNewDeath = allDeathStats.stream().mapToInt(deathStat -> deathStat.getDeltaFromPreviousDeaths()).sum();

        model.addAttribute("locationDeathStats",allDeathStats);
        model.addAttribute("totalDeath", totalDeath);
        model.addAttribute("totalNewDeath", totalNewDeath);
        */
        List<LocationStats> allStats = coronaTrackerDataService.getAllStatsDataList();
        int totalConfirmed = allStats.stream().mapToInt(confirmedStat -> confirmedStat.getLatestTotalCases()).sum();
        int totalNewConfirmed = allStats.stream().mapToInt(confirmedStat -> confirmedStat.getDeltaFromPreviousCases()).sum();
        int totalDeath = allStats.stream().mapToInt(deathStat -> deathStat.getLatestTotalDeath()).sum();
        int totalNewDeath = allStats.stream().mapToInt(deathStat -> deathStat.getDeltaFromPreviousDeaths()).sum();

        model.addAttribute("locationConfirmedStats",allStats);
        model.addAttribute("totalConfirmed", totalConfirmed);
        model.addAttribute("totalNewConfirmed", totalNewConfirmed);
        model.addAttribute("totalDeath", totalDeath);
        model.addAttribute("totalNewDeath", totalNewDeath);

        return "home";
    }
}
