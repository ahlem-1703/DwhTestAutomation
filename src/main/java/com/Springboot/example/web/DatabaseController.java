package com.Springboot.example.web;

import com.Springboot.example.model.Database;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.List;

@Controller
public class DatabaseController {
    @Autowired
    DbService dbService;
    @Autowired
    DbRepository dbRepository;

    @GetMapping("/manage_connexion")
    public String reg(Model model) {
        model.addAttribute("database", new Database());
        return "/Database/manageConnexion";
    }

    @PostMapping("/manage_connexion")
    public String createOrUpdateDb(Model model, @ModelAttribute("database") Database empDTO,
                                   RedirectAttributes redirectAttributes) {
        if (dbRepository.findDatabaseByNameAndSystem(empDTO.getName(), empDTO.getSystem()) != null) {
            model.addAttribute("message", "Database Exist");
            return "Database/manageConnexion";
        } else {
            List<Database> databases = dbService.createOrUpdateDb(empDTO);
            redirectAttributes.addFlashAttribute("DataBases", databases);
            return "redirect:/list";
        }
    }

    @GetMapping({"", "/list"})
    public String getAllDb(Model model) {
        model.addAttribute("empList", dbService.getAllDb());


        return "/Database/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDatabase(@PathVariable("id") String id) {
        dbService.deleteDatabase(Long.parseLong(id));
        return "redirect:/list";
    }

    @GetMapping("/verifyConnexion/{id}")
    public String VerifyExistenceDatabase(@PathVariable("id") String id, Model model,
                                          HttpSession httpSession) {
        Database db = dbRepository.findDatabaseById(Long.parseLong(id));
        model.addAttribute("message", dbService.VerifyConnexion(db,httpSession));
        model.addAttribute("empList", dbService.getAllDb());
        return "/Database/list";
    }
}
