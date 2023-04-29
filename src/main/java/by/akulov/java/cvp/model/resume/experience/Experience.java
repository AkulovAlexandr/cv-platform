package by.akulov.java.cvp.model.resume.experience;

import by.akulov.java.cvp.model.resume.Resume;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer startYear;
    private Integer endYear;
    private String description;
    private String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(startYear, that.startYear) && Objects.equals(endYear, that.endYear) && Objects.equals(description, that.description) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startYear, endYear, description, type);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", resume_id=" + resume.getId() +
                '}';
    }
}
