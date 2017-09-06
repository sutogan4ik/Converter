package ru.brucha.converter.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by prog on 05.09.2017.
 */
@Root
public class Valute implements Serializable{
    @Attribute
    String ID;
    @Element
    int NumCode;
    @Element
    String CharCode;
    @Element
    int Nominal;
    @Element
    String Name;
    @Element
    String Value;

    public String getID() {
        return ID;
    }

    public int getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public int getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNumCode(int numCode) {
        NumCode = numCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setValue(String value) {
        Value = value;
    }
}
