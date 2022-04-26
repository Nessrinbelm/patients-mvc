package niss.emsi.patientsmvc;

import niss.emsi.patientsmvc.entities.Consultation;
import niss.emsi.patientsmvc.entities.Medecin;
import niss.emsi.patientsmvc.entities.Patient;
import niss.emsi.patientsmvc.repositories.ConsultationRepository;
import niss.emsi.patientsmvc.repositories.MedecinRepository;
import niss.emsi.patientsmvc.repositories.PatientRepository;
import niss.emsi.patientsmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    //Bean signife au demarrage creer un objet de type passwordencoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, MedecinRepository medecinRepository ,  ConsultationRepository consultationRepository) {
        return args -> {
            patientRepository.save(
                    new Patient(null, "Hassan", new Date(), false, 129));
            patientRepository.save(
                    new Patient(null, "Mohamed", new Date(), true, 321));
            patientRepository.save(
                    new Patient(null, "Yasmine", new Date(), true, 659));
            patientRepository.save(
                    new Patient(null, "Hanae", new Date(), false, 327));

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });

            medecinRepository.save(
                    new Medecin(null, "Hassan", 104));
            medecinRepository.save(
                    new Medecin(null, "Amine", 150));
            medecinRepository.save(
                    new Medecin(null, "Hanane", 129));
            medecinRepository.save(
                    new Medecin(null, "niss", 180));

            medecinRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });



            consultationRepository.save(
                    new Consultation(null, "Patient1", new Date(),"Docteur1"));
            consultationRepository.save(
                    new Consultation(null, "Patient2", new Date(),"Docteur2"));

            consultationRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };
    }
    // comnt
    //@Bean
    CommandLineRunner saveUsers( SecurityService securityService) {
        return args -> {
            securityService.saveNewUser(  "mohamed",  "1234",  "1234");
            securityService.saveNewUser(  "Yassmine",  "1234",  "1234");
            securityService.saveNewUser(  "Nessrin",  "1234",  "1234");
            securityService.saveNewRole(  "USER",  "");
            securityService.saveNewRole( "ADMIN", "");

            securityService.addRoleToUser(  "mohamed",  "USER");
            securityService.addRoleToUser(  "mohamed",  "ADMIN");
            securityService.addRoleToUser(  "Yassmine",  "USER");
            securityService.addRoleToUser(  "Nessrin",  "USER");




        };

    }
}
