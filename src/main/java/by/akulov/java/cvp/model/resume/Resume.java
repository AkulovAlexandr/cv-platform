package by.akulov.java.cvp.model.resume;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Education;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.Job;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "resumes")
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) default NULL")
    private String title;
    @Column(name = "common", columnDefinition = "TEXT(1000) default NULL")
    private String commonInfo;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Skill> skills;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Job.class)
    private Collection<Experience> jobs;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Education.class)
    private Collection<Experience> educations;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Contact> contacts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PlatformUser platformUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(id, resume.id) && Objects.equals(title, resume.title) && Objects.equals(commonInfo, resume.commonInfo) && Objects.equals(platformUser, resume.platformUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, commonInfo, platformUser);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", commonInfo='" + commonInfo + '\'' +
                ", platformUser=" + platformUser +
                '}';
    }
}
