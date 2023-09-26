package com.Springboot.example.service;

import com.Springboot.example.model.Rsl_test_sys;
import com.Springboot.example.model.Vue_Detaillé;
import com.Springboot.example.model.Vue_Globale;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface VueDetailSevice {
    public void writeObjectsToCsv(List<Vue_Detaillé> objects, String[] ColumnNames, List<Vue_Globale> vueGlobaleList,String[] emails) throws IOException;
    public void InsertOrUpdate(Vue_Detaillé r);
}
