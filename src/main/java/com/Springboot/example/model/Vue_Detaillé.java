package com.Springboot.example.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vue_detaille")
public class Vue_Detaillé {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id ;
    public String date ;
    public long code_requete;

    public double val_kpi1;
    public  double val_kpi2;
    public String name_kpi;
    public String database1;
    public String database2;
    public double gap;
  
	public String GAP_par_100;
	public int NbreRecordOk ;
	public int NbreRecordNotOk;
	public String val_dim;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDatabase1() {
        return database1;
    }

    public void setDatabase1(String database1) {
        this.database1 = database1;
    }
    public String getDatabase2() {
        return database2;
    }

    public void setDatabase2(String database2) {
        this.database2 = database2;
    }

    public long getCode_requete() {
        return code_requete;
    }

    public void setCode_requete(long code_requete) {
        this.code_requete = code_requete;
    }



    public double getVal_kpi1() {
        return val_kpi1;
    }

    public void setVal_kpi1(double val_kpi1) {
        this.val_kpi1 = val_kpi1;
    }

    public double getVal_kpi2() {
        return val_kpi2;
    }

    public void setVal_kpi2(double val_kpi2) {
        this.val_kpi2 = val_kpi2;
    }

    public String getName_kpi() {
        return name_kpi;
    }

    public void setName_kpi(String name_kpi) {
        this.name_kpi = name_kpi;
    }

    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }

	

    public void setVal_dim(String val_dim) {
		this.val_dim = val_dim; 
		
	}
	public String getVal_dim() {
		return  val_dim; 
		
	}

	
	public int getNbreRecordOk() {
		return NbreRecordOk;
	}
	public void setNbreRecordOk(int nbreRecordOk) {
		NbreRecordOk = nbreRecordOk;
	}
	public int getNbreRecordNotOk() {
		return NbreRecordNotOk;
	}
	public void setNbreRecordNotOk(int nbreRecordNotOk) {
		NbreRecordNotOk = nbreRecordNotOk;
	}

	public String getGAP_par_100() {
		return GAP_par_100;
	}
	public void setGAP_par_100(String gAP_par_100) {
		GAP_par_100 = gAP_par_100;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vue_Detaillé that = (Vue_Detaillé) o;
        return Objects.equals(code_requete, that.code_requete) && Objects.equals(date, that.date) && Objects.equals(database1, that.database1)
                && Objects.equals(database2, that.database2) && Objects.equals(val_dim, that.val_dim) && Objects.equals(name_kpi, that.name_kpi)
                && Objects.equals(val_kpi1, that.val_kpi1) && Objects.equals(val_kpi2, that.val_kpi2) && Objects.equals(gap, that.gap)
                && Objects.equals(GAP_par_100, that.GAP_par_100) && Objects.equals(NbreRecordOk, that.NbreRecordOk)
                && Objects.equals(NbreRecordNotOk, that.NbreRecordNotOk);

    }

    @Override
    public int hashCode() {
        return Objects.hash(date, code_requete, database1, database2, val_dim, name_kpi, val_kpi1, val_kpi2, gap, GAP_par_100, NbreRecordOk, NbreRecordNotOk);
    }
}
