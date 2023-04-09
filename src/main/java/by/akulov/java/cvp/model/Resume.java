package by.akulov.java.cvp.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) default NULL")
    private String title;
    @Column(name = "common", columnDefinition = "TEXT(10000) default NULL")
    private String commonInfo;
    @Column(name = "education", columnDefinition = "TEXT(10000) default NULL")
    private String educationInfo;
    @Column(name = "work_exp", columnDefinition = "TEXT(10000) default NULL")
    private String workExperienceInfo;
    @Column(name = "contacts", columnDefinition = "TEXT(100) default NULL")
    private String contacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(String commonInfo) {
        this.commonInfo = commonInfo;
    }

    public String getEducationInfo() {
        return educationInfo;
    }

    public void setEducationInfo(String educationInfo) {
        this.educationInfo = educationInfo;
    }

    public String getWorkExperienceInfo() {
        return workExperienceInfo;
    }

    public void setWorkExperienceInfo(String workExperienceInfo) {
        this.workExperienceInfo = workExperienceInfo;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(id, resume.id) && Objects.equals(title, resume.title) && Objects.equals(commonInfo, resume.commonInfo) && Objects.equals(educationInfo, resume.educationInfo) && Objects.equals(workExperienceInfo, resume.workExperienceInfo) && Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, commonInfo, educationInfo, workExperienceInfo, contacts);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", commonInfo='" + commonInfo + '\'' +
                ", educationInfo='" + educationInfo + '\'' +
                ", workExperienceInfo='" + workExperienceInfo + '\'' +
                ", contacts='" + contacts + '\'' +
                '}';
    }
}
