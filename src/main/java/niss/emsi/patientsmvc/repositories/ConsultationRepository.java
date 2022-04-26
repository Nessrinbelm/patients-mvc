package niss.emsi.patientsmvc.repositories;

import niss.emsi.patientsmvc.entities.Consultation;
import niss.emsi.patientsmvc.entities.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
        Page<Consultation> findByNomContains (String kw, Pageable pageable);

}

