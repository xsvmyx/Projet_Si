package tpsi2.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.services.ConferenceService;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping
    public List<Conference> getConferences(){
        return conferenceService.getConferences();
    }

}
