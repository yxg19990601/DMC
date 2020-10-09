package com.ems.szy.wegw.entity;

import com.ems.szy.wegw.entity.adapter.DateXmlAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "data")
public class Data {


    private String operation;

    private String sequence; //�ɼ������������ķ������ݵ����


    /**
     *  yes: ���������ķ��͵����ݾ����ɼ�������;
     *    no:  ���������ķ��͵�����δ�����ɼ�������;
     */
    private String parse;

    private Date time; // <!-- ���ݲɼ�ʱ�� -->

    private Integer total; // ʣ����������

    private Integer current; //��ǰ��

    // ���
    private List<Meter> meters;


    public String getOperation() {
        return operation;
    }

    @XmlAttribute
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSequence() {
        return sequence;
    }
    @XmlElement
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getParse() {
        return parse;
    }
    @XmlElement
    public void setParse(String parse) {
        this.parse = parse;
    }

    @XmlJavaTypeAdapter(DateXmlAdapter.class)
    public Date getTime() {
        return time;
    }

    @XmlElement
    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getTotal() {
        return total;
    }

    @XmlElement
    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    @XmlElement
    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    @XmlElement(name = "meter")
    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }


    @Override
    public String toString() {
        return "Data{" +
                "operation='" + operation + '\'' +
                ", sequence='" + sequence + '\'' +
                ", parse='" + parse + '\'' +
                ", time=" + time +
                ", total=" + total +
                ", current=" + current +
                ", meters=" + meters +
                '}';
    }
}
