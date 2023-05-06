package by.akulov.java.cvp.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "photos")
@Data
public class Photo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Photo() {

    }

    public Photo(String photoName) {
        this.name = photoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) && Objects.equals(name, photo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

