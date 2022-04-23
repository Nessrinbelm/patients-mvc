package niss.emsi.patientsmvc.sec.repositories;

import niss.emsi.patientsmvc.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository  extends JpaRepository <AppUser,String> {
    AppUser findByUsername (String username);

}
