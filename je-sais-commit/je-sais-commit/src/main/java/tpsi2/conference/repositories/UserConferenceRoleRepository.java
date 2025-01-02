package tpsi2.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tpsi2.conference.entities.Conference;
import tpsi2.conference.entities.UserConferenceRole;
import tpsi2.conference.entities.Utilisateur;

import java.util.Optional;

public interface UserConferenceRoleRepository extends JpaRepository<UserConferenceRole, Long> {

    boolean existsByUserIdAndConferenceIdAndRole(Long id, Long conferenceId, String role);

    UserConferenceRole findByUser(Optional<Utilisateur> utilisateur);

    UserConferenceRole findByUserAndRole(Optional<Utilisateur> utilisateur, String editeur);


    UserConferenceRole findByUserAndRoleAndConference(Optional<Utilisateur> utilisateur, String role, Optional<Conference> conference);
}
