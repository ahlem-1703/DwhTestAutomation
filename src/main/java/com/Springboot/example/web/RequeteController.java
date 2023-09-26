package com.Springboot.example.web;

import com.Springboot.example.model.*;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.repository.DimensionRepositroy;
import com.Springboot.example.repository.KpiRepository;
import com.Springboot.example.repository.RequeteRepository;
import com.Springboot.example.service.DataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Controller
public class RequeteController {
    @Autowired
    private RequeteRepository requeteRepository;
    @Autowired
    private DbRepository dbRepository;
    @Autowired
    private KpiRepository kpiRepository;
    @Autowired
    DataSourceProvider provider;
    @Autowired
    private DimensionRepositroy dimensionRepositroy;

    @GetMapping("/tester_requete")
    public String Tester_Requete(Model model, @RequestParam("ajoute") String[] countries) {


        return "/Requete/tester_requete";
    }

    @GetMapping("listRequetes")
    public String GetReq(Model model) {
        model.addAttribute("ReqList", requeteRepository.findAllByOrderByKpiAsc());
        return "/Requete/listRequetes";

    }

    @GetMapping("/editrequete/{id}")
    public String editRequete(@PathVariable("id") Long idrequete, Model model) {
        Requete requete = requeteRepository.findById(idrequete).get();
        model.addAttribute("requete", requete);
        return "/Requete/editrequete";

    }

    @PostMapping("LaunchScriptTestResultStep2")
    @ResponseBody
    public Map<String, Object> LaunchScriptTestResultStep2(Model model,
                                         @ModelAttribute("requete") Requete requete,
                                         @ModelAttribute("kpi") Kpi kpi,
                                         @RequestParam("ajoutStep2[]") String[] valdim,
                                         @RequestParam("ajouttStep2") String valdimalias,
                                         @RequestParam("dateDebStep2") String dateDeb,
                                         @RequestParam("dateFinStep2") String dateFin,
                                         @RequestParam("db[]") Long[] id) throws SQLException {
        Database db = dbRepository.findById(id[0]).orElse(null);
        DatabaseInfo databaseInfo = new DatabaseInfo();
        String separator = " ||' | '|| ";
        StringBuilder resultDimStep2Builder = new StringBuilder();
        for (int i = 0; i < valdim.length; i++) {
            resultDimStep2Builder.append(valdim[i]);
            if (i < valdim.length - 1) {
                resultDimStep2Builder.append(separator);
            }
        }
        String query = "select " + requete.date + " as Date ,"
                + kpi.id_kpi + " as code_requete," + resultDimStep2Builder + " as " + valdimalias
                + ", " + requete.val_kpi + " as " + requete.val_kpi_alias
                + " " + requete.copr + " and " + requete.date
                + " between CAST('" + dateDeb + "' as date) and CAST('" + dateFin + "' as date) "
                + "group by " + requete.date + ", " + resultDimStep2Builder + " limit 10";
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        List<String> columnLabels = new ArrayList<>();
        if (db != null) {
            databaseInfo.setUrl(db.url);
            databaseInfo.setSystem(db.system);
            databaseInfo.setPort(db.port);
            databaseInfo.setName(db.name);
            databaseInfo.setUsername(db.username);
            databaseInfo.setPassword(db.password);
            DataSource dataSource = provider.getDataSource(databaseInfo);
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (resultSet.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnLabel(i);
                                Object value = resultSet.getObject(i);
                                row.put(columnName, value);
                            }
                            rows.add(row);
                        }
                        if (!rows.isEmpty()) {
                            Map<String, Object> firstRow = rows.get(0);
                            Set<String> columnNames = firstRow.keySet();
                            columnLabels = new ArrayList<>(columnNames);
                            model.addAttribute("columns", columnLabels);
                        }
                        model.addAttribute("rows", rows);
                    }
                }
            }

        }
        result.put("rows",rows);
        result.put("columns",columnLabels);
        return result;
    }
    @PostMapping("LaunchScriptEditRequeteTestResult")
    @ResponseBody
    public Map<String, Object> LaunchScriptEditRequeteTestResult(Model model,
                                         @ModelAttribute("requete") Requete requete,
                                         @ModelAttribute("kpi") Kpi kpi,
                                         @RequestParam("ajout") String[] valdim1,
                                         @RequestParam(value = "ajout[]", required = false) String[] valdim2,
                                         @RequestParam("ajoutt") String valdimalias,
                                         @RequestParam(value = "id_databasee", defaultValue = "") String id,
                                         @RequestParam("dateDeb") String dateDeb,
                                         @RequestParam("dateFin") String dateFin) {
        Map<String, Object> result = new HashMap<>();
        try {
            Database db = dbRepository.findById(Long.parseLong(id)).orElse(null);
            DatabaseInfo databaseInfo = new DatabaseInfo();
            // check if valdim2 is null and set its length to 0 if it is
            int valdim2Length = valdim2 != null ? valdim2.length : 0;

            // create a new combinedNotes array with the correct length
            String[] combinedNotes = new String[valdim1.length + valdim2Length];

            // copy the valdim1 and valdim2 arrays into the combinedNotes array
            System.arraycopy(valdim1, 0, combinedNotes, 0, valdim1.length);
            if (valdim2 != null) {
                System.arraycopy(valdim2, 0, combinedNotes, valdim1.length, valdim2.length);
            }
            String separator = " ||' | '|| ";
            StringBuilder resultBuilder = new StringBuilder();

            for (int i = 0; i < combinedNotes.length; i++) {
                resultBuilder.append(combinedNotes[i]);
                if (i < combinedNotes.length - 1) {
                    resultBuilder.append(separator);
                }
            }

            String resultToString = resultBuilder.toString();
            String query = "select " + requete.date + " as Date ,"
                    + kpi.id_kpi + " as code_requete," + resultToString + " as " + valdimalias
                    + ", " + requete.val_kpi + " as " + requete.val_kpi_alias
                    + " " + requete.copr + " and " + requete.date
                    + " between CAST('" + dateDeb + "' as date) and CAST('" + dateFin + "' as date) "
                    + "group by " + requete.date + ", " + resultToString + " limit 10";
            List<Map<String, Object>> rows = new ArrayList<>();

            List<String> columnLabels = new ArrayList<>();
            if (db != null) {
                databaseInfo.setUrl(db.url);
                databaseInfo.setSystem(db.system);
                databaseInfo.setPort(db.port);
                databaseInfo.setName(db.name);
                databaseInfo.setUsername(db.username);
                databaseInfo.setPassword(db.password);
                DataSource dataSource = provider.getDataSource(databaseInfo);
                try (Connection connection = dataSource.getConnection()) {
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        try (ResultSet resultSet = statement.executeQuery()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            int columnCount = metaData.getColumnCount();

                            while (resultSet.next()) {
                                Map<String, Object> row = new HashMap<>();
                                for (int i = 1; i <= columnCount; i++) {
                                    String columnName = metaData.getColumnLabel(i);
                                    Object value = resultSet.getObject(i);
                                    row.put(columnName, value);
                                }
                                rows.add(row);
                            }
                            if (!rows.isEmpty()) {
                                Map<String, Object> firstRow = rows.get(0);
                                Set<String> columnNames = firstRow.keySet();
                                columnLabels = new ArrayList<>(columnNames);
                            }
                        }
                    }
                }

            }
            result.put("rows",rows);
            result.put("columns",columnLabels);
        }
        catch (SQLException e){
            Map<String,Object> errorMessage = new HashMap<>();
            errorMessage.put("error","Something went wrong : " + e.getMessage() );
            return errorMessage;
        }
        return result;
    }

    @PostMapping("LaunchScriptTestResultStep3")
    @ResponseBody
    public Map<String, Object> LaunchScriptTestResultStep3(Model model,
                                         @ModelAttribute("date2") String date,
                                         @ModelAttribute("valKPI2") String val_kpi,
                                         @ModelAttribute("valKPIAs2") String val_kpi_alias,
                                         @ModelAttribute("copr2") String copr,
                                         @ModelAttribute("kpi") Kpi kpi,
                                         @RequestParam("ajoutStep3[]") String[] valdim,
                                         @RequestParam("ajouttStep3") String valdimalias,
                                         @RequestParam("dateDebStep3") String dateDeb,
                                         @RequestParam("dateFinStep3") String dateFin,
                                         @RequestParam("db[]") Long[] id) throws SQLException {
        Database db = dbRepository.findById(id[1]).orElse(null);
        DatabaseInfo databaseInfo = new DatabaseInfo();
        String separator = " ||' | '|| ";
        StringBuilder resultDimStep2Builder = new StringBuilder();
        for (int i = 0; i < valdim.length; i++) {
            resultDimStep2Builder.append(valdim[i]);
            if (i < valdim.length - 1) {
                resultDimStep2Builder.append(separator);
            }
        }
        String query = "select " + date + " as Date ,"
                + kpi.id_kpi + " as code_requete," + resultDimStep2Builder + " as " + valdimalias
                + ", " + val_kpi + " as " + val_kpi_alias
                + " " + copr + " and " + date
                + " between CAST('" + dateDeb + "' as date) and CAST('" + dateFin + "' as date) "
                + "group by " + date + ", " + resultDimStep2Builder + " limit 10";
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        List<String> columnLabels = new ArrayList<>();
        if (db != null) {
            databaseInfo.setUrl(db.url);
            databaseInfo.setSystem(db.system);
            databaseInfo.setPort(db.port);
            databaseInfo.setName(db.name);
            databaseInfo.setUsername(db.username);
            databaseInfo.setPassword(db.password);
            DataSource dataSource = provider.getDataSource(databaseInfo);
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        while (resultSet.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnLabel(i);
                                Object value = resultSet.getObject(i);
                                row.put(columnName, value);
                            }
                            rows.add(row);
                        }
                        if (!rows.isEmpty()) {
                            Map<String, Object> firstRow = rows.get(0);
                            Set<String> columnNames = firstRow.keySet();
                            columnLabels = new ArrayList<>(columnNames);
                            model.addAttribute("columns", columnLabels);
                        }
                        model.addAttribute("rows", rows);
                    }
                }
            }

        }
        result.put("rows",rows);
        result.put("columns",columnLabels);
        return result;
    }

    @RequestMapping(value = "/updatee", method = RequestMethod.POST)
    public String updateRequete(Requete requete, Model model,
                                @RequestParam("ajout") String[] valdim1,
                                @RequestParam(value = "ajout[]", required = false) String[] valdim2,
                                @RequestParam("ajoutt") String valdimalias,
                                @RequestParam(value = "ids", defaultValue = "") long ids) {
        // check if valdim2 is null and set its length to 0 if it is
        int valdim2Length = valdim2 != null ? valdim2.length : 0;

        // create a new combinedNotes array with the correct length
        String[] combinedNotes = new String[valdim1.length + valdim2Length];

        // copy the valdim1 and valdim2 arrays into the combinedNotes array
        System.arraycopy(valdim1, 0, combinedNotes, 0, valdim1.length);
        if (valdim2 != null) {
            System.arraycopy(valdim2, 0, combinedNotes, valdim1.length, valdim2.length);
        }
        String separator = " ||' | '|| ";
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < combinedNotes.length; i++) {
            resultBuilder.append(combinedNotes[i]);
            if (i < combinedNotes.length - 1) {
                resultBuilder.append(separator);
            }
        }

        String result = resultBuilder.toString();
        Requete r = requeteRepository.findById(requete.getId()).orElse(null);
        r.setCopr(requete.getCopr());
        r.setKpi(requete.getKpi());
        r.setDate(requete.getDate());
        r.setVal_kpi(requete.getVal_kpi());
        r.setVal_kpi_alias(requete.getVal_kpi_alias());
        r.setId_databasee(requete.getId_databasee());
        if (r == null) {
            // handle error
        }

        List<Dimension> dimensions = r.getDims();
        Dimension d = dimensions.get(0);
        d.setVal_dim(result);
        d.setVal_dim_alias(valdimalias);
        /*for (int i = 0; i < combinedNotes.length; i++) {
            if (i < dimensions.size()) {
                // Update existing dimension
                Dimension d = dimensions.get(i);
                d.setVal_dim(combinedNotes[i]);
                d.setVal_dim_alias(valdimalias);
            } else {
                // Create new dimension
                Dimension d = new Dimension();
                d.setVal_dim(combinedNotes[i]);
                d.setVal_dim_alias(valdimalias);
                dimensions.add(d);
            }
        }*/

        dimensionRepositroy.save(d);
        requeteRepository.save(r);

        return "redirect:/listRequetes";
    }

}
