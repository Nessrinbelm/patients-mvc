package niss.emsi.patientsmvc.sec.repositories;

import niss.emsi.patientsmvc.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository <AppRole,Long> {
    AppRole findByRoleName (String roleName);

}
