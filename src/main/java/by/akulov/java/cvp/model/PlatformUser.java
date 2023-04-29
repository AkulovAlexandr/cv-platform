package by.akulov.java.cvp.model;

import by.akulov.java.cvp.model.resume.Resume;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "users")
@Data
public class PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String login;
    private String password;
    private String role;
    @OneToMany(mappedBy = "platformUser", cascade = CascadeType.PERSIST)
    private Collection<Resume> resume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformUser platformUser = (PlatformUser) o;
        return Objects.equals(id, platformUser.id) && Objects.equals(name, platformUser.name) && Objects.equals(surname, platformUser.surname) && Objects.equals(login, platformUser.login) && Objects.equals(password, platformUser.password) && Objects.equals(role, platformUser.role) && Objects.equals(resume, platformUser.resume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, role, resume);
    }

    @Override
    public String toString() {
        return "PlatformUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
