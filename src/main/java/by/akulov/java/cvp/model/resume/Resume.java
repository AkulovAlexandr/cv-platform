package by.akulov.java.cvp.model.resume;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Experience;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "resumes")
@Data
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
    @OneToMany(mappedBy = "resume", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Collection<Skill> skills;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Collection<Experience> experiences;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Collection<Contact> contacts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PlatformUser platformUser;

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
                ", skills=" + skills +
                ", contacts=" + contacts +
                ", user_id=" + platformUser.getId() +
                '}';
    }
}
