package com.Springboot.example.service;


import com.Springboot.example.model.*;

import com.Springboot.example.repository.Database2Repository;
import com.Springboot.example.repository.KpiRepository;
import com.Springboot.example.repository.Rsl_test_sysRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Database2ServiceImp implements Database2Service {
    @Value("${spring.datasource.url}")
    private String SPRING_URL;
    @Value("${spring.datasource.username}")
    private String SPRING_USERNAME;
    @Value("${spring.datasource.password}")
    private String SPRING_PASSWORD;
    @PersistenceContext
    public EntityManager em;
    @Autowired
    private Database2Repository Database2repository;

    @Override
    public void createOrUpdateDatabae2(Database2 Database2) {
        Database2repository.save(toEntitykpi(Database2));

    }

    private Database2 toEntitykpi(Database2 database2) {
        Database2 r = new Database2();
        r.setIdKpi(database2.getIdKpi());
        r.setDate(database2.getDate());
        r.setSysteme(database2.getSysteme());
        r.setVal_kpi(database2.getVal_kpi());
        r.setVal_dim(database2.getVal_dim());
        return r;
    }

    @Override
    public List<Database2> getAllkpi() {
        return Database2repository.findAll();
    }

    @Override
    public void deleteDatabase2(Integer id) {
        Database2repository.deleteById(id);

    }
//	@RequestMapping("deleteall")
//	public void deleteRsl_test_sysAll() {
//		Rsl_test_sysrepository.deleteAll();
//		return rslt;
//	}


    @SuppressWarnings("unchecked")
    @Override
    public List<Vue_Globale> getCompKpi() throws Exception {
        //Requete qui compare les kpi qui ont le meme dim et kpi_id avec leur seuil
        List<Vue_Globale> rslt = new ArrayList<Vue_Globale>();
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(SPRING_URL
                , SPRING_USERNAME, SPRING_PASSWORD);

        PreparedStatement ps = conn.prepareStatement("select k.name_kpi as name, count(distinct(a.val_kpi)) as val1,count(distinct(b.val_kpi)) as val2, sum(distinct(a.val_kpi)) as val3 ,sum(distinct(b.val_kpi)) as val4, a.idkpi as Code_requete ,a.date as Date \r\n" +
                "from database2 as a LEFT OUTER JOIN database2 as b ON  a.idkpi=b.idkpi INNER JOIN kpi as k on a.idkpi=k.id_kpi \r\n" +
                "and a.date=b.date \r\n" +
                "and a.system='Postgres' and b.system='SqlServer' \r\n" +
                "group by a.system, a.idkpi,a.date,k.seuil, k.name_kpi \r\n" +
                "having (sum(a.val_kpi) -sum(b.val_kpi)) <> k.seuil \r\n" +
                "order by a.date\r\n" +
                " ");
        PreparedStatement ps1 = conn.prepareStatement
                ("select  a.idkpi,a.date, count(distinct(a.val_kpi))as val5 \r\n" +
                        "from database2 a , database2 b ,kpi k where k.id_kpi=a.idkpi \r\n" +
                        "and a.idkpi=b.idkpi\r\n" +
                        "and a.system='Postgres' and b.system='SqlServer'\r\n" +
                        "and a.val_kpi = b.val_kpi and a.valeur_dim=b.valeur_dim \r\n" +
                        "and a.date=b.date\r\n" +
                        "group by a.date , a.idkpi,k.seuil\r\n" +
                        "\r\n " +
                        "Except \r\n" +
                        "select a.idkpi,a.date, count(distinct(a.val_kpi)),count(distinct(b.val_kpi)) \r\n" +
                        "from database2 a , database2 b ,kpi k where k.id_kpi=a.idkpi \r\n" +
                        "and a.idkpi=b.idkpi\r\n" +
                        "and a.system='Postgres' and b.system='SqlServer'\r\n" +
                        "and a.date=b.date\r\n" +
                        "group by a.date , a.idkpi,k.seuil\r\n" +
                        "order by idkpi");
        PreparedStatement ps2 = conn.prepareStatement
                ("select  a.idkpi,a.date, count(distinct(a.val_kpi))as val5 \r\n" +
                        "from database2 a , database2 b ,kpi k where k.id_kpi=a.idkpi \r\n" +
                        "and a.idkpi=b.idkpi\r\n" +
                        "and a.system='Postgres' and b.system='SqlServer'\r\n" +
                        "and a.val_kpi = b.val_kpi and a.valeur_dim=b.valeur_dim \r\n" +
                        "and a.date=b.date \r\n" +
                        "group by a.date , a.idkpi,k.seuil \r\n" +
                        "\r\n" +
                        "Except \r\n" +
                        "select a.idkpi,a.date, count(distinct(a.val_kpi)),count(distinct(b.val_kpi)) \r\n" +
                        "from database2 a , database2 b ,kpi k where k.id_kpi=a.idkpi \r\n" +
                        "and a.idkpi=b.idkpi\r\n" +
                        "and a.system='Postgres' and b.system='SqlServer'\r\n" +
                        "and a.date=b.date\r\n" +
                        "group by a.date , a.idkpi,k.seuil\r\n" +
                        "order by idkpi");

        ResultSet rs = ps.executeQuery();
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();

        while (rs.next() && rs1.next()) {
            Vue_Globale blog = new Vue_Globale();
            blog.setDate(rs.getString("Date"));
            blog.setCode_requete(rs.getLong("Code_requete"));
            blog.setNbreRecordOk(rs1.getInt("val5"));
            //      blog.setVal_kpi1( rs.getLong("val3"));
            //       blog.setVal_kpi2(rs.getLong("val4"));
            blog.setName_kpi(rs.getString("name"));
            blog.setLoad1(rs.getString("val1"));
            blog.setLoad2(rs.getString("val2"));

            blog.setGap((rs.getLong("val4") - rs.getLong("val3")));
            java.text.DecimalFormat df = new java.text.DecimalFormat("###");
            System.out.println();
            BigDecimal bigD = new BigDecimal((Math.abs(rs.getDouble("val4") - rs.getDouble("val3")) * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
            blog.setGAP_par_100(df.format(bigD));
            rslt.add(blog);
        }


        return rslt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Vue_Globale> getCompKpi2() throws Exception {
        //Requete qui compare les kpi qui ont le meme dim et kpi_id avec leur seuil
        List<Vue_Globale> rslt = new ArrayList<Vue_Globale>();
        Connection conn = null;
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(SPRING_URL
                , SPRING_USERNAME, SPRING_PASSWORD);

        PreparedStatement ps = conn.prepareStatement("select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as valeur1" +
                " , b.val_kpi as valeur2 , k.name_kpi as name from database2 a ,database2 b ,kpi k \r\n" +
                "where b.valeur_dim = a.valeur_dim \r\n" +
                "and b.idkpi = a.idkpi \r\n" +
                "and a.idkpi = k.id_kpi and abs(b.val_kpi - a.val_kpi) > k.seuil ");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Vue_Globale blog = new Vue_Globale();
            blog.setDate(rs.getString("Date"));
            blog.setCode_requete(rs.getLong("Code_requete"));

            //   blog.setVal_kpi1( rs.getLong("valeur1"));
            //    blog.setVal_kpi2(rs.getLong("valeur2"));
            blog.setName_kpi(rs.getString("name"));


            blog.setGap((rs.getLong("valeur1") - rs.getLong("valeur2")));
            java.text.DecimalFormat df = new java.text.DecimalFormat("###");
            System.out.println();
            BigDecimal bigD = new BigDecimal((Math.abs(rs.getDouble("valeur1") - rs.getDouble("valeur2")) * 100) / (Math.max(rs.getDouble("valeur1"), rs.getDouble("valeur2"))));
            blog.setGAP_par_100(df.format(bigD));
            rslt.add(blog);
        }


        return rslt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean VeriferExis(String val_dim, String date, String system) {

        List<Database2> k = new ArrayList<Database2>();
        k = em.createQuery("SELECT c FROM Rsl_test_sys c WHERE c.val_dim LIKE :valdim And c.date LIKE :date AND c.systeme LIKE :system")
                .setParameter("valdim", val_dim)
                .setParameter("date", date)
                .setParameter("system", system)
                .getResultList();
        if (k.size() >= 1) {
            System.out.println();
            return true;
        } else {
            System.out.println(k);
            return false;
        }
    }

    @Transactional
    @Override
    public void InsertOrUpdate(Database2 r) {
        em.createNativeQuery("INSERT into database2 \n" +
                        "(date,idkpi,system,valeur_dim,val_kpi,id_kpi)\n" +
                        "VALUES\n" +
                        "(?,?,?,?,?,?)\n")
                //"ON CONFLICT  (date,idkpi,system,valeur_dim)\n" +
                //"DO update set val_kpi=excluded.val_kpi")
                .setParameter(1, r.date)
                .setParameter(2, r.idKpi)
                .setParameter(3, r.systeme)
                .setParameter(4, r.val_dim)
                .setParameter(5, r.val_kpi)
                .setParameter(6, r.getKpi().id_kpi)
                .executeUpdate();
    }

    public void insertBatchData(List<Database2> records) {
        String insertQuery = "INSERT into database2 \n" +
                "(date,idkpi,system,valeur_dim,val_kpi,id_kpi)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?)\n";

        try (Connection connection = DriverManager.getConnection(SPRING_URL, SPRING_USERNAME, SPRING_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(insertQuery)) {

            for (Database2 r : records) {
                ps.setString(1, r.date);
                ps.setFloat(2, r.idKpi);
                ps.setString(3, r.systeme);
                ps.setString(4, r.val_dim);
                ps.setFloat(5, r.val_kpi);
                ps.setLong(6, r.getKpi().id_kpi);
                ps.addBatch();  // Add the prepared statement to the batch
            }

            ps.executeBatch();  // Execute the batch

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDatabase2All() {
        // TODO Auto-generated method stub

    }


}
