package be.dog.d.steven.corona_tracker.controllers;

import be.dog.d.steven.corona_tracker.services.CoronaTrackerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronaTrackerDataService coronaTrackerDataService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("locationConfirmedStats",coronaTrackerDataService.getAllConfirmedDataList());
        model.addAttribute("locationDeathStats",coronaTrackerDataService.getAllDeathDataList());
        return "home";
    }
}
