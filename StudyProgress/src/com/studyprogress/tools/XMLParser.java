package com.studyprogress.tools;

import android.util.Log;

import com.studyprogress.objects.Course;
import com.studyprogress.objects.Curriculum;
import com.studyprogress.objects.University;
import com.studyprogress.properties.GlobalProperties;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// TODO: check if valid file!
public class XMLParser {

    private XmlPullParserFactory xmlPullParseFactory;
    private XmlPullParser xmlPullParser;
    private ArrayList<Curriculum> curricula = null;
    private ArrayList<University> universities = null;

    private static ArrayList<Course> currentCourses = null;
    private static Curriculum currentCurriculum = null;
    private static University currentUniversity = null;

    private InputStream inputStream;
    private static XMLParser instance = null;

    public XMLParser(InputStream inputStream) {
        currentCurriculum = new Curriculum();
        currentUniversity = new University();
        currentCourses = new ArrayList<Course>();
        this.inputStream = inputStream;
    }

    public static XMLParser getInstance(InputStream is) {
        if (instance == null) {
            instance = new XMLParser(is);

        } else {
            instance.setInputStream(is);
        }
        return instance;
    }

    public void setInputStream(InputStream is) {
        this.inputStream = is;
    }

    public void clearCurrentCourses() {
        currentCourses.clear();
    }

    public ArrayList<Curriculum> getCurricula() {
        return curricula;

    }

    public void addCourseToCurrentCourses(Course course) {
        currentCourses.add(course);

    }

    public void parseUniversities() {
        try {
            int eventType;

            xmlPullParseFactory = XmlPullParserFactory.newInstance();
            xmlPullParseFactory.setNamespaceAware(true);
            xmlPullParser = xmlPullParseFactory.newPullParser();
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
                    false);
            eventType = xmlPullParser.getEventType();
            University currentUniversity = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        universities = new ArrayList<University>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = xmlPullParser.getName();
                        if (name.equals("university")) {
                            currentUniversity = new University();
                        } else if (currentUniversity != null) {
                            if (name.equals("name")) {
                                currentUniversity.setName(xmlPullParser.nextText());
                            }
                            if (name.equals("id")) {
                                // TODO: test with no int
                                currentUniversity.setId(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            // TODO: more
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        name = xmlPullParser.getName();
                        if (name.equals("university") && currentUniversity != null) {
                            universities.add(currentUniversity);
                            currentUniversity = null;
                        }
                }
                eventType = xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<University> getAllUniversities() {
        return universities;
    }

    public void parseCourses(boolean isSavedFile) {
        if (currentCourses != null)
            clearCurrentCourses();
        int eventType;
        try {

            xmlPullParseFactory = XmlPullParserFactory.newInstance();
            xmlPullParseFactory.setNamespaceAware(true);

            xmlPullParser = xmlPullParseFactory.newPullParser();
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
                    false);

            eventType = xmlPullParser.getEventType();
            Course currentCourse = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        name = xmlPullParser.getName();

                        if (name.equals("courses")) {
                            if (xmlPullParser.getAttributeCount() > 0
                                    && isSavedFile == true) {

                                String universityName = (xmlPullParser
                                        .getAttributeValue(null, "uname"));

                                String curriculumName = xmlPullParser.getAttributeValue(
                                        null, "cname");
                                int studId = Integer.parseInt(xmlPullParser
                                        .getAttributeValue(null, "cid"));
                                int studMode = Integer.parseInt(xmlPullParser
                                        .getAttributeValue(null, "cmode"));

                                setCurrentCurriculum(universityName, curriculumName, studMode, studId);
                                setCurrentUniversity(universityName, 99);

                            }
                            currentCourses = new ArrayList<Course>();
                        }
                        if (name.equals("course")) {

                            currentCourse = new Course();
                        } else if (currentCourse != null) {
                            if (name.equals("name")) {
                                currentCourse.setCourseName(xmlPullParser
                                        .nextText());
                            }
                            if (name.equals("cid")) {
                                currentCourse.setCourseNumber(xmlPullParser
                                        .nextText());
                            }
                            if (name.equals("steop")) {
                                currentCourse.setSteop(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            if (name.equals("mode")) {
                                currentCourse.setMode(xmlPullParser.nextText());
                            }

                            if (name.equals("id")) {
                                // TODO: int parser
                                currentCourse.setCurriculaNumber(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            if (name.equals("semester")) {
                                currentCourse.setSemester(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            if (name.equals("ects")) {
                                currentCourse.setEcts(Float
                                        .parseFloat(xmlPullParser.nextText()));
                            }
                            if (isSavedFile) {
                                if (name.equals("status")) {
                                    currentCourse.setStatus(Integer
                                            .parseInt(xmlPullParser.nextText()));
                                }
                            }
                            // TODO: more
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        name = xmlPullParser.getName();
                        if (name.equals("course") && currentCourse != null) {
                            currentCourses.add(currentCourse);
                            currentCourse = null;
                        }
                }
                eventType = xmlPullParser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setCurrentCurriculum(String universityName, String studName, int studMode, int studId) {
        currentCurriculum.setUniversityName(universityName);
        currentCurriculum.setName(studName);
        currentCurriculum.setCurriculumId(studId);
        currentCurriculum.setMode(studMode);
    }

    public void setCurrentUniversity(String name, int id) {

        currentUniversity.setName(name);
        currentUniversity.setId(id);
    }

    // TODO: names
    public void parseCurricula() {
        int eventType;
        try {

            xmlPullParseFactory = XmlPullParserFactory.newInstance();
            xmlPullParseFactory.setNamespaceAware(true);
            xmlPullParser = xmlPullParseFactory.newPullParser();
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
                    false);
            eventType = xmlPullParser.getEventType();

            Curriculum currentCurriculum = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        curricula = new ArrayList<Curriculum>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = xmlPullParser.getName();
                        if (name.equals("curriculum")) {
                            currentCurriculum = new Curriculum();
                        } else if (currentCurriculum != null) {
                            if (name.equals("name")) {
                                currentCurriculum.setName(xmlPullParser.nextText());
                            }
                            if (name.equals("mode")) {
                                currentCurriculum.setMode(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            if (name.equals("id")) {
                                // TODO: test with no int
                                currentCurriculum.setCurriculumId(Integer
                                        .parseInt(xmlPullParser.nextText()));
                            }
                            // TODO: more
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        name = xmlPullParser.getName();
                        if (name.equals("curriculum") && currentCurriculum != null) {
                            curricula.add(currentCurriculum);
                            currentCurriculum = null;
                        }
                }
                eventType = xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getCurriculumMode(String name) {
        for (int i = 0; i < curricula.size(); i++)
            if (curricula.get(i).getName().equals(name)) {
                return curricula.get(i).getMode();
            }
        return 0;
    }

    public Curriculum getCurrentCurriculum() {
        return currentCurriculum;
    }

    public University getCurrentUniversity() {
        return currentUniversity;
    }

    public ArrayList<String> getCurriculaNames()
            throws XmlPullParserException, IOException {
        ArrayList<String> curriculaNames = new ArrayList<String>();

        for (int i = 0; i < curricula.size(); i++) {
            curriculaNames.add(curricula.get(i).getName());
        }
        return curriculaNames;
    }

    public ArrayList<String> getUniversityNames()
            throws XmlPullParserException, IOException {
        ArrayList<String> universityNames = new ArrayList<String>();

        for (int i = 0; i < universities.size(); i++) {
            universityNames.add(universities.get(i).getName());
        }
        return universityNames;
    }

    public int getCurriculumIdWithName(String name) {
        for (int i = 0; i < curricula.size(); i++)
            if (curricula.get(i).getName().equals(name)) {
                return curricula.get(i).getCurriculumId();
            }
        return 0;
    }

    public int getUniversityIdWithName(String name) {
        for (int i = 0; i < universities.size(); i++)
            if (universities.get(i).getName().equals(name)) {
                return universities.get(i).getId();
            }
        return 0;
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public void setStatusOfCurrentCourseTo(int index, int status) {
        // 0-not 1-progress 2-done
        if (index != -1 && currentCourses.get(index) != null)
            currentCourses.get(index).setStatus(status);
    }

    public String[] getCurrentCoursesNames() throws XmlPullParserException,
            IOException {

        String[] coursesNames = new String[currentCourses.size()];

        for (int i = 0; i < currentCourses.size(); i++) {
            coursesNames[i] = currentCourses.get(i).getCourseName();
        }

        return coursesNames;
    }

    public String[] getCourseNamesWithModesOfSemester(int semesterNo) {
        String[] coursesNames = new String[currentCourses.size()];
        int j = 0;
        int numNotUsedArraySlots = 0;
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).getSemester() == semesterNo) {
                coursesNames[j] = currentCourses.get(i).getCourseName() + " " + currentCourses.get(i).getMode();
                j++;
            } else {
                numNotUsedArraySlots++;
            }
        }
        String[] coursesBySemester = new String[currentCourses.size()
                - numNotUsedArraySlots];
        for (int i = 0; i < (currentCourses.size() - numNotUsedArraySlots); i++)
            coursesBySemester[i] = coursesNames[i];

        return coursesBySemester;

    }


    public void deleteCourse(Course course) {
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(course))
                currentCourses.remove(i);
        }
    }

    public float getEctsByCourse(Course course) {
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(course)) {
                return currentCourses.get(i).getEcts();
            }
        }
        return 0;
    }

    public String getCourseNumberByCourse(Course course) {
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(course)) {
                return currentCourses.get(i).getCourseNumber();
            }
        }
        return "";
    }

    public int getCourseSteopByCourse(Course course) {
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(course)) {
                return currentCourses.get(i).getSteop();
            }
        }
        return -1;
    }

    public String getCourseModeByCourse(Course course) {
        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(course)) {
                return currentCourses.get(i).getMode();
            }
        }
        return "";
    }

    public float getCurrentEcts() {
        float currentEcts = 0;
        for (int i = 0; i < getCurrentCourses().size(); i++) {
            if ((getCurrentCourses().get(i).getStatus() == GlobalProperties.STATUS_DONE))
                currentEcts += getCurrentCourses().get(i).getEcts();
        }
        return currentEcts;
    }

    public float getAllEcts() {
        float allEcts = 0;
        for (int i = 0; i < getCurrentCourses().size(); i++) {

            allEcts += getCurrentCourses().get(i).getEcts();
        }
        return allEcts;
    }

    public Course getCourseByNameInList(String courseNameInList) {
        for (int i = 0; i < getCurrentCourses().size(); i++) {
            String tempCourseNameInList = getCurrentCourses().get(i).getCourseName() + " " + getCurrentCourses().get(i).getMode();
            if (tempCourseNameInList.equals(courseNameInList))
                return getCurrentCourses().get(i);
        }
        return null;
    }


}