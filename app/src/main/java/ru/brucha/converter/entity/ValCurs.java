package ru.brucha.converter.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by prog on 05.09.2017.
 */
@Root
public class ValCurs implements Serializable{
    @Attribute
    private String name;
    @Attribute
    private String Date;

    @ElementList(inline=true, entry = "Valute")
    List<Valute> list;

    public String getName() {
        return name;
    }

    public String getDate() {
        return Date;
    }

    public List<Valute> getList() {
        return list;
    }
}
