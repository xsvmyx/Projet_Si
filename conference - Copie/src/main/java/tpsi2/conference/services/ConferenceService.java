package tpsi2.conference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.repositories.ConferenceRepository;

import java.util.List;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public List<Conference> getConferences(){
        return conferenceRepository.findAll();

    }
}
