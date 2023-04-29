package by.akulov.java.cvp.model.resume.experience;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("EDUCATION")
public class Education extends Experience {
}
