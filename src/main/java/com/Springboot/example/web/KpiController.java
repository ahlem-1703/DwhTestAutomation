package com.Springboot.example.web;

import com.Springboot.example.model.*;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.repository.DimensionRepositroy;
import com.Springboot.example.repository.KpiRepository;
import com.Springboot.example.repository.RequeteRepository;
import com.Springboot.example.service.DbService;
import com.Springboot.example.service.KpiService;
import com.Springboot.example.service.RequeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class KpiController {

    @Autowired
    KpiService servicekpi;
    @Autowired
    DbService dbservice;
    @Autowired
    RequeteService requeteService;
    @Autowired
    KpiRepository kpirepository;
    @Autowired
    DbRepository dbrepository;
    @Autowired
    RequeteRepository requeteRepository;
    @Autowired
    DimensionRepositroy dimensionRepositroy;

    @GetMapping("/manage_kpi")
    public String manage_kpi(Model model) {
        model.addAttribute("oracleList", dbservice.getOracleDb());
        model.addAttribute("postgresList", dbservice.getPostgresDb());
        model.addAttribute("SqlServerList", dbservice.getSqlServerDb());
        model.addAttribute("kpi", new Kpi());
        Requete rq = new Requete();
        Requete requeteStep3 = new Requete();
        model.addAttribute("requete", rq);
        model.addAttribute("requeteStep3", requeteStep3);
        return "/Kpi/manage_kpi";
    }

    @PostMapping("/Kpi/manage_kpi")
    public String Insert_rslt(@RequestParam("ajoutStep2[]") String[] valdim
            , @RequestParam("ajouttStep2") String valdimalias
            , @RequestParam("copr2") String copr
            , @RequestParam("db[]") Long[] databases
            , @ModelAttribute("kpi") Kpi kpi
            , @ModelAttribute("requete") Requete r) {
        String separator = " ||' | '|| ";
        kpirepository.save(kpi);
        for(int i = 0; i < databases.length; i++) {
            Database db = dbrepository.findById(databases[i]).orElse(null);
            StringBuilder resultBuilder = new StringBuilder();
            for (int j = 0; j < valdim.length; j++) {
                resultBuilder.append(valdim[j]);
                Dimension dimChild = new Dimension();
                Kpi kpiChild = new Kpi();
                Requete childQuery = r;
                kpiChild.setId_kpi((long)  Integer.valueOf(String.valueOf(kpi.getId_kpi()) + j));
                kpiChild.setName_kpi(kpi.getName_kpi() +" par dim " + (j + 1) );
                kpiChild.setSeuil(kpi.getSeuil());
                kpiChild.setSubject_area(kpi.getSubject_area());
                kpiChild.setType("Child");
                kpiChild.setSeuil_gap(kpi.getSeuil_gap());
                kpiChild.setSeuil_dataQuality(kpi.getSeuil_dataQuality());
                kpirepository.save(kpiChild);
                dimChild.setVal_dim(valdim[j].toString());
                dimChild.setVal_dim_alias(valdimalias);
                childQuery.setId(null);
                childQuery.setId_databasee(db);
                childQuery.setCode_requete(kpiChild.getId_kpi().intValue());
                childQuery.setKpi(kpiChild);
                childQuery.setDims(Collections.singletonList(dimChild));
                if (i == 0){
                    childQuery.setCopr(r.getCopr());
                }
                else {
                    childQuery.setCopr(copr);
                }
                requeteRepository.save(childQuery);
                dimensionRepositroy.save(dimChild);
                if (j < valdim.length - 1) {
                    resultBuilder.append(separator);
                }
            }
            Requete parentQuery = r;
            Dimension dimParent = new Dimension();
            dimParent.setVal_dim(resultBuilder.toString());
            dimParent.setVal_dim_alias(valdimalias);
            if (i == 0){
                parentQuery.setCopr(r.getCopr());
            }
            else {
                parentQuery.setCopr(copr);
            }
            parentQuery.setId(null);
            parentQuery.setId_databasee(db);
            parentQuery.setCode_requete(kpi.getId_kpi().intValue());
            parentQuery.setKpi(kpi);
            parentQuery.setDims(Collections.singletonList(dimParent));
            requeteRepository.save(parentQuery);
            dimensionRepositroy.save(dimParent);
        }
        return "redirect:/listKpis";
    }

    @RequestMapping(value = "/editkpi/{kpiId}", method = RequestMethod.GET)
    public String editKpi(@PathVariable("kpiId") Long idkpi, Model model) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        model.addAttribute("kpi", kpi);
        return "/Kpi/editkpi";

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Kpi kpi, Model model) {
        kpirepository.save(kpi);
        // Add a success message to the model
        model.addAttribute("message", "KPI updated successfully");
        return "redirect:/listKpis";

    }

    @RequestMapping(value = "/addRequete/{kpiId}", method = RequestMethod.GET)
    public String affecterRequeteKpi(@PathVariable("kpiId") Long idkpi, Model model) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        model.addAttribute("databaseList", dbrepository.findAll());
        model.addAttribute("kpi", kpi);
        model.addAttribute("idkpi", kpi.getId_kpi());
        model.addAttribute("requete", new Requete());
        return "/Kpi/addRequeteKpi";

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String affecterRequete(@RequestParam("id_kpi") Long idkpi, Model model
            , @RequestParam("ajout") String[] valdim, @RequestParam("ajoutt") String[] valdimalias
            , @RequestParam("Control") Long idDatabase, @RequestParam("valKPI") String
                                          val_kpi, @RequestParam("date") String datee
            , @RequestParam("valKPIAs") String aliasvalkpi
            , @RequestParam("copr") String copr, @ModelAttribute("requete") Requete requete) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        Database db = dbrepository.findById(idDatabase).get();

        Requete r = new Requete();
        requete.setCode_requete(kpi.getId_kpi().intValue());
        r.setDate(datee);
        r.setVal_kpi(val_kpi);
        r.setVal_kpi_alias(aliasvalkpi);
        r.setCopr(copr);
        requete.setId_databasee(db);
        List<Dimension> dims = new ArrayList<Dimension>();
        for (int j = 0; j < valdim.length; j++) {
            Dimension d = new Dimension();

            d.setVal_dim(valdim[j]);
            d.setVal_dim_alias(valdimalias[j]);
            dims.add(d);
        }
        requete.setDims(dims);
        requete.setKpi(kpi);
        kpi.getRequetess().add(requete);
        System.out.println(kpi);
        kpirepository.save(kpi);

        return "redirect:/listKpis";

    }

    @GetMapping("/listKpis")
    public String getAllKpis(Model model) {
        model.addAttribute("listKpis", kpirepository.findAll());


        return "/Kpi/listkpis";
    }

    @PostMapping("/testerRequete")
    public String TesterRequete(Model model, @ModelAttribute("requete") Requete r,
                                @ModelAttribute("Control") Long id, @ModelAttribute("kpi") Kpi k
            , @ModelAttribute("dateDeb") String dateDeb, @ModelAttribute("dateFin") String dateFin
            , WebRequest request, @RequestParam("ajout") String[] valdim, @RequestParam("ajoutt") String[]
                                        valdimalias) throws ClassNotFoundException {
        System.out.println(id);

        Database dbb = dbrepository.findById(id).get();
        r.setId_databasee(dbb);
        System.out.println(r);
        List<Dimension> dims = new ArrayList<Dimension>();
        for (int i = 0; i < valdim.length; i++) {
            Dimension d = new Dimension();
            d.setVal_dim_alias(valdim[i]);
            d.setVal_dim_alias(valdimalias[i]);
            dims.add(d);
        }
        r.setDims(dims);
        String message = requeteService.TesterRequete(dbb, r, k, dateDeb, dateFin, valdim, valdimalias);
        model.addAttribute("message", message);
        request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);

        model.addAttribute("oracleList", dbservice.getOracleDb());
        model.addAttribute("postgresList", dbservice.getPostgresDb());
        model.addAttribute("SqlServerList", dbservice.getSqlServerDb());
        return "/Kpi/manage_kpi";


    }

    @GetMapping("/deletekpi/{id}")
    public String deleteKPI(@PathVariable("id") Long id) {
        kpirepository.deleteById(id);
        return "redirect:/listKpis";
    }

    @PostMapping("/testerRq")
    public String TesterRq(@RequestParam("id_kpi") Long idkpi, Model model
            , @ModelAttribute("ajout") String[] valdim, @ModelAttribute("ajoutt") String[] valdimalias
            , @ModelAttribute("Control") Long idDatabase, @ModelAttribute("dateDeb") String
                                   dateDeb, @ModelAttribute("dateFin") String dateFin
            , @ModelAttribute("requete") Requete requete) throws ClassNotFoundException {
        Kpi kpi = kpirepository.findById(idkpi).get();
        Database db = dbrepository.findById(idDatabase).get();
        requete.setCode_requete(kpi.getId_kpi().intValue());

        requete.setId_databasee(db);
        List<Dimension> dims = new ArrayList<Dimension>();
        for (int j = 0; j < valdim.length; j++) {
            Dimension d = new Dimension();

            d.setVal_dim(valdim[j]);
            d.setVal_dim_alias(valdimalias[j]);
            dims.add(d);
        }
        requete.setDims(dims);
        requete.setKpi(kpi);
        kpi.getRequetess().add(requete);
        System.out.println(requete);

        String message = requeteService.TesterRequete(db, requete, kpi, dateDeb, dateFin, valdim, valdimalias);
        model.addAttribute("message", message);
        model.addAttribute("databaseList", dbrepository.findAll());
        model.addAttribute("kpi", kpi);
        model.addAttribute("idkpi", kpi.getId_kpi());
        return "/Kpi/addRequeteKpi";


    }
}
