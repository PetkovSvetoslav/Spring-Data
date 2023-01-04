package model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @XmlElement(required = true)
    private String name;

    @NonNull
    private Address address;

    @XmlElementWrapper(name = "phone-numbers")
    @XmlElement(name = "phone")
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
