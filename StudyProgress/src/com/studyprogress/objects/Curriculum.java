package com.studyprogress.objects;

public class Curriculum {

    private String name;
    private int curriculumId;
    private String universityName;


    private int mode;

    public Curriculum() {

        mode = 0;

    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setCurriculumId(int curriculumId) {

        this.curriculumId = curriculumId;
    }

    public int getCurriculumId() {

        return curriculumId;
    }

    public void setMode(int mode) {

        this.mode = mode;
    }

    public int getMode() {

        return mode;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
