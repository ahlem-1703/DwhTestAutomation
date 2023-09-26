
package com.Springboot.example.web;

import com.Springboot.example.helper.LoadDataHelper;
import com.Springboot.example.helper.SchedulerHelper;
import com.Springboot.example.model.*;
import com.Springboot.example.repository.*;
import com.Springboot.example.service.*;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.IntervalUnit.DAY;

@Controller
@EnableScheduling
@Aspect
// @RestController  // Ajoutant l'annotation restcontroller pour cree les API Rest en les consommant apartir du front 
public class Rsl_Test_SysController {

    @Autowired
    TaskSchedulerRepository taskSchedulerRepository;
    @Autowired
    TriggerInformationRepository triggerInformationRepository;
    @Autowired
    LoadDataHelper loadDataHelper;

    @Autowired
    SchedulerHelper schedulerHelper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    Database1Service database1Service;
    @Autowired
    Database2Service database2Service;
    @Autowired
    private Database1Repository database1repository;
    @Autowired
    private Database2Repository database2repository;
    @Autowired
    private Vue_DetailsRepository vue_detailsRepository;
    @Autowired
    private RslRepository rslRepository;
    @Autowired
    private KpiRepository kpirepository;
    @Autowired
    private Rsl_test_sysService rsltService;
    @Autowired
    public VueDetailSevice vueDetailSevice;

    @Autowired
    private DbRepository dbrepository;
    @Autowired
    private RequeteRepository requeteRepository;
    @Autowired
    private RslRepository rslrepository;
    @Autowired
    private Rsl_test_sysRepository rsl_test_sys_repo;

    @GetMapping("resultat")
    public String GetScript(Model model) throws Exception {
        List<Kpi> KpiList = new ArrayList<Kpi>();
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");

        long startTime = System.currentTimeMillis();
        PreparedStatement stm = conn.prepareStatement("select * from kpi");
        ResultSet rslt = stm.executeQuery();
        while (rslt.next()) {
            Kpi blog = new Kpi();
            blog.setId_kpi(rslt.getLong("id_kpi"));
            blog.setName_kpi(rslt.getString("name_kpi"));
            blog.setSubject_area(rslt.getString("subject_area"));
            blog.setSeuil(rslt.getFloat("seuil"));
            blog.setSeuil_gap(rslt.getFloat("seuil_gap"));
            blog.setSeuil_dataQuality(rslt.getFloat("seuil_data_quality"));
            KpiList.add(blog);
        }

        // System.out.println(rsltService.getCompKpi());
        model.addAttribute("ReqList", requeteRepository.findAll());
        model.addAttribute("databaseList", dbrepository.findAll());
        model.addAttribute("KpisList", KpiList);
        return "resultat";

    }

    /*@PostMapping("resultat")
    public String GetScript2(Model model) throws Exception {

        // System.out.println(rsltService.getCompKpi());
        model.addAttribute("ReqList", requeteRepository.findAll());
        model.addAttribute("databaseList", dbrepository.findAll());
        model.addAttribute("KpisList", kpirepository.findAll());
        return "resultat";

    }*/

    @RequestMapping("deleteAll")
    public String GetScript3(Model model) throws Exception {
        rsl_test_sys_repo.deleteAll();
        model.addAttribute("msg", "All records are deleted successfully...");
        model.addAttribute("ReqList", requeteRepository.findAll());
        model.addAttribute("databaseList", dbrepository.findAll());
        model.addAttribute("KpisList", kpirepository.findAll());
        return "resultat";

    }

    @RequestMapping(value = "/deletekpi", method = RequestMethod.GET)
    public String deletekpi(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
                            @RequestParam("DateFin") String dateFin // , @RequestParam("PlanTask") String PlanTask //here
            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
        // LocalDate localDate = LocalDate.now();
        // while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;
        if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
            model.addAttribute("message", "date Deb supérieur à date Fin");

            return "resultat";
        }
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);
        List<Rsl_test_sys> rslt = new ArrayList<Rsl_test_sys>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

            Requete r1 = new Requete();
            for (Requete r : k.getRequetess()) {
                if (r1 != r) {
                    r1 = r;

                    if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())
                            || dbrepository.findDatabaseById(db1).equals(r.getId_databasee())) {

//						if (r.getId_databasee().getSystem().equals("SqlServer")) {
//
//							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//							Connection con = DriverManager.getConnection(
//									"jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
//											+ r.getId_databasee().getName(),
//									r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							String date = r.getDate();
//							String copr = r.getCopr();
//							String val_kpi = r.val_kpi;
//							String alias_val_kpi = r.val_kpi_alias;
//
//							List<Dimension> dimss = r.getDims();
//							String dimension = " ";
//							for (Dimension d : dimss) {
//								if (d == dimss.get(dimss.size() - 1)) {
//									dimension = dimension + d.getVal_dim();
//								} else {
//									dimension = dimension + d.getVal_dim() + " +'|'+ ";
//								}
//							}
//						
//
//						
//							PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
//									+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//									+ alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
//									+ dateFin + "' group by " + date + " ," + dimension);
//						
//							ResultSet rs = ps.executeQuery();
//							PreparedStatement ps0 = con.prepareStatement("delete from rsl_test_sys where" + k.id_kpi +" = :id_kpi and date between'"  + dateDeb +"' and '" + dateFin + "'");
//							ResultSet rs0 = ps0.executeQuery();
//							
//								while (rs.next() ) {
//								Rsl_test_sys blog = new Rsl_test_sys();
//								blog.setDate(rs.getString("date"));
//								blog.setIdKpi(rs.getFloat("Code_requete"));
//								blog.setVal_dim(rs.getString("val_dim"));
//								blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//								blog.setSysteme("SqlServer");
//								rslt.add(blog);
//
//							} 
//								
//
//							con.close();
//						} else

                        if (r.getId_databasee().getSystem().equals("Postgres")) {

                            try {
                                Connection conn = null;
                                Class.forName("org.postgresql.Driver");
                                conn = DriverManager.getConnection(
                                        "jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
                                                + r.getId_databasee().getName(),
                                        r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                                String date = r.getDate();
                                String copr = r.getCopr();
                                String val_kpi = r.val_kpi;
                                String alias_val_kpi = r.val_kpi_alias;
                                String name_db = r.getId_databasee().getName();
                                List<Dimension> dimss = r.getDims();
                                String dimension = " ";
                                for (Dimension d : dimss) {
                                    if (d == dimss.get(dimss.size() - 1)) {
                                        dimension = dimension + d.getVal_dim();
                                    } else {
                                        dimension = dimension + d.getVal_dim() + " ||'|'|| ";
                                    }
                                }


                                PreparedStatement ps0 = conn.prepareStatement("delete from rsl_test_sys where id_kpi =: " + k.id_kpi + " and date between'" + dateDeb + "' and '" + dateFin + "'");
                                ResultSet rs0 = ps0.executeQuery();

//								System.out.println(rslt.size());
                                for (Rsl_test_sys rs1 : rslt) {

                                    Rsl_test_sys r2 = new Rsl_test_sys();

                                    r2.setDate(rs1.getDate());
                                    r2.setIdKpi(rs1.getIdKpi());
                                    r2.setVal_dim(rs1.getVal_dim());
                                    r2.setVal_kpi(rs1.getVal_kpi());
                                    r2.setSysteme(rs1.getSysteme());
                                    r2.setKpi(kpirepository.findById((long) rs1.getIdKpi()).get());
                                    r2.setDatabase_name(rs1.getDatabase_name());

                                    rsltService.InsertOrUpdate(r2);


                                }
                                conn.close();
                            } catch (Exception e) {
                                System.out.println(
                                        "Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
                            }
                        }
//						} else if (r.getId_databasee().getSystem().equals("Oracle"))
//							try {
//
//								Class.forName("oracle.jdbc.OracleDriver");
//								String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//										+ r.getId_databasee().getName() + r.getId_databasee().getUsername()
//										+ r.getId_databasee().getPassword();
//								System.out.println();
//								Connection con = DriverManager.getConnection(
//										"jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							} catch (Exception e) {
//								System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
//							}
                        else {

                            System.out.println("no connexions");
                        }

                    }
                }
            }
        }

//		System.out.println(rslt.size());
        for (Rsl_test_sys rs : rslt) {

            Rsl_test_sys r = new Rsl_test_sys();

            r.setDate(rs.getDate());
            r.setIdKpi(rs.getIdKpi());
            r.setVal_dim(rs.getVal_dim());
            r.setVal_kpi(rs.getVal_kpi());
            r.setSysteme(rs.getSysteme());
            r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
            r.setDatabase_name(rs.getDatabase_name());

            rsltService.InsertOrUpdate(r);

        }

//		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

//			System.out.println("voilaaa");
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");


            PreparedStatement ps0 = conn.prepareStatement("delete from rsl_test_sys where id_kpi= " + k.getId_kpi() + " and date between'" + dateDeb + "' and '" + dateFin + "'");
            int rs0 = ps0.executeUpdate();


        }


        return "resultat";

    }

// -----------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/HistoriqueVueGlobale")
    public String getGV(@RequestParam(name = "kpi",defaultValue = "") String[] kpis,
                        @RequestParam(name="date",defaultValue = "") String date,
                        Model model) throws Exception {

        List<Vue_Globale> rslt00 = new ArrayList<Vue_Globale>();
        List<Kpi> KpiList = new ArrayList<Kpi>();
        String sql = "";
        if (kpis.length != 0 && !date.isEmpty()) {
            String kpisStr = String.join(",", kpis);
            sql = "SELECT * from vue_globale where code_requete in ("+kpisStr+") and "
                    +"TO_TIMESTAMP(date_deb, 'YYYY-MM-DD') <= TO_TIMESTAMP('"+date+"','YYYY-MM-DD')"
                    +" AND TO_TIMESTAMP(date_fin, 'YYYY-MM-DD') >=TO_TIMESTAMP('"+date+"','YYYY-MM-DD') order by id desc";
        } else {
            sql = "SELECT * FROM vue_globale WHERE 1=0"; // empty result set
        }
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");

        long startTime = System.currentTimeMillis();
        PreparedStatement stm = conn.prepareStatement("select * from kpi");
        ResultSet rslt = stm.executeQuery();
        while (rslt.next()) {
            Kpi blog = new Kpi();
            blog.setId_kpi(rslt.getLong("id_kpi"));
            blog.setName_kpi(rslt.getString("name_kpi"));
            blog.setSubject_area(rslt.getString("subject_area"));
            KpiList.add(blog);
        }

        PreparedStatement ps007 = conn.prepareStatement(
                sql
        );
        ResultSet rs007 = ps007.executeQuery();
        while (rs007.next()) {
//             System.out.println("pap");

//             System.out.println(rs007.getString("acceptation"));

            Vue_Globale blog = new Vue_Globale();

            blog.setDateDeb(rs007.getString("date_deb"));
            blog.setDateFin(rs007.getString("date_fin"));
            blog.setCode_requete(rs007.getLong("code_requete"));
            blog.setVal_kpi1(rs007.getFloat("val_kpi1"));
            blog.setVal_kpi2(rs007.getFloat("val_kpi2"));
            blog.setGap(rs007.getLong("gap"));
            blog.setGAP_par_100(rs007.getString("gap_par_100"));
            blog.setNbreRecordNotOk(rs007.getInt("nbre_record_not_ok"));
            blog.setNbreRecordOk(rs007.getInt("nbre_record_ok"));
            blog.setDataQualite(rs007.getFloat("data_qualite"));
            blog.setAcceptation(rs007.getString("acceptation"));
            blog.setName_kpi(rs007.getString("name_kpi"));
            blog.setSeuil_dataQuality(rs007.getFloat("seuil_data_quality"));
            blog.setSeuil(rs007.getFloat("seuil"));
            blog.setSeuil_gap(rs007.getFloat("seuil_gap"));
            blog.setDateExec(rs007.getString("date_exec"));
            blog.setTempsExec(rs007.getString("temps_exec"));
            rslt00.add(blog);
        }
        rslt00 = rslt00.stream().distinct().collect(Collectors.toList());
        model.addAttribute("rslt", rslt00);
        model.addAttribute("KpiList", KpiList);


        return "Historique/VueGlobale";

    }

    @GetMapping("/HistoriqueVueDetaille")
    public String getVueDetailleHistorique(@RequestParam(name = "kpi", defaultValue = "") String[] kpis,
                                           @RequestParam(name = "dateDeb", defaultValue = "") String dateDeb,
                                           @RequestParam(name = "dateFin", defaultValue = "") String dateFin,
                                           Model model) throws Exception {

        List<Vue_Detaillé> vueDetaillés = new ArrayList<>();
        String sql = "";
        if (kpis.length != 0 && !dateDeb.isEmpty() && !dateFin.isEmpty()) {
            String kpisStr = String.join(",", kpis);
            sql = "SELECT code_requete, date, gap, name_kpi, val_kpi1, val_kpi2, gap_par_100, nbre_record_not_ok, nbre_record_ok, val_dim, database1, database2 " +
                    "FROM public.vue_detaille where code_requete in ("+kpisStr+") and TO_TIMESTAMP(date, 'YYYY-MM-DD') between TO_TIMESTAMP('"+dateDeb+"', 'YYYY-MM-DD') " +
                    "and TO_TIMESTAMP('"+dateFin+"', 'YYYY-MM-DD')";
        } else {
            sql = "SELECT * FROM vue_detaille WHERE 1=0"; // empty result set
        }
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");

        long startTime = System.currentTimeMillis();


        PreparedStatement ps007 = conn.prepareStatement(
                sql
        );


        ResultSet rs007 = ps007.executeQuery();
        while (rs007.next()) {
            Vue_Detaillé blog = new Vue_Detaillé();

            blog.setCode_requete(rs007.getLong("code_requete"));
            blog.setDate(rs007.getString("date"));
            blog.setGap(rs007.getLong("gap"));
            blog.setName_kpi(rs007.getString("name_kpi"));
            blog.setVal_kpi1(rs007.getLong("val_kpi1"));
            blog.setVal_kpi2(rs007.getLong("val_kpi2"));
            blog.setGAP_par_100(rs007.getString("gap_par_100"));
            blog.setNbreRecordNotOk(rs007.getInt("nbre_record_not_ok"));
            blog.setNbreRecordOk(rs007.getInt("nbre_record_ok"));
            blog.setVal_dim(rs007.getString("val_dim"));
            blog.setDatabase1(rs007.getString("database1"));
            blog.setDatabase2(rs007.getString("database2"));
            vueDetaillés.add(blog);
        }
        vueDetaillés = vueDetaillés.stream().distinct().collect(Collectors.toList());
        model.addAttribute("rslt", vueDetaillés);


        return "Historique/VueDetaille";

    }
    @GetMapping("/HistoriqueVueDetailleDataTable")
    @ResponseBody
    public Map<String, Object> historiqueVueDetailleDataTable(@RequestParam(name = "kpi", defaultValue = "") String[] kpis,
                                                             @RequestParam(name = "dateDeb", defaultValue = "") String dateDeb,
                                                             @RequestParam(name = "dateFin", defaultValue = "") String dateFin,
                                                             @RequestParam("draw") int draw,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("search[value]") String searchValue,
                                                             @RequestParam("order[0][column]") int sortColumnIndex,
                                                             @RequestParam("order[0][dir]") String sortDirection) throws Exception {
        List<Vue_Detaillé> vueDetailles = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        int totalRecords = 0;
        String sql = "";
        String countStatement = "";
        if (kpis.length != 0 && !dateDeb.isEmpty() && !dateFin.isEmpty()) {
            String[] columnNames = {"date", "code_requete", "val_kpi1", "val_kpi2", "name_kpi", "val_dim", "gap", "GAP_par_100", "nbreRecordNotOk"};
            String orderByClause = "";

            if (sortColumnIndex >= 0) {
                String columnName = columnNames[sortColumnIndex];
                orderByClause = " ORDER BY " + columnName + " " + sortDirection;
            }

            String kpisStr = String.join(",", kpis);
            countStatement = "select count(*) from (SELECT code_requete, date, gap, name_kpi, val_kpi1, val_kpi2, gap_par_100, nbre_record_not_ok, nbre_record_ok, val_dim, database1, database2 " +
                    "FROM public.vue_detaille WHERE code_requete IN (" + kpisStr + ") AND TO_TIMESTAMP(date, 'YYYY-MM-DD') BETWEEN TO_TIMESTAMP('" + dateDeb + "', 'YYYY-MM-DD') " +
                    "AND TO_TIMESTAMP('" + dateFin + "', 'YYYY-MM-DD') " + orderByClause + ") as subquery";
            sql = "SELECT code_requete, date, gap, name_kpi, val_kpi1, val_kpi2, gap_par_100, nbre_record_not_ok, nbre_record_ok, val_dim, database1, database2 " +
                    "FROM public.vue_detaille WHERE code_requete IN (" + kpisStr + ") AND TO_TIMESTAMP(date, 'YYYY-MM-DD') BETWEEN TO_TIMESTAMP('" + dateDeb + "', 'YYYY-MM-DD') " +
                    "AND TO_TIMESTAMP('" + dateFin + "', 'YYYY-MM-DD') " + orderByClause + " OFFSET ? LIMIT ?";
        } else {
            sql = "SELECT * FROM vue_detaille WHERE 1 = 0 OFFSET ? LIMIT ?"; // empty result set
            countStatement = "SELECT count(*) FROM vue_detaille WHERE 1 = 0"; // empty result set
        }

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, page * size);
            ps.setInt(2, size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vue_Detaillé blog = new Vue_Detaillé();
                    blog.setCode_requete(rs.getLong("code_requete"));
                    blog.setDate(rs.getString("date"));
                    blog.setGap(rs.getLong("gap"));
                    blog.setName_kpi(rs.getString("name_kpi"));
                    blog.setVal_kpi1(rs.getLong("val_kpi1"));
                    blog.setVal_kpi2(rs.getLong("val_kpi2"));
                    blog.setGAP_par_100(rs.getString("gap_par_100"));
                    blog.setNbreRecordNotOk(rs.getInt("nbre_record_not_ok"));
                    blog.setNbreRecordOk(rs.getInt("nbre_record_ok"));
                    blog.setVal_dim(rs.getString("val_dim"));
                    blog.setDatabase1(rs.getString("database1"));
                    blog.setDatabase2(rs.getString("database2"));
                    vueDetailles.add(blog);
                }
            }
            try(PreparedStatement cs = conn.prepareStatement(countStatement)){
                ResultSet rcs = cs.executeQuery();
                rcs.next();
                totalRecords = rcs.getInt(1);
            }
            res.put("draw", draw);
            res.put("recordsTotal", totalRecords);
            res.put("data", vueDetailles);
            res.put("recordsFiltered", totalRecords);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("An error occurred while executing the SQL statement.");
        }

        return res;
    }

    @RequestMapping(value = "/deletevd", method = RequestMethod.GET)
    public String deletevd(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
                           @RequestParam("DateFin") String dateFin // , @RequestParam("PlanTask") String PlanTask //here
            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
        // LocalDate localDate = LocalDate.now();
        // while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;
        if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
            model.addAttribute("message", "date Deb supérieur à date Fin");

            return "resultat";
        }
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);
        List<Vue_Detaillé> rslt = new ArrayList<Vue_Detaillé>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

            Requete r1 = new Requete();
            for (Requete r : k.getRequetess()) {
                if (r1 != r) {
                    r1 = r;

                    if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())
                            || dbrepository.findDatabaseById(db1).equals(r.getId_databasee())) {

//							if (r.getId_databasee().getSystem().equals("SqlServer")) {
                        //
//								Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        //
//								Connection con = DriverManager.getConnection(
//										"jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
                        //
//								String date = r.getDate();
//								String copr = r.getCopr();
//								String val_kpi = r.val_kpi;
//								String alias_val_kpi = r.val_kpi_alias;
                        //
//								List<Dimension> dimss = r.getDims();
//								String dimension = " ";
//								for (Dimension d : dimss) {
//									if (d == dimss.get(dimss.size() - 1)) {
//										dimension = dimension + d.getVal_dim();
//									} else {
//										dimension = dimension + d.getVal_dim() + " +'|'+ ";
//									}
//								}
//							
                        //
//							
//								PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
//										+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//										+ alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
//										+ dateFin + "' group by " + date + " ," + dimension);
//							
//								ResultSet rs = ps.executeQuery();
//								PreparedStatement ps0 = con.prepareStatement("delete from rsl_test_sys where" + k.id_kpi +" = :id_kpi and date between'"  + dateDeb +"' and '" + dateFin + "'");
//								ResultSet rs0 = ps0.executeQuery();
//								
//									while (rs.next() ) {
//									Rsl_test_sys blog = new Rsl_test_sys();
//									blog.setDate(rs.getString("date"));
//									blog.setIdKpi(rs.getFloat("Code_requete"));
//									blog.setVal_dim(rs.getString("val_dim"));
//									blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//									blog.setSysteme("SqlServer");
//									rslt.add(blog);
                        //
//								} 
//									
                        //
//								con.close();
//							} else
                        if (r.getId_databasee().getSystem().equals("Postgres")) {

                            try {
                                Connection conn = null;
                                Class.forName("org.postgresql.Driver");
                                conn = DriverManager.getConnection(
                                        "jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
                                                + r.getId_databasee().getName(),
                                        r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                                String date = r.getDate();
                                String copr = r.getCopr();
                                String val_kpi = r.val_kpi;
                                String alias_val_kpi = r.val_kpi_alias;
                                String name_db = r.getId_databasee().getName();
                                List<Dimension> dimss = r.getDims();
                                String dimension = " ";
                                for (Dimension d : dimss) {
                                    if (d == dimss.get(dimss.size() - 1)) {
                                        dimension = dimension + d.getVal_dim();
                                    } else {
                                        dimension = dimension + d.getVal_dim() + " ||'|'|| ";
                                    }
                                }


                                PreparedStatement ps0 = conn.prepareStatement("delete from vue_detaille where code_requete =: " + k.id_kpi + " and date between'" + dateDeb + "' and '" + dateFin + "'");
                                ResultSet rs0 = ps0.executeQuery();

//									System.out.println(rslt.size());
                                for (Vue_Detaillé rs1 : rslt) {

                                    Vue_Detaillé r2 = new Vue_Detaillé();

                                    r2.setDate(rs1.getDate());
                                    r2.setCode_requete(rs1.getCode_requete());
                                    r2.setVal_dim(rs1.getVal_dim());
                                    r2.setVal_kpi1(rs1.getVal_kpi1());
                                    r2.setVal_kpi2(rs1.getVal_kpi2());


                                    vueDetailSevice.InsertOrUpdate(r2);


                                }
                                conn.close();
                            } catch (Exception e) {
                                System.out.println(
                                        "Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
                            }
                        }
//							} else if (r.getId_databasee().getSystem().equals("Oracle"))
//								try {
                        //
//									Class.forName("oracle.jdbc.OracleDriver");
//									String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//											+ r.getId_databasee().getName() + r.getId_databasee().getUsername()
//											+ r.getId_databasee().getPassword();
//									System.out.println();
//									Connection con = DriverManager.getConnection(
//											"jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//													+ r.getId_databasee().getName(),
//											r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
                        //
//								} catch (Exception e) {
//									System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
//								}
                        else {

                            System.out.println("no connexions");
                        }

                    }
                }
            }
        }

//			System.out.println(rslt.size());
        for (Vue_Detaillé rs : rslt) {

            Vue_Detaillé r = new Vue_Detaillé();

            r.setDate(rs.getDate());
            r.setCode_requete(rs.getCode_requete());
            r.setVal_dim(rs.getVal_dim());
            r.setVal_kpi1(rs.getVal_kpi1());
            r.setVal_kpi2(rs.getVal_kpi2());


            vueDetailSevice.InsertOrUpdate(r);

        }

//			List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

//				System.out.println("voilaaa");
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");


            PreparedStatement ps0 = conn.prepareStatement("delete from vue_detaille where code_requete= " + k.getId_kpi() + " and date between'" + dateDeb + "' and '" + dateFin + "'");
            int rs0 = ps0.executeUpdate();


        }


        return "resultat";

    }


// --------------------------------------------------------------------------------------------------------------------------------------------


    @RequestMapping(value = "/resultat2", method = RequestMethod.GET)
    public String GetRes1(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
                          @RequestParam("DateFin") String dateFin // , @RequestParam("PlanTask") String PlanTask //here
            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
        // LocalDate localDate = LocalDate.now();
        // while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;
        if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
            model.addAttribute("message", "date Deb supérieur à date Fin");
            return "resultat";
        }
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);
        List<Rsl_test_sys> rslt = new ArrayList<Rsl_test_sys>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

            Requete r1 = new Requete();
            for (Requete r : k.getRequetess()) {
                if (r1 != r) {
                    r1 = r;

                    if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())
                            || dbrepository.findDatabaseById(db1).equals(r.getId_databasee())) {

                        if (r.getId_databasee().getSystem().equals("SqlServer")) {

                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                            Connection con = DriverManager.getConnection(
                                    "jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
                                            + r.getId_databasee().getName(),
                                    r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                            String date = r.getDate();
                            String copr = r.getCopr();
                            String val_kpi = r.val_kpi;
                            String alias_val_kpi = r.val_kpi_alias;

                            List<Dimension> dimss = r.getDims();
                            String dimension = " ";
                            for (Dimension d : dimss) {
                                if (d == dimss.get(dimss.size() - 1)) {
                                    dimension = dimension + d.getVal_dim();
                                } else {
                                    dimension = dimension + d.getVal_dim() + " +'|'+ ";
                                }
                            }
//							System.out.println(dimension);

                            System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ,"
                                    + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr
                                    + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by "
                                    + date + " ," + dimension);
                            System.out.println("no1");
                            PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
                                    + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
                                    + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
                                    + dateFin + "' group by " + date + " ," + dimension);
                            System.out.println("no2");
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                Rsl_test_sys blog = new Rsl_test_sys();
                                blog.setDate(rs.getString("date"));
                                blog.setIdKpi(rs.getFloat("Code_requete"));
                                blog.setVal_dim(rs.getString("val_dim"));
                                blog.setVal_kpi(rs.getFloat(alias_val_kpi));
                                blog.setSysteme("SqlServer");
                                rslt.add(blog);

                            }

                            con.close();
                        } else if (r.getId_databasee().getSystem().equals("Postgres")) {

                            try {
                                Connection conn = null;
                                Class.forName("org.postgresql.Driver");
                                conn = DriverManager.getConnection(
                                        "jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
                                                + r.getId_databasee().getName(),
                                        r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                                String date = r.getDate();
                                String copr = r.getCopr();
                                String val_kpi = r.val_kpi;
                                String alias_val_kpi = r.val_kpi_alias;
                                String name_db = r.getId_databasee().getName();
                                List<Dimension> dimss = r.getDims();
                                String dimension = " ";
                                for (Dimension d : dimss) {
                                    if (d == dimss.get(dimss.size() - 1)) {
                                        dimension = dimension + d.getVal_dim();
                                    } else {
                                        dimension = dimension + d.getVal_dim() + " ||'|'|| ";
                                    }
                                }

//								System.out.println(dimension);
                                System.out.println("Select " + date + " as date, "
                                        + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi
                                        + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb
                                        + "' and '" + dateFin + "' group by " + date + " ," + dimension);

                                PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, "
                                        + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi
                                        + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb
                                        + "' and '" + dateFin + "' group by " + date + " ," + dimension);
                                ResultSet rs = ps.executeQuery();
//								System.out.println("no3");
                                while (rs.next()) {
                                    Rsl_test_sys blog = new Rsl_test_sys();
                                    blog.setDate(rs.getString("date"));
                                    blog.setIdKpi(rs.getFloat("Code_requete"));
                                    blog.setVal_dim(rs.getString("val_dim"));
                                    blog.setVal_kpi(rs.getFloat(alias_val_kpi));

                                    blog.setDatabase_name(name_db);
                                    blog.setSysteme(name_db);

                                    blog.setDbname(name_db);
                                    rslt.add(blog);

                                }
                                conn.close();
                            } catch (Exception e) {
                                System.out.println(
                                        "Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
                            }
                        } else if (r.getId_databasee().getSystem().equals("Oracle"))
                            try {

                                Class.forName("oracle.jdbc.OracleDriver");
                                String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
                                        + r.getId_databasee().getName() + r.getId_databasee().getUsername()
                                        + r.getId_databasee().getPassword();
                                System.out.println();
                                Connection con = DriverManager.getConnection(
                                        "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
                                                + r.getId_databasee().getName(),
                                        r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                            } catch (Exception e) {
                                System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
                            }
                        else {

                            System.out.println("no connexions");
                        }

                    }
                }
            }
        }
//		System.out.println(rslt.size());
        for (Rsl_test_sys rs : rslt) {

            Rsl_test_sys r = new Rsl_test_sys();

            r.setDate(rs.getDate());
            r.setIdKpi(rs.getIdKpi());
            r.setVal_dim(rs.getVal_dim());
            r.setVal_kpi(rs.getVal_kpi());
            r.setSysteme(rs.getSysteme());
            r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
            r.setDatabase_name(rs.getDatabase_name());

            rsltService.InsertOrUpdate(r);

        }

//		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

//			System.out.println("voilaaa");
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
            // requete pour afficher idkpi valkpi1,valkpi2,date,NbreRecord 1 , Nbre Record 2
//            PreparedStatement ps = conn.prepareStatement("select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3 ,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n" +
//                    "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n" +
//                    " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from \n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    " select a.idkpi, count(a.valeur_dim) as nbre,a.date from rsl_test_sys a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T3\n" +
//                    "  FULL OUTER JOIN\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from\n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    
//                    " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from rsl_test_sys a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T4\n" +
//                    " ON   T3.date = T4.date) as RR1 \n" +
//                    "right OUTER JOIN\n" +
//                    " (select sum((	T1.val_kpi)/2) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n" + // Here
//                    ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n" +
//                    ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" +  //here  (- to +)
//                    ",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n" +
//                    " from " +
//                    " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "'  and  a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
//                    " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "' and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
//                    " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n" +
//                    " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n" +
//                    " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n" +
//                    "order by COALESCE(T1.date, B2.date)) as RR2\n" +
//                    " \n" +
//                    " on RR1.date=RR2.date\n" +
//                    " Left outer join\n" +
//                    " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n" +
//                    "(select  a.idkpi,a.date,  COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n" +
//                    "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R1\n" +
//                    "full OUTER join  \n" +
//                    " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n" +
//                    "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R2 \n" +
//                    " on R1.date=R2.date\n" +
//                    " order by R1.date) as RR3 \n" +
//                    " on RR2.date = RR3.date	" 
//                   );

            // requete pour afficher Nbre d'enregistrement existe dans db2 et NON dans db1

//			PreparedStatement ps4 = conn.prepareStatement("select T2.date,sum(T2.nbre) as load2 from\n"
//					+ "(select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n" + "where a.idkpi="
//					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
//					+ d2.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim\n" + "except all\n"
//					+ "select a.idkpi, count(a.valeur_dim) as nbre,a.date from rsl_test_sys a \n" + "where a.idkpi="
//					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
//					+ d1.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim) as T2 \n"
//					+ "group by T2.date \n" + "order by T2.date ");

            /*
             * PreparedStatement ps5 = conn.
             * prepareStatement("select k.name_kpi as name , a.idkpi as Code_Requete ,a.date as Date,count(distinct(a.val_kpi)) as val2,sum(distinct(a.val_kpi)) as val3\r\n"
             * + " from rsl_test_sys as a ,kpi k where k.id_kpi=a.idkpi\r\n " +
             * "and a.date IN(\r\n " + "select a.date\r\n " + "from rsl_test_sys as a \r\n "
             * + "where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
             * +dateFin+"' \r\n " + "and a.system='"+d1.getSystem()+"'\r\n " +
             *
             * "EXCEPT\r\n" + "select a.date\r\n " + "from rsl_test_sys as a \r\n " +
             * "where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
             * +dateFin+"' \r\n " + "and a.system='"+d2.getSystem()+"')  " +
             * "and a.idkpi="+k.id_kpi+"\r\n " + "group by a.date,k.name_kpi , a.idkpi");
             * System.out.println("pfff"); PreparedStatement ps6 = conn.
             * prepareStatement("select k.name_kpi as name , a.idkpi as Code_Requete ,a.date as Date,count(distinct(a.val_kpi)) as val2,sum(distinct(a.val_kpi)) as val3\r\n"
             * + " from rsl_test_sys as a ,kpi k where k.id_kpi=a.idkpi\r\n " +
             * " and a.date IN(\r\n" + " select a.date\r\n" + " from rsl_test_sys as a \r\n"
             * + " where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
             * +dateFin+"' \r\n" + " and a.system='"+d2.getSystem()+"'\r\n" +
             *
             * " EXCEPT\r\n" + " select a.date\r\n" + " from rsl_test_sys as a \r\n" +
             * " where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
             * +dateFin+"' \r\n" + " and a.system='"+d1.getSystem()+"')\r\n" +
             * " and a.idkpi="+k.id_kpi+"\r\n" + " group by a.date,k.name_kpi , a.idkpi");
             */

            PreparedStatement ps = conn.prepareStatement(
                    "select  k.name_kpi as name ,a.idkpi  Code_requete , a.date as Date , a.valeur_dim as dim , a.val_kpi as val3  , b.val_kpi  as val4 ,0 as nbreRecordOk ,0 as nbreRecordNotOk,abs(a.val_kpi - b.val_kpi) as gap from database1 a   \r\n"
                            + "Full outer join database2 b  on  a.id_kpi = b.id_kpi and a.valeur_dim = b.valeur_dim and a.date = b.date\r\n"
                            + "left outer join kpi k on k.id_kpi=a.id_kpi \r\n"
                            + "where a.id_kpi = k.id_kpi \r\n"
            );

            ResultSet rs = ps.executeQuery();

//			PreparedStatement ps1 = conn.prepareStatement(
//					"select  valeur_dim as mix from rsl_test_sys where valeur_dim = (select max(valeur_dim) from rsl_test_sys)");
//			ResultSet rs1 = ps1.executeQuery();

            long startTime = System.currentTimeMillis();

            // esultSet rs1 = ps1.executeQuery();

            // ResultSet rs2 = ps2.executeQuery();

            // ResultSet rs3 = ps3.executeQuery();

//			ResultSet rs4 = ps4.executeQuery();
            long startTime2 = System.currentTimeMillis();

            // ResultSet rs5 = ps5.executeQuery();
            long startTime3 = System.currentTimeMillis();

            // ResultSet rs6 = ps6.executeQuery();
//			System.out.println("bf");

            while (rs.next()) {
                System.out.println("pap");
//
//				Vue_Globale blog = new Vue_Globale();
//				blog.setDate(rs.getString("Date"));
//				blog.setCode_requete(rs.getLong("Code_requete"));
//				blog.setVal_kpi1(rs.getLong("val3"));
//				blog.setVal_kpi2(rs.getLong("val4"));
//				 blog.setName_kpi(rs.getString("name"));
//				 blog.setVal_dim(rs.getString("dim"));
//                blog.setLoad2(rs.getString("val2"));
//
//				// probleme au niveau de nbreRecordOk si on a un seul enregistrement //
//				// Heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere
//
//				// blog.setNbreRecordOk(rs.getInt("nbreRecordOK"));
//				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//				Date dateobj = new Date();
//				blog.dateExec = (dfff.format(dateobj));
//				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//
////                
////                int  j = rs1.getInt("mix");
////                   blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk")+ j);
//				int i = 0;
//				int j = 0;
//
//                if (rs.getInt("gap") > k.getSeuil_gap()) {
//                	i = i+1 ;
//            	blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk") + i);}
//
//				if (rs.getInt("gap") < k.getSeuil_gap()) {
//					j = j + 1;
//					
//			
//				
//				
//					blog.setNbreRecordOk(rs.getInt("nbreRecordOk") + j);
//				}
//
//				// blog.setFoundDB1(rs.getInt("load1"));
////                blog.setFoundDB2(rs.getInt("load2"));
//				blog.setGap((long) rs.getInt("gap"));
//				java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
//				BigDecimal bigD = new BigDecimal(
//						(rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//				blog.setGAP_par_100(dff.format(bigD));
////				if (rs.getInt("gap") < 1400) {// (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))) == 0) &&
////												// (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"),
////												// rs.getDouble("val2"))) * 100F == 100.0) {
////					blog.setAcceptation("OK");
////				} else if (rs.getInt("gap") > 1400 && rs.getInt("gap") < 2400) {
////					blog.setAcceptation("OK partiel");
////				}
////
//////                } else if (k.getSeuil_dataQuality() < (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F
//////                        && k.getSeuil_gap() > (rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))) {
//////                    blog.setAcceptation("OK partiel");
////				else {
////					blog.setAcceptation("NotOk"); // This One !
////				}
//
//				rslt2.add(blog);
//
//			}

            }
            /*
             * while(rs5.next()) { Vue_Globale blog = new Vue_Globale();
             * blog.setDate(rs5.getString("Date"));
             * blog.setCode_requete(rs5.getLong("Code_requete")); DateFormat dfff = new
             * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
             * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
             * java.text.DecimalFormat("###.###");
             *
             * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime2) /1000F);
             * blog.setVal_kpi1( rs5.getLong("val3")); blog.setVal_kpi2((long) 0);
             * blog.setName_kpi(rs5.getString("name"));
             * blog.setLoad1(rs5.getString("val2")); blog.setLoad2("0");
             * blog.setNbreRecordOk(0); blog.setNbreRecordNotOk(0);
             * blog.setDataQualite("0"); blog.setFoundDB2(0);
             * blog.setGap(rs5.getLong("val3")); java.text.DecimalFormat dff = new
             * java.text.DecimalFormat("###"); System.out.println(); BigDecimal bigD = new
             * BigDecimal((Math.abs ( rs5.getDouble("val3")) *100)/ rs5.getDouble("val3"));
             * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
             *
             *
             * } while(rs6.next()) { Vue_Globale blog = new Vue_Globale();
             * blog.setDate(rs6.getString("Date"));
             * blog.setCode_requete(rs6.getLong("Code_requete")); DateFormat dfff = new
             * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
             * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
             * java.text.DecimalFormat("###.###");
             *
             * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime3) /1000F);
             * blog.setVal_kpi1( rs6.getLong("val3")); blog.setVal_kpi2((long) 0);
             * blog.setName_kpi(rs6.getString("name"));
             * blog.setLoad1(rs6.getString("val2")); blog.setLoad2("0");
             * blog.setDataQualite("0"); blog.setNbreRecordOk(0);
             * blog.setNbreRecordNotOk(0); blog.setFoundDB1( rs6.getInt("val2"));
             * blog.setFoundDB2(0); blog.setGap(rs6.getLong("val3"));
             * java.text.DecimalFormat dff = new java.text.DecimalFormat("###");
             * System.out.println(); BigDecimal bigD = new BigDecimal((Math.abs (
             * rs6.getDouble("val3")) *100)/ rs6.getDouble("val3"));
             * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
             *
             *
             * }
             */

//		for (Vue_Globale r : rslt2) {
//			rslRepository.save(r);
        }
//		model.addAttribute("rslt", rslt2);

        return "resultat2";

    }

// FROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM HERE !!!

//	@RequestMapping(value = "/resultat2", method = RequestMethod.GET)
//	public String GetScript3(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
//			@RequestParam("DateFin") String dateFin, @RequestParam("db1") long db1, @RequestParam("db2") Long db2)
//			throws Exception {
//
//		if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
//				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
//			model.addAttribute("message", "date Deb supérieur à date Fin");
//			return "resultat";
//		}
//		Database d1 = dbrepository.findDatabaseById(db1);
//		Database d2 = dbrepository.findDatabaseById(db2);
//
//		List<Rsl_test_sys> rslt = new ArrayList<Rsl_test_sys>();
//		for (String s : kpis) {
//			long kpi = Long.parseLong(s);
//			Kpi k = kpirepository.findById(kpi).get();
//
//			Requete r1 = new Requete();
//			for (Requete r : k.getRequetess()) {
//				if (r1 != r) {
//					r1 = r;
//
//					if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())) { // HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERE
//																							// !
//
//						if (r.getId_databasee().getSystem().equals("SqlServer")) {
//
//							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//							Connection con = DriverManager.getConnection(
//									"jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
//											+ r.getId_databasee().getName(),
//									r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							String date = r.getDate();
//							String copr = r.getCopr();
//							String val_kpi = r.val_kpi;
//							String alias_val_kpi = r.val_kpi_alias;
//
//							List<Dimension> dimss = r.getDims();
//							String dimension = " ";
//							for (Dimension d : dimss) {
//								if (d == dimss.get(dimss.size() - 1)) {
//									dimension = dimension + d.getVal_dim();
//								} else {
//									dimension = dimension + d.getVal_dim() + " +'|'+ ";
//								}
//							}
//							System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ,"
//									+ dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr
//									+ " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by "
//									+ date + " ," + dimension);
//
//							PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
//									+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//									+ alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
//									+ dateFin + "' group by " + date + " ," + dimension);
//							ResultSet rs = ps.executeQuery();
//
//							while (rs.next()) {
//								Rsl_test_sys blog = new Rsl_test_sys();
//								blog.setDate(rs.getString("date"));
//								blog.setIdKpi(rs.getFloat("Code_requete"));
//								blog.setVal_dim(rs.getString("val_dim"));
//								blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//								blog.setSysteme("SqlServer");
//								rslt.add(blog);
//							}
//
//							con.close();
//						} else if (r.getId_databasee().getSystem().equals("Postgres")) {
//
//							try {
//								Connection conn = null;
//								Class.forName("org.postgresql.Driver");
//								conn = DriverManager.getConnection(
//										"jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//								String date = r.getDate();
//								String copr = r.getCopr();
//								String val_kpi = r.val_kpi;
//								String alias_val_kpi = r.val_kpi_alias;
//
//								List<Dimension> dimss = r.getDims();
//								String dimension = " ";
//								for (Dimension d : dimss) {
//									if (d == dimss.get(dimss.size() - 1)) {
//										dimension = dimension + d.getVal_dim();
//									} else {
//										dimension = dimension + d.getVal_dim() + " ||'|'|| ";
//									}
//								}
//								System.out.println("Select " + date + " as date, " + k.getId_kpi()
//										+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//										+ alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb
//										+ "' and " + dateFin + " group by " + date + " ," + dimension);
//
//								PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, "
//										+ k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi
//										+ " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb
//										+ "' and '" + dateFin + "' group by " + date + " ," + dimension);
//								ResultSet rs = ps.executeQuery();
//
//								while (rs.next()) {
//									Rsl_test_sys blog = new Rsl_test_sys();
//									blog.setDate(rs.getString("date"));
//									blog.setIdKpi(rs.getFloat("Code_requete"));
//									blog.setVal_dim(rs.getString("val_dim"));
//									blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//									blog.setSysteme("Postgres");
//									rslt.add(blog);
//								}
//
//								conn.close();
//							} catch (Exception e) {
//								System.out.println(
//										"Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
//							}
//						} else if (r.getId_databasee().getSystem().equals("Oracle"))
//							try {
//
//								Class.forName("oracle.jdbc.OracleDriver");
//								String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//										+ r.getId_databasee().getName() + r.getId_databasee().getUsername()
//										+ r.getId_databasee().getPassword();
//								System.out.println();
//								Connection con = DriverManager.getConnection(
//										"jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							} catch (Exception e) {
//								System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
//							}
//						else {
//
//							System.out.println("no connexions");
//						}
//
//					}
//				}
//			}
//		}
//		System.out.println(rslt.size());
//		for (Rsl_test_sys rs : rslt) {
//
//			Rsl_test_sys r = new Rsl_test_sys();
//			r.setDate(rs.getDate());
//			r.setIdKpi(rs.getIdKpi());
//			r.setVal_dim(rs.getVal_dim());
//			r.setVal_kpi(rs.getVal_kpi());
//			r.setSysteme(rs.getSysteme());
//			r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
//			rsltService.InsertOrUpdate(r);
//		}
//
//		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
//		for (String s : kpis) {
//			long kpi = Long.parseLong(s);
//			Kpi k = kpirepository.findById(kpi).get();
//			Connection conn = null;
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
//			PreparedStatement ps = conn.prepareStatement(
//					"select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n"
//							+ "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n"
//							+ " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n"
//							+ " (select T2.date,sum(T2.nbre) as nbre from \n"
//							+ " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n"
//							+ " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "'  and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n"
//							+ " select a.idkpi, count(a.valeur_dim) as nbre,a.date from rsl_test_sys a \n"
//							+ " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '"
//							+ dateFin + "'  and a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" + " group by T2.date\n"
//							+ " order by T2.date) as T3\n" + "  FULL OUTER JOIN\n"
//							+ " (select T2.date,sum(T2.nbre) as nbre from\n"
//							+ " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n"
//							+ " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "'  and a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n"
//							+ " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from rsl_test_sys a \n"
//							+ " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '"
//							+ dateFin + "'  and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" + " group by T2.date\n"
//							+ " order by T2.date) as T4\n" + " ON   T3.date = T4.date) as RR1 \n" + "right OUTER JOIN\n"
//							+ " (select sum(T1.val_kpi) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n"
//							+ ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n"
//							+ ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" + // heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere
//							",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n" + " from "
//							+ " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n"
//							+ " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin
//							+ "'  and  a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n"
//							+ " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n"
//							+ " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n"
//							+ " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n"
//							+ " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n"
//							+ " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n"
//							+ "order by COALESCE(T1.date, B2.date)) as RR2\n" + " \n" + " on RR1.date=RR2.date\n"
//							+ " Left outer join\n"
//							+ " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n"
//							+ "(select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n"
//							+ "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n"
//							+ "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//							+ "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" + " group by a.date , a.idkpi  \n"
//							+ " order by a.date) as R1\n" + "full OUTER join  \n"
//							+ " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n"
//							+ "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n"
//							+ "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//							+ "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" + " group by a.date , a.idkpi  \n"
//							+ " order by a.date) as R2 \n" + " on R1.date=R2.date\n" + " order by R1.date) as RR3 \n"
//							+ " on RR2.date = RR3.date	");
//
//			long startTime = System.currentTimeMillis();
//
//			ResultSet rs = ps.executeQuery();
//
//			PreparedStatement ps3 = conn.prepareStatement(
//					"select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as val3 , abs(a.val_kpi - b.val_kpi) as gap  , 0 as nbreRecordOK , 0 as nbreRecordNotOk "
//							+ " , b.val_kpi as val4 , k.name_kpi as name from rsl_test_sys a ,rsl_test_sys b ,kpi k \r\n"
//							+ "where b.valeur_dim = a.valeur_dim \r\n" + "and b.idkpi = a.idkpi \r\n"
//							+ "and a.idkpi = k.id_kpi and abs(b.val_kpi - a.val_kpi) > k.seuil "
//
//			);
////            PreparedStatement ps50 = conn.prepareStatement("select k.id_kpi as Code_requete, coalesce (sum(T1.val_kpi),0) as val1\n" +
////                    ",coalesce (sum(B2.val_kpi),0) as val2\n" +
////                    ",ABS(coalesce (sum(T1.val_kpi),0)- coalesce (sum(B2.val_kpi),0)) as gap\n" +   //heeeeeeeeeeeere
////                    ",k.name_kpi as name,coalesce(B2.date,T1.date) as Date ,coalesce(T1.valeur_dim,B2.valeur_dim) as dim\n" +
////                    "from\n" +
////                    "(select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n" +
////                    "where idkpi="+k.getCode_requete()+" and a.date like '"+k.getDate()+"' and  a.system='SqlServer'\n" +
////                    "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
////                    "FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n" +
////                    "where idkpi="+k.getCode_requete()+" and a.date like '"+k.getDate()+"'  and a.system='Postgres'\n" +
////                    "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
////                    " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n" +
////                    "left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi " +
////                    "where ABS(coalesce ((T1.val_kpi),0)- coalesce ((B2.val_kpi),0))  > "+kpi.getSeuil()+" \n" +
////                    "group by k.name_kpi, B2.date,B2.valeur_dim,T1.date,k.id_kpi,T1.valeur_dim,B2.valeur_dim\n" +
////                    "\n" +
////                    "order by B2.date");
////            ResultSet rs50 = ps50.executeQuery();
////            while(rs.next()) {
////                Vue_Detaillé blog = new  Vue_Detaillé();
////                blog.setDate(rs.getString("Date"));
////                blog.setCode_requete(rs.getLong("Code_requete"));
////                blog.setGroupement(rs.getString("dim"));
////                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
////                blog.setVal_kpi1(rs.getDouble("val1"));
////                blog.setVal_kpi2(rs.getDouble("val2"));
////                blog.setName_kpi(rs.getString("name"));
////
////                blog.setGap(rs.getDouble("gap"));
////                rslt2.add(blog);
////            }
//
//			ResultSet rs3 = ps3.executeQuery();
//
//			while (rs.next() && rs3.next()) {
//				System.out.println("pap");
//
//				Vue_Globale blog = new Vue_Globale();
//				blog.setDate(rs.getString("Date"));
//				blog.setCode_requete(rs.getLong("Code_requete"));
//				blog.setVal_kpi1(rs.getLong("val1"));
//				blog.setVal_kpi2(rs.getLong("val2"));
//				blog.setName_kpi(rs.getString("name"));
//				blog.setLoad1(rs.getString("val3"));
//				blog.setLoad2(rs.getString("val4"));
//				// probleme au niveau de nbreRecordOk si on a un seul enregistrement
//				blog.setNbreRecordOk(rs.getInt("nbreRecordOK"));
//				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//				Date dateobj = new Date();
//				blog.dateExec = (dfff.format(dateobj));
//				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//
//				blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk"));
//
//				System.out.println(
//						(rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
//				blog.setDataQualite(ddf.format(
//						(rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//
//				blog.setFoundDB1(rs.getInt("load1"));
//				blog.setFoundDB2(rs.getInt("load2"));
//				blog.setGap((long) rs.getInt("gap"));
//				java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
//
//				BigDecimal bigD = new BigDecimal(
//						(rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//				blog.setGAP_par_100(dff.format(bigD));
//				System.out.println(blog.getGap());
//				if (((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))) == 0)
//						&& (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2")))
//								* 100F == 100.0) {
//					blog.setAcceptation("OK");
//				} else if (k
//						.getSeuil_dataQuality() < (rs.getDouble("nbreRecordOk")
//								/ Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F
//						&& k.getSeuil_gap() > (rs.getInt("gap") * 100)
//								/ (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))) {
//					blog.setAcceptation("OK partiel");
//				} else {
//					blog.setAcceptation("NotOk");
//				}
//
//				rslt2.add(blog);
//			}
//
//			model.addAttribute("rslt", rslt2);
//
//		}
//
//		for (Vue_Globale r : rslt2) {
//			rslRepository.save(r);
//		}
//		return "resultat2";
//	}

    // }
    // TOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO HERE
    @RequestMapping(value = "/Vue_Global", method = RequestMethod.GET)
    public String Getres15(Model model
            ,
                           @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
                           @RequestParam("DateFin") String dateFin // , @RequestParam("PlanTask") String PlanTask //here
            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
        // LocalDate localDate = LocalDate.now();
        // while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;
        StopWatch watch = new StopWatch();
        watch.start();
        if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
            model.addAttribute("message", "date Deb supérieur à date Fin");

            return "resultat3";
        }
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);

        List<Rsl_test_sys> rslt = new ArrayList<Rsl_test_sys>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();

            Requete r1 = new Requete();
            for (Requete r : k.getRequetess()) {
                if (r1 != r) {
                    r1 = r;

                    if (dbrepository.findDatabaseById(db1).equals(r.getId_databasee())) {  //HEEEEEEEEEEEEEEEEEEEEERE !

                        if (r.getId_databasee().getSystem().equals("SqlServer")) {


                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() +
                                            ";databaseName=" + r.getId_databasee().getName()
                                    , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                            String date = r.getDate();
                            String copr = r.getCopr();
                            String val_kpi = r.val_kpi;
                            String alias_val_kpi = r.val_kpi_alias;


                            List<Dimension> dimss = r.getDims();
                            String dimension = " ";
                            for (Dimension d : dimss) {
                                if (d == dimss.get(dimss.size() - 1)) {
                                    dimension = dimension + d.getVal_dim();
                                } else {
                                    dimension = dimension + d.getVal_dim() + " +'|'+ ";
                                }
                            }
                            System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by " + date + " ," + dimension);

                            PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                Rsl_test_sys blog = new Rsl_test_sys();
                                blog.setDate(rs.getString("date"));
                                blog.setIdKpi(rs.getFloat("Code_requete"));
                                blog.setVal_dim(rs.getString("val_dim"));
                                blog.setVal_kpi(rs.getFloat(alias_val_kpi));
                                blog.setSysteme("SqlServer");
//                            System.out.println("hetha  tee sqlserver"+blog);
                                rslt.add(blog);
                            }

                            con.close();
                        } else if (r.getId_databasee().getSystem().equals("Postgres")) {


                            try {
                                Connection conn = null;
                                Class.forName("org.postgresql.Driver");
                                conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                                        , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                                String date = r.getDate();
                                String copr = r.getCopr();
                                String val_kpi = r.val_kpi;
                                String alias_val_kpi = r.val_kpi_alias;


                                List<Dimension> dimss = r.getDims();
                                String dimension = " ";
                                for (Dimension d : dimss) {
                                    if (d == dimss.get(dimss.size() - 1)) {
                                        dimension = dimension + d.getVal_dim();
                                    } else {
                                        dimension = dimension + d.getVal_dim() + " ||'|'|| ";
                                    }
                                }
                                System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by " + date + " ," + dimension);

                                PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
                                ResultSet rs = ps.executeQuery();

                                while (rs.next()) {
                                    Rsl_test_sys blog = new Rsl_test_sys();
                                    blog.setDate(rs.getString("date"));
                                    blog.setIdKpi(rs.getFloat("Code_requete"));
                                    blog.setVal_dim(rs.getString("val_dim"));
                                    blog.setVal_kpi(rs.getFloat(alias_val_kpi));
                                    blog.setSysteme("Postgres");
//                                System.out.println("hetha  tee post"+blog);
                                    rslt.add(blog);
                                }

                                conn.close();
                            } catch (Exception e) {
                                System.out.println("Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
                            }
                        } else if (r.getId_databasee().getSystem().equals("Oracle"))
                            try {

                                Class.forName("oracle.jdbc.OracleDriver");
                                String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                                        + r.getId_databasee().getUsername() + r.getId_databasee().getPassword();
                                System.out.println();
                                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                                        , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());


                            } catch (Exception e) {
                                System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
                            }
                        else {

                            System.out.println("no connexions");
                        }

                    }
                }
            }
        }
//        System.out.println(rslt.size());
        for (Rsl_test_sys rs : rslt) {

            Rsl_test_sys r = new Rsl_test_sys();
            r.setDate(rs.getDate());
            r.setIdKpi(rs.getIdKpi());
            r.setVal_dim(rs.getVal_dim());
            r.setVal_kpi(rs.getVal_kpi());
            r.setSysteme(rs.getSysteme());
            r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
            rsltService.InsertOrUpdate(r);
        }
//	 List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                    , "postgres", "root");
            PreparedStatement ps = conn.prepareStatement("select  k.name_kpi as name ,a.idkpi  Code_requete , a.date as Date , a.valeur_dim as dim , a.val_kpi as val3  , b.val_kpi  as val4 ,0 as nbreRecordOk ,0 as nbreRecordNotOk,abs(a.val_kpi - b.val_kpi) as gap from database1 a   \r\n"
                    + "Full outer join database2 b  on  a.id_kpi = b.id_kpi and a.valeur_dim = b.valeur_dim and a.date = b.date\r\n"
                    + "left outer join kpi k on a.id_kpi=" + k.id_kpi + " \r\n"
                    + "where a.id_kpi = k.id_kpi \r\n");

            long startTime = System.currentTimeMillis();

            ResultSet rs = ps.executeQuery();
            PreparedStatement ps1 = conn.prepareStatement("Select count(*) as nbreRecordOK from vue_detaille where Code_requete=" + k.id_kpi + "  and date  between '" + dateDeb + "' and '" + dateFin + "' and nbre_record_ok = 1");
            ResultSet rs1 = ps1.executeQuery();
            PreparedStatement ps2 = conn.prepareStatement("Select count(*) as nbreRecordNotOk from vue_detaille where Code_requete=" + k.id_kpi + "  and date  between '" + dateDeb + "' and '" + dateFin + "' and nbre_record_not_ok = 1");
            ResultSet rs2 = ps2.executeQuery();
            PreparedStatement ps3 = conn.prepareStatement("Select sum(val_kpi1) as val3 ,sum(val_kpi2) as val4 ,abs(sum(val_kpi1)- sum(val_kpi2)) as gap from vue_detaille where Code_requete=" + k.id_kpi + "  and date  between '" + dateDeb + "' and '" + dateFin + "' ");
            ResultSet rs3 = ps3.executeQuery();
            PreparedStatement ps4 = conn.prepareStatement("select seuil as seuil , seuil_gap as seuil_gap , seuil_data_quality as seuil_dataQuality from  kpi  where id_kpi=" + k.id_kpi + "");
            ResultSet rs4 = ps4.executeQuery();
//            PreparedStatement ps5 = conn.prepareStatement("select date as datedeb from vue_detaille where date LIKE " +dateDeb+ " "  );
//            ResultSet rs5 = ps5.executeQuery();
            while (rs.next() && rs1.next() && rs2.next() && rs3.next() && rs4.next()) {
                System.out.println("pap");


//                Vue_Globale blog = new Vue_Globale();
//                System.out.println(dateDeb);
//                blog.setDateDeb(dateDeb);
//                blog.setDateFin(dateFin);
//                blog.setDate(rs.getString("Date"));
//                blog.setCode_requete(rs.getLong("Code_requete"));
//                blog.setVal_kpi1(rs3.getLong("val3"));
//                blog.setVal_kpi2(rs3.getLong("val4"));
//                blog.setName_kpi(rs.getString("name"));
//                blog.setSeuil_dataQuality(rs4.getFloat("seuil_dataQuality"));
//                blog.setSeuil(rs4.getFloat("seuil"));
//                blog.setSeuil_gap(rs4.getFloat("seuil_gap"));
////                blog.setLoad1(rs.getString("val1"));
////                blog.setLoad2(rs.getString("val2"));
//                //probleme au niveau de nbreRecordOk si on a un seul enregistrement
//                blog.setNbreRecordOk(rs1.getInt("nbreRecordOK"));
//                DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                Date dateobj = new Date();
//                blog.dateExec = (dfff.format(dateobj));
//                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//                blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//             
//                blog.setNbreRecordNotOk(rs2.getInt("nbreRecordNotOk"));
//       
//                blog.setDataQualite((rs1.getInt("nbreRecordOk"))*100f /  ((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk"))));
//                
//            
////                System.out.println((rs1.getInt("nbreRecordOK") / (Math.addExact(rs1.getInt("nbreRecordOk"), rs2.getInt("nbreRecordNotOk"))) )*100000);
////                System.out.println((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk")));
////                System.out.println((rs1.getInt("nbreRecordOk"))*100 /  ((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk"))));
//                System.out.println(rs);
////                System.out.println((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
////                blog.setDataQualite(
////                        ddf.format((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//
////                blog.setFoundDB1(rs.getInt("load1"));
////                blog.setFoundDB2(rs.getInt("load2"));
//            	blog.setGap((long) rs3.getInt("gap"));
//                java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
////                assert(Float.NaN, 0f / 0);
//                BigDecimal bigD = new BigDecimal((rs3.getInt("gap")   / (Math.max(rs3.getDouble("val4"), rs3.getDouble("val3")))* 100f));
//             	blog.setGAP_par_100(dff.format(bigD));
//                System.out.println(blog.getGap());
//                System.out.println(k.getSeuil_dataQuality());
//                System.out.println(k.getSeuil_gap());
//                System.out.println(k.getSeuil());
//                System.out.println(rs3.getInt("val4"));
//                System.out.println(rs3.getInt("val3"));
//                if(((rs3.getInt("gap") == 0) && ((rs1.getInt("nbreRecordOk"))*100f / ( ((rs1.getInt("nbreRecordOk"))+ (rs2.getInt("nbreRecordNotOk")))) ==100.0f))){
//                    blog.setAcceptation("OK");
//                }
//                else if(k.getSeuil_dataQuality()< ((rs1.getInt("nbreRecordOk"))*100f /  ((rs1.getInt("nbreRecordOk"))+ (rs2.getInt("nbreRecordNotOk"))))
//                        && k.getSeuil_gap()>(rs3.getInt("gap") )  ){
//                    blog.setAcceptation("OK partiel");
//                }else{
//                    blog.setAcceptation("NotOk");
//                }
//
//                rslt2.add(blog);
//            }
//
//            model.addAttribute("rslt", rslt2);
//
//        }

//        for (Vue_Globale r : rslt2){
//            rslRepository.save(r);
            }
        }
        watch.stop();
        System.out.println("Total execution time using StopWatch in millis: "
                + watch.getTotalTimeMillis());
        return "resultat3";

    }
//	
//	  @RequestMapping(value="/resultat5",method=RequestMethod.GET) public String
//	  GetScript5(Model model,@RequestParam("kpiii") String[ ] kpis
//	  ,@RequestParam("DateDeb") String dateDeb,@RequestParam("DateFin") String
//	  dateFin ,@RequestParam("db1")long db1 ,@RequestParam("db2")Long db2) throws
//	  Exception {
//	  
//	  
//	  if(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb).compareTo(new
//	  SimpleDateFormat("yyyy-MM-dd").parse(dateFin))>0){
//	  model.addAttribute("message","date Deb supérieur à date Fin"); return
//	  "resultat"; } Database d1 =dbrepository.findDatabaseById(db1); Database d2 =
//	  dbrepository.findDatabaseById(db2); List<Vue_Globale> rslt2 = new
//	  ArrayList<Vue_Globale>(); for (String s : kpis) { long kpi =
//	  Long.parseLong(s); Kpi k = kpirepository.findById(kpi).get();
//	  
//	  SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
//	  
//	  
//	  
//	  
//	  Connection conn = null; Class.forName("org.postgresql.Driver"); conn =
//	  DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4" ,
//	  "postgres", "root"); //requete pour afficher idkpi
//	  valkpi1,valkpi2,date,NbreRecord 1 , Nbre Record 2 PreparedStatement ps =
//	  conn.
//	  prepareStatement("select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n"
//	  +
//	  "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n"
//	  +
//	  " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n"
//	  + " (select T2.date,sum(T2.nbre) as nbre from \n" +
//	  " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n"
//	  + " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//	  " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n" +
//	  " select a.idkpi, count(a.valeur_dim) as nbre,a.date from rsl_test_sys a \n"
//	  + " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//	  " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//	  " group by T2.date\n" + " order by T2.date) as T3\n" + "  FULL OUTER JOIN\n"
//	  + " (select T2.date,sum(T2.nbre) as nbre from\n" +
//	  " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from rsl_test_sys a \n"
//	  + " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//	  " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n" +
//	  " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from rsl_test_sys a \n"
//	  + " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//	  " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//	  " group by T2.date\n" + " order by T2.date) as T4\n" +
//	  " ON   T3.date = T4.date) as RR1 \n" + "right OUTER JOIN\n" +
//	  " (select sum(T1.val_kpi) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n"
//	  +
//	  ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n"
//	  + ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" +
//	  ",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n"
//	  + " from " +
//	  " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n" +
//	  " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" +
//	  dateFin + "'  and  a.system='" + d2.getSystem() + "'\n" +
//	  " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
//	  " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from rsl_test_sys as a\n"
//	  + " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '"
//	  + dateFin + "' and a.system='" + d1.getSystem() + "'\n" +
//	  " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
//	  " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n"
//	  + " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n" +
//	  " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n" +
//	  "order by COALESCE(T1.date, B2.date)) as RR2\n" + " \n" +
//	  " on RR1.date=RR2.date\n" + " Left outer join\n" +
//	  " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n"
//	  +
//	  "(select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n"
//	  +
//	  "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n"
//	  +
//	  "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//	  + "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" +
//	  " group by a.date , a.idkpi  \n" + " order by a.date) as R1\n" +
//	  "full OUTER join  \n" +
//	  " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n"
//	  +
//	  "from rsl_test_sys as a FULL OUTER JOIN rsl_test_sys as b ON  a.idkpi=b.idkpi \n"
//	  +
//	  "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//	  + "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb +
//	  "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" +
//	  " group by a.date , a.idkpi  \n" + " order by a.date) as R2 \n" +
//	  " on R1.date=R2.date\n" + " order by R1.date) as RR3 \n" +
//	 " on RR2.date = RR3.date	");
//	 
//	  long startTime = System.currentTimeMillis();
//	  
//	  ResultSet rs = ps.executeQuery();
//	  
//	  
//	  while (rs.next()) { System.out.println("pap");
//	  
//	 
//	 Vue_Globale blog = new Vue_Globale(); blog.setDate(rs.getString("Date"));
//	 blog.setCode_requete(rs.getLong("Code_requete"));
//	  blog.setVal_kpi1(rs.getLong("val3")); blog.setVal_kpi2(rs.getLong("val4"));
//	  blog.setName_kpi(rs.getString("name")); blog.setLoad1(rs.getString("val1"));
//	  blog.setLoad2(rs.getString("val2"));
//	  blog.setNbreRecordOk(rs.getInt("nbreRecordOK")); DateFormat dfff = new
//	  SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
//	  blog.dateExec = (dfff.format(dateobj)); java.text.DecimalFormat ddf = new
//	  java.text.DecimalFormat("###.##"); blog.tempsExec =
//	  ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//	  
//	  blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk"));
//	  
//	  System.out.println((rs.getDouble("nbreRecordOk") /
//	 Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
//	  blog.setDataQualite( ddf.format((rs.getDouble("nbreRecordOk") /
//	  Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//	  
//	  blog.setFoundDB1(rs.getInt("load1")); blog.setFoundDB2(rs.getInt("load2"));
//	  blog.setGap((long) rs.getInt("gap")); java.text.DecimalFormat dff = new
//	  java.text.DecimalFormat("###.##");
//	  
//	  BigDecimal bigD = new BigDecimal((rs.getInt("gap") * 100) /
//	  (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//	  blog.setGAP_par_100(dff.format(bigD)); System.out.println(blog.getGap());
//	  if(((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"),
//	  rs.getDouble("val3")))==0) && (rs.getDouble("nbreRecordOk") /
//	  Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F==100.0){
//	  blog.setAcceptation("OK"); } else if(k.getSeuil_dataQuality()<
//	  (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"),
//	  rs.getDouble("val2"))) * 100F && k.getSeuil_gap()>(rs.getInt("gap") * 100) /
//	  (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))){
//	  blog.setAcceptation("OK partiel"); }else{ blog.setAcceptation("NotOk"); //NOT
//	  THIS ONE }
//	  
//	  rslt2.add(blog); }
//	  
//	  
//	 }  while(rs5.next()) { Vue_Globale blog = new Vue_Globale();
//	  blog.setDate(rs5.getString("Date"));
//	  blog.setCode_requete(rs5.getLong("Code_requete")); DateFormat dfff = new
//	  SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
//	 blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
//	  java.text.DecimalFormat("###.###");
//	  
//	  blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime2) /1000F);
//	  blog.setVal_kpi1( rs5.getLong("val3")); blog.setVal_kpi2((long) 0);
//	  blog.setName_kpi(rs5.getString("name"));
//	  blog.setLoad1(rs5.getString("val2")); blog.setLoad2("0");
//	  blog.setNbreRecordOk(0); blog.setNbreRecordNotOk(0);
//	  blog.setDataQualite("0"); blog.setFoundDB2(0);
//	  blog.setGap(rs5.getLong("val3")); java.text.DecimalFormat dff = new
//	  java.text.DecimalFormat("###"); System.out.println(); BigDecimal bigD = new
//	  BigDecimal((Math.abs ( rs5.getDouble("val3")) *100)/ rs5.getDouble("val3"));
//	  blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
//	  
//	  
//	  } while(rs6.next()) { Vue_Globale blog = new Vue_Globale();
//	  blog.setDate(rs6.getString("Date"));
//	 blog.setCode_requete(rs6.getLong("Code_requete")); DateFormat dfff = new
//	 SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
//	 blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
//	  java.text.DecimalFormat("###.###");
//	  
//	  blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime3) /1000F);
//	  blog.setVal_kpi1( rs6.getLong("val3")); blog.setVal_kpi2((long) 0);
//	  blog.setName_kpi(rs6.getString("name"));
//	  blog.setLoad1(rs6.getString("val2")); blog.setLoad2("0");
//	 blog.setDataQualite("0"); blog.setNbreRecordOk(0);
//	  blog.setNbreRecordNotOk(0); blog.setFoundDB1( rs6.getInt("val2"));
//	  blog.setFoundDB2(0); blog.setGap(rs6.getLong("val3"));
//	  java.text.DecimalFormat dff = new java.text.DecimalFormat("###");
//	  System.out.println(); BigDecimal bigD = new BigDecimal((Math.abs (
//	  rs6.getDouble("val3")) *100)/ rs6.getDouble("val3"));
//	  blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
//	  
//	  
//	 }
//	 
//
//	 model.addAttribute("rslt", rslt2);
//
//        for (Vue_Globale r : rslt2){
//            rslRepository.save(r);
//        }
//
//
//
//        return "resultat2";*/
//
//    }
//	@GetMapping("/Vue_Global")
//	public String getAllRslt(Model model) {
//		try {
//			model.addAttribute("rslt", rsltService.getCompKpi());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return "Vue_Global";
//	}

    @GetMapping("/VueDetailleDataTable")
    @ResponseBody
    public Map<String, Object> getAllRsltDetaille(
                                     @RequestParam("kpiii") String[] kpis,
                                     @RequestParam("DateDeb") String dateDeb,
                                     @RequestParam("DateFin") String dateFin,
                                     @RequestParam("draw") int draw,
                                     @RequestParam("page") int page,
                                     @RequestParam("size") int size,
                                     @RequestParam("search[value]") String searchValue,
                                     @RequestParam("order[0][column]") int sortColumnIndex,
                                     @RequestParam("order[0][dir]") String sortDirection) throws Exception {

        List<Vue_Detaillé> rslt2 = new ArrayList<>();
        List<Vue_Detaillé> rsltForCsv = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();

        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
  //          Database d1 = dbrepository.findDatabaseById(db1);
//			Database d2 = dbrepository.findDatabaseById(db2);
            String whereClause = "";
            whereClause = " WHERE name_kpi ILIKE '%" + searchValue + "%'"
                    + " OR CAST(code_requete AS TEXT) ILIKE '" + searchValue + "%'"
                    + " OR date ILIKE '" + searchValue + "%'"
                    + " OR val_dim ILIKE '" + searchValue + "%'"
                    + " OR CAST(val_kpi1 AS TEXT) ILIKE '" + searchValue + "%'"
                    + " OR CAST(val_kpi2 AS TEXT) ILIKE '" + searchValue + "%'"
                    + " OR CAST(gap AS TEXT) ILIKE '" + searchValue + "%'"
                    + " OR CAST(nbre_record_ok AS TEXT) ILIKE '" + searchValue + "%'"
                    + " OR CAST(nbre_record_not_ok AS TEXT) ILIKE '" + searchValue + "%'";

            String[] columnNames = {"date", "code_requete", "val_kpi1", "val_kpi2", "name_kpi", "val_dim", "gap", "GAP_par_100", "nbreRecordNotOk"};
            // Build the ORDER BY clause of the SQL query based on the sort column index and direction
            String orderByClause = "";
            if (sortColumnIndex >= 0) {

                String columnName = columnNames[sortColumnIndex];
                orderByClause = " ORDER BY " + columnName + " " + sortDirection;
            }
            String kpiList = Arrays.stream(kpis).map(k -> "'" + k + "'").collect(Collectors.joining(","));
            PreparedStatement ps = conn
                    .prepareStatement(
                            "        select coalesce (a.id_kpi,b.id_kpi ) as Code_requete\r\n"
                                    + "        , coalesce (a.date, b.date) as Date\r\n"
                                    + "        , coalesce (a.valeur_dim,b.valeur_dim) as valeur_dim\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0))  as val_kpi_a\r\n"
                                    + "        , sum ( coalesce (b.val_kpi,0)) as val_kpi_b\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)) as gap\r\n"
                                    + "        , 100*abs (sum (coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) / GREATEST  (abs(sum ( coalesce (a.val_kpi,0))), abs (sum ( coalesce (b.val_kpi,0))) ) as gap_par_100\r\n"
                                    + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) >  K.seuil) then 1 else 0 end as nbre_record_not_ok\r\n"
                                    + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                                    + "        from database1 a\r\n"
                                    + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                                    + "        and a.valeur_dim = b.valeur_dim\r\n"
                                    + "        and a.date = b.date\r\n"
                                    + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                                    + "        WHERE 1=1 \r\n"
                                    + "        AND coalesce(a.id_kpi,b.id_kpi) IN (" + kpiList + ") "
                                    + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                                    + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                                    + "    AND CASE WHEN (ABS(coalesce(a.val_kpi, 0) - coalesce(b.val_kpi, 0))) > K.seuil THEN 1 ELSE 0 END = 1\r\n"
                                    + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n"
                                    + orderByClause
                                    + " OFFSET ? LIMIT ?");
            PreparedStatement psForCsv = conn
                    .prepareStatement(
                            "        select coalesce (a.id_kpi,b.id_kpi ) as Code_requete\r\n"
                                    + "        , coalesce (a.date, b.date) as Date\r\n"
                                    + "        , coalesce (a.valeur_dim,b.valeur_dim) as valeur_dim\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0))  as val_kpi_a\r\n"
                                    + "        , sum ( coalesce (b.val_kpi,0)) as val_kpi_b\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)) as gap\r\n"
                                    + "        , 100*abs (sum (coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) / GREATEST  (abs(sum ( coalesce (a.val_kpi,0))), abs (sum ( coalesce (b.val_kpi,0))) ) as gap_par_100\r\n"
                                    + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) >  K.seuil) then 1 else 0 end as nbre_record_not_ok\r\n"
                                    + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                                    + "        from database1 a\r\n"
                                    + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                                    + "        and a.valeur_dim = b.valeur_dim\r\n"
                                    + "        and a.date = b.date\r\n"
                                    + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                                    + "        WHERE 1=1 \r\n"
                                    + "        AND coalesce(a.id_kpi,b.id_kpi) IN (" + kpiList + ") "
                                    + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                                    + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                                    + "    AND CASE WHEN (ABS(coalesce(a.val_kpi, 0) - coalesce(b.val_kpi, 0))) > K.seuil THEN 1 ELSE 0 END = 1\r\n"
                                    + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n"
                                    + orderByClause);
            ResultSet rsForCsv = psForCsv.executeQuery();
            while (rsForCsv.next()) {
                Vue_Detaillé blog = new Vue_Detaillé();
                blog.setDate(rsForCsv.getString("date"));
                blog.setCode_requete(rsForCsv.getLong("code_requete"));
                blog.setVal_kpi1(rsForCsv.getFloat("val_kpi_a"));
                blog.setVal_kpi2(rsForCsv.getFloat("val_kpi_b"));
                blog.setName_kpi(rsForCsv.getString("name"));
                blog.setVal_dim(rsForCsv.getString("valeur_dim"));
                blog.setGap(rsForCsv.getFloat("gap"));
                blog.setGAP_par_100(rsForCsv.getString("gap_par_100"));
                blog.setNbreRecordNotOk(rsForCsv.getInt("nbre_record_not_ok"));


                rsltForCsv.add(blog);


            }
            PreparedStatement countStatement = conn.prepareStatement(
                    "SELECT COUNT(*) FROM (" +
                            "        select coalesce (a.id_kpi,b.id_kpi ) as Code_requete\r\n"
                            + "        , coalesce (a.date, b.date) as Date\r\n"
                            + "        , coalesce (a.valeur_dim,b.valeur_dim) as valeur_dim\r\n"
                            + "        , sum ( coalesce (a.val_kpi,0))  as val_kpi_a\r\n"
                            + "        , sum ( coalesce (b.val_kpi,0)) as val_kpi_b\r\n"
                            + "        , sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)) as gap\r\n"
                            + "        , 100*abs (sum (coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) / GREATEST  (abs(sum ( coalesce (a.val_kpi,0))), abs (sum ( coalesce (b.val_kpi,0))) ) as gap_par_100\r\n"
                            + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) >  K.seuil) then 1 else 0 end as nbre_record_not_ok\r\n"
                            + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                            + "        from database1 a\r\n"
                            + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                            + "        and a.valeur_dim = b.valeur_dim\r\n"
                            + "        and a.date = b.date\r\n"
                            + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                            + "        WHERE 1=1 \r\n"
                            + "        AND coalesce(a.id_kpi,b.id_kpi) IN (" + kpiList + ") "
                            + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                            + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                            + "    AND CASE WHEN (ABS(coalesce(a.val_kpi, 0) - coalesce(b.val_kpi, 0))) > K.seuil THEN 1 ELSE 0 END = 1\r\n"
                            + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n"+
                            ") AS subquery");
            ps.setInt(1, page * size);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int totalSet = countResult.getInt(1);
            while (rs.next()) {
                Vue_Detaillé blog = new Vue_Detaillé();
                blog.setDate(rs.getString("date"));
                blog.setCode_requete(rs.getLong("code_requete"));
                blog.setVal_kpi1(rs.getFloat("val_kpi_a"));
                blog.setVal_kpi2(rs.getFloat("val_kpi_b"));
                blog.setName_kpi(rs.getString("name"));
                blog.setVal_dim(rs.getString("valeur_dim"));
                blog.setGap(rs.getFloat("gap"));
                blog.setGAP_par_100(rs.getString("gap_par_100"));
                blog.setNbreRecordNotOk(rs.getInt("nbre_record_not_ok"));


                rslt2.add(blog);


            }
            for (Vue_Detaillé r : rslt2) {
                vue_detailsRepository.save(r);
            }
            res.put("draw", draw);
            res.put("recordsTotal", totalSet);
            res.put("data", rslt2);
            res.put("recordsFiltered", totalSet);

            return res;
            // Your code here

        } catch (SQLException e) {
            // Handle any exceptions that occur during the database operation
            e.printStackTrace();
            Map<String,Object> messageError = new HashMap<>();
            messageError.put("message",e);
            return messageError;
        } finally {
            // Close the connection in the finally block to ensure it always gets closed
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
//
    }

    @GetMapping("Vue_detaille")
    public String Vue_detaillePage(Model model) {
        return "Vue_detaille";
    }

    @GetMapping("resultat4")
    public String resultat4(Model model) {
        return "resultat4";
    }

    ///////////////////////////////////////////////
    @GetMapping(value = "/resultat4Table", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getAllRsltDetailles(Model model,
                                                   @RequestParam("draw") int draw,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("search[value]") String searchValue,
                                                   @RequestParam("order[0][column]") int sortColumnIndex,
                                                   @RequestParam("order[0][dir]") String sortDirection) throws Exception {

        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");

        // Build the WHERE clause of the SQL query based on the search value
        String whereClause = "";
        whereClause = " WHERE name_kpi ILIKE '%" + searchValue + "%'"
                + " OR CAST(code_requete AS TEXT) ILIKE '" + searchValue + "%'"
                + " OR date ILIKE '" + searchValue + "%'"
                + " OR val_dim ILIKE '" + searchValue + "%'"
                + " OR CAST(val_kpi1 AS TEXT) ILIKE '" + searchValue + "%'"
                + " OR CAST(val_kpi2 AS TEXT) ILIKE '" + searchValue + "%'"
                + " OR CAST(gap AS TEXT) ILIKE '" + searchValue + "%'"
                + " OR CAST(nbre_record_ok AS TEXT) ILIKE '" + searchValue + "%'"
                + " OR CAST(nbre_record_not_ok AS TEXT) ILIKE '" + searchValue + "%'";


        // Build the ORDER BY clause of the SQL query based on the sort column index and direction
        String orderByClause = "";
        if (sortColumnIndex >= 0) {
            String[] columnNames = {"code_requete", "date", "name_kpi", "val_dim", "val_kpi1", "val_kpi2", "gap", "nbreRecordOk", "nbreRecordNotOk"};
            String columnName = columnNames[sortColumnIndex];
            orderByClause = " ORDER BY " + columnName + " " + sortDirection;
        }

        // Get the total number of records
        String countQuery = "SELECT COUNT(*) FROM vue_detaille" + whereClause;
        PreparedStatement countStmt = conn.prepareStatement(countQuery);
        ResultSet countRs = countStmt.executeQuery();
        countRs.next();
        int total = countRs.getInt(1);
        String query = "SELECT * FROM vue_detaille " + whereClause + orderByClause + " OFFSET ? LIMIT ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, page * size);
        ps.setInt(2, size);
        ResultSet rs = ps.executeQuery();
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> rslt2 = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            Vue_Detaillé blog = new Vue_Detaillé();
            blog.setDate(rs.getString("Date"));
            blog.setCode_requete(rs.getLong("Code_requete"));
            blog.setVal_kpi1(rs.getFloat("val_kpi1"));
            blog.setVal_kpi2(rs.getFloat("val_kpi2"));
            blog.setName_kpi(rs.getString("name_kpi"));
            blog.setVal_dim(rs.getString("val_dim"));
            blog.setGap(rs.getFloat("gap"));
            blog.setGAP_par_100(rs.getString("gap_par_100"));
            blog.setNbreRecordNotOk(rs.getInt("nbre_record_not_ok"));
            blog.setNbreRecordOk(rs.getInt("nbre_record_ok"));
            map.put("code_requete", blog.getCode_requete());
            map.put("date", blog.getDate());
            map.put("name_kpi", blog.getName_kpi());
            map.put("val_dim", blog.getVal_dim());
            map.put("val_kpi1", blog.getVal_kpi1());
            map.put("val_kpi2", blog.getVal_kpi2());
            map.put("gap", blog.getGap());
            map.put("nbreRecordOk", blog.getNbreRecordOk());
            map.put("nbreRecordNotOk", blog.getNbreRecordNotOk());
            map.put("total", total);
            rslt2.add(map);
        }
        res.put("draw", draw);
        res.put("recordsTotal", total);
        res.put("data", rslt2);
        res.put("recordsFiltered", total);
        return res;
    }


//---------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/resultat5")
    public String getGV(Model model) throws Exception {

        List<Vue_Globale> rslt00 = new ArrayList<Vue_Globale>();

        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");

        long startTime = System.currentTimeMillis();


        PreparedStatement ps007 = conn.prepareStatement(
                "			SELECT * from vue_globale\r\n"
        );


        ResultSet rs007 = ps007.executeQuery();
        while (rs007.next()) {
//             System.out.println("pap");

//             System.out.println(rs007.getString("acceptation"));

            Vue_Globale blog = new Vue_Globale();

            blog.setDateDeb(rs007.getString("date_deb"));
            blog.setDateFin(rs007.getString("date_fin"));
            blog.setCode_requete(rs007.getLong("code_requete"));
            blog.setVal_kpi1(rs007.getFloat("val_kpi1"));
            blog.setVal_kpi2(rs007.getFloat("val_kpi2"));
            blog.setGap(rs007.getLong("gap"));
            blog.setGAP_par_100(rs007.getString("gap_par_100"));
            blog.setNbreRecordNotOk(rs007.getInt("nbre_record_not_ok"));
            blog.setNbreRecordOk(rs007.getInt("nbre_record_ok"));
            blog.setDataQualite(rs007.getFloat("data_qualite"));
            blog.setAcceptation(rs007.getString("acceptation"));
            blog.setName_kpi(rs007.getString("name_kpi"));
            blog.setSeuil_dataQuality(rs007.getFloat("seuil_data_quality"));
            blog.setSeuil(rs007.getFloat("seuil"));
            blog.setSeuil_gap(rs007.getFloat("seuil_gap"));

            DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            blog.dateExec = (dfff.format(dateobj));
            java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
            blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime));


            rslt00.add(blog);
        }

        model.addAttribute("rslt", rslt00);


        return "resultat5";

    }

//----------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------


    @RequestMapping(value = "/Vue_Globale", method = RequestMethod.GET)
    public String Getres1574(Model model


            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
        // LocalDate localDate = LocalDate.now();
        // while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;

        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);

        List<Rsl_test_sys> rslt = new ArrayList<Rsl_test_sys>();

//        System.out.println(rslt.size());
        for (Rsl_test_sys rs : rslt) {

            Rsl_test_sys r = new Rsl_test_sys();
            r.setDate(rs.getDate());
            r.setIdKpi(rs.getIdKpi());
            r.setVal_dim(rs.getVal_dim());
            r.setVal_kpi(rs.getVal_kpi());
            r.setSysteme(rs.getSysteme());
            r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
            rsltService.InsertOrUpdate(r);
        }
        List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();


        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");
//            PreparedStatement ps = conn.prepareStatement("select  k.name_kpi as name ,a.idkpi  Code_requete , a.date as Date , a.valeur_dim as dim , a.val_kpi as val3  , b.val_kpi  as val4 ,0 as nbreRecordOk ,0 as nbreRecordNotOk,abs(a.val_kpi - b.val_kpi) as gap from database1 a   \r\n"
//					+"Full outer join database2 b  on  a.id_kpi = b.id_kpi and a.valeur_dim = b.valeur_dim and a.date = b.date\r\n"
//					+"left outer join kpi k on a.id_kpi="+k.id_kpi+" \r\n"
//					+"where a.id_kpi = k.id_kpi \r\n");

        long startTime = System.currentTimeMillis();
//
//            ResultSet rs = ps.executeQuery();
        PreparedStatement ps1 = conn.prepareStatement("Select count(*) as nbreRecordOK from vue_detaille where nbre_record_ok = 1");
        ResultSet rs1 = ps1.executeQuery();
        PreparedStatement ps2 = conn.prepareStatement("Select count(*) as nbreRecordNotOk from vue_detaille where nbre_record_not_ok = 1");
        ResultSet rs2 = ps2.executeQuery();
        PreparedStatement ps3 = conn.prepareStatement("Select sum(val_kpi1) as val3 ,sum(val_kpi2) as val4 ,abs(sum(val_kpi1)- sum(val_kpi2)) as gap from vue_detaille ");
        ResultSet rs3 = ps3.executeQuery();
//            PreparedStatement ps4 = conn.prepareStatement("select seuil as seuil , seuil_gap as seuil_gap , seuil_data_quality as seuil_dataQuality from  kpi  where id_kpi="+k.id_kpi +"" );
//            ResultSet rs4 = ps4.executeQuery();	
        PreparedStatement ps5 = conn.prepareStatement("select min(date) as datedeb , max(date) as datefin from vue_detaille");
        ResultSet rs5 = ps5.executeQuery();
        PreparedStatement ps6 = conn.prepareStatement("select code_requete as Code_requete , name_kpi as name from vue_detaille");
        ResultSet rs6 = ps6.executeQuery();
        PreparedStatement ps7 = conn.prepareStatement
                ("  	select  code_requete as Code_requete , name_kpi as name   ,\r\n"
                        + "		sum(case when nbre_record_ok = 1 then 1 else 0 end) as nbreRecordOK,\r\n"
                        + "     sum(case when nbre_record_not_ok = 1 then 1 else 0 end) as nbreRecordNotOk,\r\n"
                        + "	    sum(val_kpi1) as val3,\r\n"
                        + "	    sum(val_kpi2) as val4,\r\n"
                        + "	    sum(gap) as gap,\r\n"
                        + "	    sum(CAST(gap_par_100 as float)) as gaptot\r\n"
                        + "     \r\n"
                        + "		from vue_detaille\r\n"
                        + "		group by name_kpi  , code_requete ");
        ResultSet rs7 = ps7.executeQuery();
        PreparedStatement ps8 = conn.prepareStatement
                ("  	select distinct k.id_kpi as name ,a.Code_requete , k.seuil_gap as seuil_gap , k.seuil as seuil , k.seuil_data_quality as seuil_dataQuality  from vue_detaille a  \r\n"
                        + "		left outer join kpi k on k.id_kpi=a.Code_requete   ");
        ResultSet rs8 = ps8.executeQuery();
        while (rs1.next() && rs2.next() && rs3.next() && rs5.next() && rs6.next() && rs7.next() && rs8.next()) {
//                System.out.println("pap");

//
//                Vue_Globale blog = new Vue_Globale();
//     
//                blog.setDateDeb(rs5.getString("datedeb"));
//                blog.setDateFin(rs5.getString("datefin"));
//         //       blog.setDate(rs.getString("Date"));
//                blog.setCode_requete(rs7.getLong("Code_requete"));
//                blog.setVal_kpi1(rs7.getLong("val3"));
//                blog.setVal_kpi2(rs7.getLong("val4"));
//                blog.setName_kpi(rs7.getString("name"));
//                
//                blog.setSeuil_dataQuality(rs8.getFloat("seuil_dataQuality"));
//                blog.setSeuil(rs8.getFloat("seuil"));
//                blog.setSeuil_gap(rs8.getFloat("seuil_gap"));
////                blog.setLoad1(rs.getString("val1"));
////                blog.setLoad2(rs.getString("val2"));
//                //probleme au niveau de nbreRecordOk si on a un seul enregistrement
//                blog.setNbreRecordOk(rs7.getInt("nbreRecordOK"));
//                DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                Date dateobj = new Date();
//                blog.dateExec = (dfff.format(dateobj));
//                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//                blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//             
//                blog.setNbreRecordNotOk(rs2.getInt("nbreRecordNotOk"));
//       
//                blog.setDataQualite((rs1.getInt("nbreRecordOk"))*100f /  ((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk"))));
//                
//            
////                System.out.println((rs1.getInt("nbreRecordOK") / (Math.addExact(rs1.getInt("nbreRecordOk"), rs2.getInt("nbreRecordNotOk"))) )*100000);
////                System.out.println((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk")));
////                System.out.println((rs1.getInt("nbreRecordOk"))*100 /  ((rs2.getInt("nbreRecordNotOk"))+ (rs1.getInt("nbreRecordOk"))));
//             //   System.out.println(rs);
////                System.out.println((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
////                blog.setDataQualite(
////                        ddf.format((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//
////                blog.setFoundDB1(rs.getInt("load1"));
////                blog.setFoundDB2(rs.getInt("load2"));
//            	blog.setGap((long) rs3.getInt("gap"));
//                java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
////                assert(Float.NaN, 0f / 0);
//                BigDecimal bigD = new BigDecimal((rs3.getInt("gap")   / (Math.max(rs3.getDouble("val4"), rs3.getDouble("val3")))* 100f));
//             	blog.setGAP_par_100(dff.format(bigD));
//                System.out.println(blog.getGap());
////                System.out.println(k.getSeuil_dataQuality());
////                System.out.println(k.getSeuil_gap());
////                System.out.println(k.getSeuil());
//                System.out.println(rs3.getInt("val4"));
//                System.out.println(rs3.getInt("val3"));
//                if(((rs3.getInt("gap") == 0) && ((rs1.getInt("nbreRecordOk"))*100f / ( ((rs1.getInt("nbreRecordOk"))+ (rs2.getInt("nbreRecordNotOk")))) ==100.0f))){
//                    blog.setAcceptation("OK");
//                }
//                else if(rs8.getFloat("seuil_dataQuality")< ((rs1.getInt("nbreRecordOk"))*100f /  ((rs1.getInt("nbreRecordOk"))+ (rs2.getInt("nbreRecordNotOk"))))
//                        && rs8.getFloat("seuil_gap")>(rs3.getInt("gap") )  ){
//                    blog.setAcceptation("OK partiel");
//                }else{
//                    blog.setAcceptation("NotOk");
//                }
//
//                rslt2.add(blog);
//            }

            model.addAttribute("rslt", rslt2);


//        for (Vue_Globale r : rslt2){
//            rslRepository.save(r);
        }

        return "resultat3";

    }


    //------------------------------------------------------------------VueGlobal----------------------------------------------------//

    public static final Logger logger = LoggerFactory.getLogger(Rsl_Test_SysController.class);

    @RequestMapping(value = "/LoadAllData", method = RequestMethod.GET)
    public String GetRes10(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
                           @RequestParam("DateFin") String dateFin
            , @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {

        if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
                .compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
            model.addAttribute("message", "date Deb supérieur à date Fin");
            return "resultat10";
        }
        model.addAttribute("rslt", loadDataHelper.loadAllData(kpis,dateDeb,dateFin,db1,db2).get("vueGlobale").get(0));
        return "resultat3";
    }
    @PostMapping("sendResultMail")
    @ResponseBody
    public String sendResultMail(@RequestParam("kpiii") String[] kpis,
                                 @RequestParam("email") String[] emails,
                                 @RequestParam("DateDeb") String dateDeb,
                                 @RequestParam("DateFin") String dateFin) throws Exception{
        List<Vue_Detaillé> rsltForCsv = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
            String[] columnNames = {"date", "code_requete", "val_kpi1", "val_kpi2", "name_kpi", "val_dim", "gap", "GAP_par_100", "nbreRecordNotOk"};
            String kpiList = Arrays.stream(kpis).map(k -> "'" + k + "'").collect(Collectors.joining(","));
            PreparedStatement psForCsv = conn
                    .prepareStatement(
                            "        select coalesce (a.id_kpi,b.id_kpi ) as Code_requete\r\n"
                                    + "        , coalesce (a.date, b.date) as Date\r\n"
                                    + "        , coalesce (a.valeur_dim,b.valeur_dim) as valeur_dim\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0))  as val_kpi_a\r\n"
                                    + "        , sum ( coalesce (b.val_kpi,0)) as val_kpi_b\r\n"
                                    + "        , sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)) as gap\r\n"
                                    + "        , 100*abs (sum (coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) / GREATEST  (abs(sum ( coalesce (a.val_kpi,0))), abs (sum ( coalesce (b.val_kpi,0))) ) as gap_par_100\r\n"
                                    + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) >  K.seuil) then 1 else 0 end as nbre_record_not_ok\r\n"
                                    + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                                    + "        from database1 a\r\n"
                                    + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                                    + "        and a.valeur_dim = b.valeur_dim\r\n"
                                    + "        and a.date = b.date\r\n"
                                    + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                                    + "        WHERE 1=1 \r\n"
                                    + "        AND coalesce(a.id_kpi,b.id_kpi) IN (" + kpiList + ") "
                                    + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                                    + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                                    + "    AND CASE WHEN (ABS(coalesce(a.val_kpi, 0) - coalesce(b.val_kpi, 0))) > K.seuil THEN 1 ELSE 0 END = 1\r\n"
                                    + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n");
            ResultSet rsForCsv = psForCsv.executeQuery();
            List<Vue_Globale> rsltGlobal = getResultFromVueGlobale(kpis,dateDeb,dateFin);
            while (rsForCsv.next()) {
                Vue_Detaillé blog = new Vue_Detaillé();
                blog.setDate(rsForCsv.getString("date"));
                blog.setCode_requete(rsForCsv.getLong("code_requete"));
                blog.setVal_kpi1(rsForCsv.getFloat("val_kpi_a"));
                blog.setVal_kpi2(rsForCsv.getFloat("val_kpi_b"));
                blog.setName_kpi(rsForCsv.getString("name"));
                blog.setVal_dim(rsForCsv.getString("valeur_dim"));
                blog.setGap(rsForCsv.getFloat("gap"));
                blog.setGAP_par_100(rsForCsv.getString("gap_par_100"));
                blog.setNbreRecordNotOk(rsForCsv.getInt("nbre_record_not_ok"));
                rsltForCsv.add(blog);
            }
            vueDetailSevice.writeObjectsToCsv(rsltForCsv,columnNames,rsltGlobal,emails);
        }
        catch (Exception e){
            return "something went wrong " + e.getMessage();
        }
        return "E-mail has been sent successfully !";
    }

    public List<Vue_Globale> getResultFromVueGlobale(String[] kpis, String dateDeb, String dateFin) throws Exception {
        List<Vue_Globale> rsltGlobal = new ArrayList<>();
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                , "postgres", "root");

        long startTime = System.currentTimeMillis();
        String kpiList = Arrays.stream(kpis).map(k -> "'" + k + "'").collect(Collectors.joining(","));

        PreparedStatement ps007 = conn.prepareStatement(
                "			SELECT Code_requete as Code_requete\r\n"
                        + "        ,sum(val_kpi1) AS Total_System_1\r\n"
                        + "        ,sum(val_kpi2) AS Total_System_2\r\n"
                        + "        ,sum(gap) AS gap_Total\r\n"
                        + "        ,100* abs(sum(gap)) / GREATEST(abs(sum(val_kpi1)), abs(sum(val_kpi2))) AS gap_par_100\r\n"
                        + "        ,sum(nbre_record_not_ok) AS nbre_record_not_ok\r\n"
                        + "        ,sum(nbre_record_ok) AS nbre_record_ok\r\n"
                        + "        ,100 * sum(nbre_record_ok) / (sum(nbre_record_not_ok) + sum(nbre_record_ok)) AS data_Quality\r\n"
                        + "        ,case when abs(sum(gap))  = 0 AND sum(nbre_record_not_ok) =0 THEN 'OK' \r\n"
                        + "         when  ( abs(sum(gap)) / GREATEST(abs(sum(val_kpi1)), abs(sum(val_kpi2)))  <= K.seuil_gap/100  ) \r\n"
                        + "                      AND 100 * sum(nbre_record_ok) / (sum(nbre_record_not_ok) + sum(nbre_record_ok)) >= K.seuil_data_quality\r\n"
                        + "                          THEN 'Partially OK'\r\n"
                        + "                        Else 'NOT OK'\r\n"
                        + "                END as acceptation        \r\n"
                        + "        ,K.name_kpi, K.seuil,  K.seuil_data_quality, K.seuil_gap \r\n"
                        + "FROM vue_detaille A \r\n"
                        + "LEFT JOIN kpi K on A.code_requete = K.id_kpi\r\n"
                        + "WHERE code_requete IN (" + kpiList + ")   AND DATE BETWEEN '" + dateDeb + "' AND '" + dateFin + "'\r\n"
                        + "GROUP BY code_requete, K.name_kpi, K.seuil , K.seuil_data_quality, K.seuil_gap \r\n");


        ResultSet rs007 = ps007.executeQuery();
        while (rs007.next()) {

            Vue_Globale blog = new Vue_Globale();

            blog.setDateDeb(dateDeb);
            blog.setDateFin(dateFin);
            blog.setCode_requete(rs007.getLong("Code_requete"));
            blog.setVal_kpi1(rs007.getFloat("Total_System_1"));
            blog.setVal_kpi2(rs007.getFloat("Total_System_2"));
            blog.setGap(rs007.getLong("gap_Total"));
            blog.setGAP_par_100(rs007.getString("gap_par_100"));
            blog.setNbreRecordNotOk(rs007.getInt("nbre_record_not_ok"));
            blog.setNbreRecordOk(rs007.getInt("nbre_record_ok"));
            blog.setDataQualite(rs007.getFloat("data_Quality"));
            blog.setAcceptation(rs007.getString("acceptation"));
            blog.setName_kpi(rs007.getString("name_kpi"));
            blog.setSeuil_dataQuality(rs007.getFloat("seuil_data_quality"));
            blog.setSeuil(rs007.getFloat("seuil"));
            blog.setSeuil_gap(rs007.getFloat("seuil_gap"));
            DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            blog.dateExec = (dfff.format(dateobj));
            java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
            blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime));

            rsltGlobal.add(blog);
        }
        return rsltGlobal;
    }

    @GetMapping("/schedule")
    @ResponseBody
    public Map<String, Object> getPlanTask(@RequestParam(name = "kpiii", required = false) String[] kpis,
                                           @RequestParam(name = "DateDeb", required = false) String dateDeb,
                                           @RequestParam(name = "DateFin", required = false) String dateFin,
                                           @RequestParam(name = "db1", required = false) long db1,
                                           @RequestParam(name = "db2", required = false) Long db2,
                                           @RequestParam(name = "scheduleName", required = false) String scheduleName,
                                           @RequestParam(name = "triggerType", required = false) String triggerType,
                                           @RequestParam(name = "endAtDaily", required = false) String endAtDaily,
                                           @RequestParam(name = "endAtMonthly", required = false) String endAtMonthly,
                                           @RequestParam(name = "endAtWeekly", required = false) String endAtWeekly,
                                           @RequestParam(name = "dailyExecutionTime", required = false) LocalTime dailyExecutionTime,
                                           @RequestParam(name = "MonthlyExecutionTime", required = false) LocalTime MonthlyExecutionTime,
                                           @RequestParam(name = "weeklyExecutionTime", required = false) LocalTime weeklyExecutionTime,
                                           @RequestParam(name = "dailyDaysOfWeek", required = false) String dailyDaysOfWeek,
                                           @RequestParam(name = "weeklyDaysOfWeek", required = false) String WeeklyDaysOfWeek,
                                           @RequestParam(name = "monthlyDateOfMonth", required = false) Integer MonthlyDateOfMonth) {

        // Create a map to store the response data
        Map<String, Object> objectMap = new HashMap<>();
        String jsonArray = new Gson().toJson(kpis);
        try {
            // Setup Quartz scheduler
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.start();

            // Create a trigger based on the trigger type selected
            Trigger trigger;
            if ("DAILY".equals(triggerType)) {
                // For Daily schedule
                trigger = schedulerHelper.createDailyTrigger(scheduleName, dailyExecutionTime,dailyDaysOfWeek,endAtDaily);

            } else if ("WEEKLY".equals(triggerType)) {
                // For Weekly schedule
                trigger = schedulerHelper.createWeeklyTrigger(scheduleName,weeklyExecutionTime, WeeklyDaysOfWeek,endAtWeekly);
            } else if ("MONTHLY".equals(triggerType)) {
                // For Monthly schedule
                trigger = schedulerHelper.createMonthlyTrigger(scheduleName,MonthlyExecutionTime, MonthlyDateOfMonth,endAtMonthly);
            } else {
                throw new IllegalArgumentException("Invalid trigger type: " + triggerType);
            }
            TaskScheduler taskScheduler =
                    new TaskScheduler(scheduleName,TriggerType.valueOf(triggerType),trigger.getEndTime());
            taskScheduler = taskSchedulerRepository.save(taskScheduler);
            // Create a job detail
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(scheduleName, "group1")
                    .usingJobData("kpi",jsonArray)
                    .usingJobData("dateDeb",dateDeb)
                    .usingJobData("dateFin",dateFin)
                    .usingJobData("database1",db1)
                    .usingJobData("database2",db2)
                    .usingJobData("idScheduler", taskScheduler.getId())
                    .usingJobData("scheduleName", scheduleName)
                    .build();

            // Schedule the job with the trigger
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("previous job execution : " + trigger.getPreviousFireTime() + "Next job execution time: " + trigger.getNextFireTime());
            // Provide some success response
            objectMap.put("status", "Task scheduled successfully");
        } catch (SchedulerException e) {
            e.printStackTrace();
            // Provide error response
            objectMap.put("error", "Error scheduling task: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return objectMap;
    }
    @GetMapping("tasksList")
    public String listTasks(Model model){
        List<TaskScheduler> taskSchedulers = taskSchedulerRepository.findAll();
        model.addAttribute("listTasks",taskSchedulers);
        return "TaskList";
    }
    @GetMapping("getDetailedExecutions/{id}")
    @ResponseBody
    public Set<TriggerInformation> getDetailedExecutions(@PathVariable("id") Long id){
        Set<TriggerInformation> triggerInformations = taskSchedulerRepository.findById(id).get().getTriggerInformation();
        return triggerInformations;
    }




}

		
	
	
	 	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

