package com.Springboot.example.helper;

import com.Springboot.example.model.*;
import com.Springboot.example.repository.*;
import com.Springboot.example.service.Database1Service;
import com.Springboot.example.service.Database2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class LoadDataHelper {

    @Autowired
    private DbRepository dbrepository;
    @Autowired
    private KpiRepository kpirepository;
    @Autowired
    private RslRepository rslRepository;
    @Autowired
    private Vue_DetailsRepository vue_detailsRepository;

    @Autowired
    Database1Service database1Service;
    @Autowired
    Database2Service database2Service;

    public Map<String,List<?>> loadAllData(String[] kpis, String dateDeb,
                                         String dateFin
            , long db1, Long db2) throws Exception {
        Map<String,List<?>> listMap = new HashMap<>();
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);
        List<Database1> rslt = new ArrayList<>();
        List<Database2> rslt1 = new ArrayList<>();
        Map<Requete, Connection> connectionMap = new ConcurrentHashMap<>();
        List<Vue_Globale> rslt211 = new ArrayList<>();
        List<Vue_Detaillé> rslt202 = new ArrayList<>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root")) {
                // Delete records from 'database1'
                deleteRecords(conn, "database1", k.getId_kpi(), dateDeb, dateFin);

                // Delete records from 'database2'
                deleteRecords(conn, "database2", k.getId_kpi(), dateDeb, dateFin);

            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        Arrays.asList(kpis).stream().forEach(s -> {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            k.getRequetess().parallelStream().forEach(r ->
            {
                if ("postgresql".equals(r.getId_databasee().getSystem())) {
                    String dimension = " " + r.getDims().stream()
                            .map(Dimension::getVal_dim)
                            .collect(Collectors.joining(" ||'|'|| "));
                    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
                                    + r.getId_databasee().getName(),
                            r.getId_databasee().getUsername(), r.getId_databasee().getPassword())) {
                        JdbcTemplate db = new JdbcTemplate(new SingleConnectionDataSource(conn, true));
                        String query = "SELECT " + r.getDate() + " AS date, " +
                                k.getId_kpi() + " AS Code_requete ," +
                                dimension +
                                " AS val_dim, " + r.getVal_kpi() + " AS " + r.getVal_kpi_alias() +
                                " " + r.getCopr() + " AND " + r.getDate() +
                                " BETWEEN '" + dateDeb + "' AND '" + dateFin + "' GROUP BY " + r.getDate() + " ," +
                                dimension;


                        db.query(query, rs -> {
                            if (d1.equals(r.getId_databasee())) {
                                Database1 blog = new Database1();
                                blog.setDate(rs.getString("date"));
                                blog.setIdKpi(rs.getFloat("Code_requete"));
                                blog.setVal_dim(rs.getString("val_dim"));
                                blog.setVal_kpi(rs.getFloat(r.getVal_kpi_alias()));
                                blog.setDatabase_name(r.getId_databasee().getName());
                                blog.setSysteme(r.getId_databasee().getName());
                                blog.setDbname(r.getId_databasee().getName());
                                blog.setKpi(kpirepository.findById((long) blog.getIdKpi()).get());
                                rslt.add(blog);
                            } else {
                                Database2 blog = new Database2();
                                blog.setDate(rs.getString("date"));
                                blog.setIdKpi(rs.getFloat("Code_requete"));
                                blog.setVal_dim(rs.getString("val_dim"));
                                blog.setVal_kpi(rs.getFloat(r.getVal_kpi_alias()));
                                blog.setDatabase_name(r.getId_databasee().getName());
                                blog.setSysteme(r.getId_databasee().getName());
                                blog.setDbname(r.getId_databasee().getName());
                                blog.setKpi(kpirepository.findById((long) blog.getIdKpi()).get());
                                rslt1.add(blog);
                            }
                        });


                    } catch (SQLException e) {
                        System.out.println("SQL Exception: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("no connections");
                }

            });


        });
        database1Service.insertBatchData(rslt);
        database2Service.insertBatchData(rslt1);
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root")) {
            Arrays.asList(kpis).parallelStream().forEach(s -> {
                long kpi = Long.parseLong(s);
                Kpi k = kpirepository.findById(kpi).get();


                try (PreparedStatement ps0 = conn.prepareStatement("delete from vue_detaille where code_requete= " + k.getId_kpi() + " and date between'" + dateDeb + "' and '" + dateFin + "'");) {
                    int rs0 = ps0.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }

                try (PreparedStatement ps = conn
                        .prepareStatement(
                                "        select coalesce (a.id_kpi,b.id_kpi ) as Code_requete\r\n"
                                        + "        , coalesce (a.date, b.date) as Date\r\n"
                                        + "        , coalesce (a.valeur_dim,b.valeur_dim) as valeur_dim\r\n"
                                        + "        , sum ( coalesce (a.val_kpi,0))  as val_kpi_a\r\n"
                                        + "        , sum ( coalesce (b.val_kpi,0)) as val_kpi_b\r\n"
                                        + "        , sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)) as gap\r\n"
                                        + "        , 100*abs (sum (coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) / GREATEST  (abs(sum ( coalesce (a.val_kpi,0))), abs (sum ( coalesce (b.val_kpi,0))) ) as gap_par_100\r\n"
                                        + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0))) >  K.seuil) then 1 else 0 end as nbre_record_not_ok\r\n"
                                        + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)))<=  K.seuil) then 1 else 0 end as nbre_record_ok\r\n"
                                        + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                                        + "        from database1 a\r\n"
                                        + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                                        + "        and a.valeur_dim = b.valeur_dim\r\n"
                                        + "        and a.date = b.date\r\n"
                                        + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                                        + "        WHERE 1=1 \r\n"
                                        + "        AND coalesce(a.id_kpi,b.id_kpi) = " + k.id_kpi + " \r\n"
                                        + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                                        + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                                        + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n")) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Vue_Detaillé blog = new Vue_Detaillé();
                        blog.setDate(rs.getString("Date"));
                        blog.setCode_requete(rs.getLong("Code_requete"));
                        blog.setVal_kpi1(rs.getFloat("val_kpi_a"));
                        blog.setVal_kpi2(rs.getFloat("val_kpi_b"));
                        blog.setName_kpi(rs.getString("name"));
                        blog.setVal_dim(rs.getString("valeur_dim"));
                        blog.setGap(rs.getFloat("gap"));
                        blog.setGAP_par_100(rs.getString("gap_par_100"));
                        blog.setNbreRecordNotOk(rs.getInt("nbre_record_not_ok"));
                        blog.setNbreRecordOk(rs.getInt("nbre_record_ok"));
                        blog.setDatabase1(rs.getString("Database1"));
                        blog.setDatabase2(rs.getString("Database2"));

                        rslt202.add(blog);


                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        vue_detailsRepository.saveAll(rslt202);
        List<Vue_Globale> rslt00 = new ArrayList<Vue_Globale>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                    , "postgres", "root");

            long startTime = System.currentTimeMillis();


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
                            + "WHERE code_requete = " + k.id_kpi + "   AND DATE BETWEEN '" + dateDeb + "' AND '" + dateFin + "'\r\n"
                            + "GROUP BY code_requete, K.name_kpi, K.seuil , K.seuil_data_quality, K.seuil_gap \r\n");


            ResultSet rs007 = ps007.executeQuery();
            while (rs007.next()) {
//	                System.out.println("pap");

//	                System.out.println(rs007.getString("acceptation"));

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
//	                blog.setLoad1(rs.getString("val1"));
//	                blog.setLoad2(rs.getString("val2"));
                //probleme au niveau de nbreRecordOk si on a un seul enregistrement
//	                blog.setNbreRecordOk(rs1.getInt("nbreRecordOK"));
                DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateobj = new Date();
                blog.dateExec = (dfff.format(dateobj));
                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
                blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime));
                rslt00.add(blog);
            }

        }
        for (Vue_Globale r : rslt00) {
            rslRepository.save(r);
        }
        listMap.put("vueGlobale", Collections.singletonList(rslt00));
        listMap.put("vueDetaille", Collections.singletonList(rslt202));
        return listMap;

    }
    public List<Vue_Globale> loadData(String[] kpis, String dateDeb,
                                      String dateFin
            , long db1, Long db2){
        Database d1 = dbrepository.findDatabaseById(db1);
        Database d2 = dbrepository.findDatabaseById(db2);
        List<Database1> rslt = new CopyOnWriteArrayList<>();
        List<Database2> rslt1 = new CopyOnWriteArrayList<>();
        List<Vue_Detaillé> rslt202 = new ArrayList<>();
        List<Vue_Globale> rslt00 = new ArrayList<>();
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root")) {
                // Delete records from 'database1'
                deleteRecords(conn, "database1", k.getId_kpi(), dateDeb, dateFin);

                // Delete records from 'database2'
                deleteRecords(conn, "database2", k.getId_kpi(), dateDeb, dateFin);

            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        for (String s : kpis) {


            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            Requete r1 = new Requete();
            for (Requete r : k.getRequetess()) {
                if (r1 != r) {
                    r1 = r;


                    if ("postgresql".equals(r.getId_databasee().getSystem())) {
                        String dimension = " " + r.getDims().stream()
                                .map(Dimension::getVal_dim)
                                .collect(Collectors.joining(" ||'|'|| "));
                        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
                                        + r.getId_databasee().getName(),
                                r.getId_databasee().getUsername(), r.getId_databasee().getPassword())) {
                            if (d1.equals(r.getId_databasee())) {

                                try (PreparedStatement ps = conn.prepareStatement(
                                        "SELECT " + r.getDate() + " AS date, " +
                                                k.getId_kpi() + " AS Code_requete ," +
                                                dimension +
                                                " AS val_dim, " + r.getVal_kpi() + " AS " + r.getVal_kpi_alias() +
                                                " " + r.getCopr() + " AND " + r.getDate() +
                                                " BETWEEN '" + dateDeb + "' AND '" + dateFin + "' GROUP BY " + r.getDate() + " ," +
                                                dimension)) {

                                    ResultSet rs = ps.executeQuery();
                                    while (rs.next()) {
                                        Database1 blog = new Database1();
                                        blog.setDate(rs.getString("date"));
                                        blog.setIdKpi(rs.getFloat("Code_requete"));
                                        blog.setVal_dim(rs.getString("val_dim"));
                                        blog.setVal_kpi(rs.getFloat(r.getVal_kpi_alias()));
                                        blog.setDatabase_name(r.getId_databasee().getName());
                                        blog.setSysteme(r.getId_databasee().getName());
                                        blog.setDbname(r.getId_databasee().getName());
                                        rslt.add(blog);
                                    }
                                    for (Database1 database1 : rslt) {

                                        Database1 data1 = new Database1();

                                        data1.setDate(database1.getDate());
                                        data1.setIdKpi(database1.getIdKpi());
                                        data1.setVal_dim(database1.getVal_dim());
                                        data1.setVal_kpi(database1.getVal_kpi());
                                        data1.setSysteme(database1.getSysteme());
                                        data1.setKpi(kpirepository.findById((long) database1.getIdKpi()).get());
                                        data1.setDatabase_name(database1.getDatabase_name());


                                        database1Service.InsertOrUpdate(data1);

                                    }
                                } catch (Exception e) {
                                    System.out.println("Failed to execute query: " + e.toString() + e.getMessage());
                                }
                            }
                            if (d2.equals(r.getId_databasee())) {
                                try (PreparedStatement ps = conn.prepareStatement(
                                        "SELECT " + r.getDate() + " AS date, " +
                                                k.getId_kpi() + " AS Code_requete ," +
                                                dimension +
                                                " AS val_dim, " + r.getVal_kpi() + " AS " + r.getVal_kpi_alias() +
                                                " " + r.getCopr() + " AND " + r.getDate() +
                                                " BETWEEN '" + dateDeb + "' AND '" + dateFin + "' GROUP BY " + r.getDate() + " ," +
                                                dimension)) {

                                    ResultSet rs = ps.executeQuery();
                                    while (rs.next()) {
                                        Database2 blog = new Database2();
                                        blog.setDate(rs.getString("date"));
                                        blog.setIdKpi(rs.getFloat("Code_requete"));
                                        blog.setVal_dim(rs.getString("val_dim"));
                                        blog.setVal_kpi(rs.getFloat(r.getVal_kpi_alias()));
                                        blog.setDatabase_name(r.getId_databasee().getName());
                                        blog.setSysteme(r.getId_databasee().getName());
                                        blog.setDbname(r.getId_databasee().getName());
                                        rslt1.add(blog);
                                    }
                                    for (Database2 database2 : rslt1) {

                                        Database2 data2 = new Database2();

                                        data2.setDate(database2.getDate());
                                        data2.setIdKpi(database2.getIdKpi());
                                        data2.setVal_dim(database2.getVal_dim());
                                        data2.setVal_kpi(database2.getVal_kpi());
                                        data2.setSysteme(database2.getSysteme());
                                        data2.setKpi(kpirepository.findById((long) database2.getIdKpi()).get());
                                        data2.setDatabase_name(database2.getDatabase_name());


                                        database2Service.InsertOrUpdate(data2);

                                    }
                                } catch (Exception e) {
                                    System.out.println("Failed to execute query: " + e.toString() + e.getMessage());
                                }
                            }

                        } catch (SQLException e) {
                            System.out.println("SQL Exception: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("no connections");
                    }
                }
            }


        }
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root")) {
                String deleteFromVueDetaille = "delete from vue_detaille where code_requete = ? and date between ? and ?";
                try (PreparedStatement ps = conn.prepareStatement(deleteFromVueDetaille)) {
                    ps.setLong(1, k.getId_kpi());
                    ps.setString(2, dateDeb);
                    ps.setString(3, dateFin);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        for (String s : kpis) {
            long kpi = Long.parseLong(s);
            Kpi k = kpirepository.findById(kpi).get();
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root")) {


                try (
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
                                                + "        , case when (abs (sum ( coalesce (a.val_kpi,0) - coalesce (b.val_kpi,0)))<=  K.seuil) then 1 else 0 end as nbre_record_ok\r\n"
                                                + "        ,  K.name_kpi as name, a.system as Database1 , b.system as Database2\r\n"
                                                + "        from database1 a\r\n"
                                                + "        full outer join database2 b on a.id_kpi = b.id_kpi\r\n"
                                                + "        and a.valeur_dim = b.valeur_dim\r\n"
                                                + "        and a.date = b.date\r\n"
                                                + "		   full outer join kpi K on K.id_kpi = coalesce (a.id_kpi,b.id_kpi )\r\n"
                                                + "        WHERE 1=1 \r\n"
                                                + "        AND coalesce(a.id_kpi,b.id_kpi) = " + k.id_kpi + " \r\n"
                                                + "        and abs (coalesce (a.val_kpi,0) + coalesce (b.val_kpi,0))>0        \r\n"
                                                + "        and coalesce (a.date, b.date) between '" + dateDeb + "' and '" + dateFin + "'  \r\n"
                                                + "        GROUP BY  coalesce ( a.id_kpi, b.id_kpi ),  K.name_kpi ,a.system,b.system,coalesce (a.date, b.date),coalesce (a.valeur_dim,b.valeur_dim) ,  K.seuil\r\n")) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Vue_Detaillé blog = new Vue_Detaillé();
                        blog.setDate(rs.getString("Date"));
                        blog.setCode_requete(rs.getLong("Code_requete"));
                        blog.setVal_kpi1(rs.getFloat("val_kpi_a"));
                        blog.setVal_kpi2(rs.getFloat("val_kpi_b"));
                        blog.setName_kpi(rs.getString("name"));
                        blog.setVal_dim(rs.getString("valeur_dim"));
                        blog.setGap(rs.getFloat("gap"));
                        blog.setGAP_par_100(rs.getString("gap_par_100"));
                        blog.setNbreRecordNotOk(rs.getInt("nbre_record_not_ok"));
                        blog.setNbreRecordOk(rs.getInt("nbre_record_ok"));
                        blog.setDatabase1(rs.getString("Database1"));
                        blog.setDatabase2(rs.getString("Database2"));

                        rslt202.add(blog);


                    }
                    for (Vue_Detaillé r : rslt202) {
                        vue_detailsRepository.save(r);
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }
                try (PreparedStatement ps007 = conn.prepareStatement(
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
                                + "WHERE code_requete = " + k.id_kpi + "   AND DATE BETWEEN '" + dateDeb + "' AND '" + dateFin + "'\r\n"
                                + "GROUP BY code_requete, K.name_kpi, K.seuil , K.seuil_data_quality, K.seuil_gap \r\n")) {
                    ResultSet rs007 = ps007.executeQuery();
                    long startTime = System.currentTimeMillis();
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
//	                blog.setLoad1(rs.getString("val1"));
//	                blog.setLoad2(rs.getString("val2"));
                        //probleme au niveau de nbreRecordOk si on a un seul enregistrement
//	                blog.setNbreRecordOk(rs1.getInt("nbreRecordOK"));
                        DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        Date dateobj = new Date();
                        blog.dateExec = (dfff.format(dateobj));
                        java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
                        blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime));
                        rslt00.add(blog);
                    }
                    for (Vue_Globale r : rslt00) {
                        rslRepository.save(r);
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return rslt00;
    }
    private void deleteRecords(Connection conn, String tableName, long idKpi, String dateDeb, String dateFin) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE id_kpi = ? AND date BETWEEN ? AND ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
            ps.setLong(1, idKpi);
            ps.setString(2, dateDeb);
            ps.setString(3, dateFin);
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
