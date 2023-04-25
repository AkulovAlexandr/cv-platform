package by.akulov.java.cvp.model.resume.contact;

import by.akulov.java.cvp.model.resume.Resume;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) && Objects.equals(data, contact.data) && type == contact.type && Objects.equals(resume, contact.resume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, type, resume);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", type='" + type + '\'' +
                ", resume_id=" + resume.getId() +
                '}';
    }
}
