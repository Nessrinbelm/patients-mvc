package niss.emsi.patientsmvc.web;

import lombok.AllArgsConstructor;
import niss.emsi.patientsmvc.entities.Consultation;
import niss.emsi.patientsmvc.entities.Medecin;
import niss.emsi.patientsmvc.repositories.ConsultationRepository;
import niss.emsi.patientsmvc.repositories.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

//LE RENDU COTE SERVEUR
@Controller
    @AllArgsConstructor
    public class ConsultationController {
    private ConsultationRepository consultationRepository;

    @GetMapping(path = "/user/listConsultations")
    public String consultations(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Consultation> pageConsultations = consultationRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listConsultations", pageConsultations.getContent());
        model.addAttribute("pages", new int[pageConsultations.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Consultation";
    }

    @GetMapping("/admin/deleteCons")
    public String delete(Long id, String keyword, int page) {
        consultationRepository.deleteById(id);
        return "redirect:/user/listConsultations?page=" + page + "&keyword=" + keyword;

    }



    @GetMapping("/user/consultations")
    @ResponseBody
    public List<Consultation> listConsultations() {
        return consultationRepository.findAll();

    }

    @GetMapping("/admin/formConsultations")
    public String formConsultations(Model model) {
     model.addAttribute("consultation", new Consultation());
        return "formConsultations";
    }

    @PostMapping(path = "/admin/saveCons")
    public String save(Model model, @Valid Consultation consultation, BindingResult bindingResult ,
                       //si la valeur n existe pas je la remplace avec ces valeurs
                       @RequestParam (defaultValue="0")int page, @RequestParam (defaultValue="")String keyword) {
        if (bindingResult.hasErrors()) return "formConsultations";
        consultationRepository.save(consultation);
        return "redirect:/user/listConsultations?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editConsultation")
    public String editConsultation(Model model, Long id ,String keyword, int page) {
        Consultation consultation = consultationRepository.findById(id).orElse(null);
        if (consultation == null) throw new RuntimeException("Consultation introuvable");
        model.addAttribute("consultation", consultation);
        model.addAttribute( "page", page);
        model.addAttribute( "keyword", keyword);
        return "editConsultation";
    }
}
