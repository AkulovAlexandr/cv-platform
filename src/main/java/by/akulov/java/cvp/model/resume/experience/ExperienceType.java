package by.akulov.java.cvp.model.resume.experience;

public enum ExperienceType {

    JOB("JOB"), EDUCATION("EDUCATION");

    private final String name;

    ExperienceType(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
