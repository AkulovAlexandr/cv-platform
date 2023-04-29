package by.akulov.java.cvp.model.resume.experience;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("JOB")
public class Job extends Experience {
}
