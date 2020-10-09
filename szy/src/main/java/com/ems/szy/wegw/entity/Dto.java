package com.ems.szy.wegw.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class Dto {


    private Common common;
    private IdValidate idValidate;
    private Supcon supcon;
    private Data data;
    private  HeatBeat heatBeat ;



    public Common getCommon() {
        return common;
    }

    @XmlElement(name = "common")
    public void setCommon(Common common) {
        this.common = common;
    }







    public IdValidate getIdValidate() {
        return idValidate;
    }

    @XmlElement(name = "id_validate")
    public void setIdValidate(IdValidate idValidate) {
        this.idValidate = idValidate;
    }




    public Supcon getSupcon() {
        return supcon;
    }

    @XmlElement(name = "supcon")
    public void setSupcon(Supcon supcon) {
        this.supcon = supcon;
    }



    public Data getData() {
        return data;
    }

    @XmlElement
    public void setData(Data data) {
        this.data = data;
    }



    public HeatBeat getHeatBeat() {
        return heatBeat;
    }

    @XmlElement(name = "heat_beat")
    public void setHeatBeat(HeatBeat heatBeat) {
        this.heatBeat = heatBeat;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "common=" + common +
                ", idValidate=" + idValidate +
                ", supcon=" + supcon +
                ", data=" + data +
                ", heatBeat=" + heatBeat +
                '}';
    }
}
