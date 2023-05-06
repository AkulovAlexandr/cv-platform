package by.akulov.java.cvp.model.resume;

import by.akulov.java.cvp.model.Photo;
import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Experience;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @OneToOne(fetch = FetchType.EAGER)
    private Photo photo;
    @OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
    private Collection<Skill> skills;
    @OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
    @Where(clause = "type = 'JOB'")
    private Collection<Experience> jobs;
    @OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
    @Where(clause = "type = 'EDUCATION'")
    private Collection<Experience> educations;
    @OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
    private Collection<Contact> contacts;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PlatformUser platformUser;
    private boolean published;



    public Collection<Experience> getExperiences() {
        List<Experience> experiences = new ArrayList<>();
        experiences.addAll(jobs);
        experiences.addAll(educations);
        return experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(id, resume.id) && Objects.equals(title, resume.title) && Objects.equals(commonInfo, resume.commonInfo) && Objects.equals(photo, resume.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, commonInfo, photo);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", commonInfo='" + commonInfo + '\'' +
                ", photo=" + photo +
                '}';
    }
}
