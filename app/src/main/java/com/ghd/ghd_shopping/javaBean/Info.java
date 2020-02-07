package com.ghd.ghd_shopping.javaBean;


import java.util.List;

public class Info {
    private String label;
    private List<String> name;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Info{" +
                "label='" + label + '\'' +
                ", name=" + name +
                '}';
    }
}
