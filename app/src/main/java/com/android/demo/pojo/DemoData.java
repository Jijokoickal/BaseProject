package com.android.demo.pojo;

/**
 * This is the sample data class created for reference.
 */
public class DemoData {
    private int id;
    private String name;

    public DemoData() {

    }

    public DemoData(final String name) {
        this.name = name;
    }

    public DemoData(final int id, final String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
