import model.Address;
import model.People;
import model.Person;
import model.PhoneNumber;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class Main {

    private static final String PEOPLE_XML_PATH = "src/main/resources/people.xml";

    public static void main(String[] args) {
        Address address1 = new Address("England", "Liverpool");
        Address address2 = new Address("France", "Paris");

        Person person1 = new Person(address1);
        person1.getPhoneNumbers().add(new PhoneNumber("0888 888 888"));
        person1.getPhoneNumbers().add(new PhoneNumber("0111 222 333"));

        Person person2 = new Person("Peter", address2);
        person2.getPhoneNumbers().add(new PhoneNumber("0123 456 789"));

        Person person3 = new Person("Dimitar", address1);

        People people = new People();
        people.getPeople().add(person1);
        people.getPeople().add(person2);
        people.getPeople().add(person3);

        // 1. Create JAXBContext
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(People.class);

            // 2. Create Marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 3. Marshal to XML
            marshaller.marshal(people, new File(PEOPLE_XML_PATH));

            // 4. Create Unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // 5. Validation
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("xsd/schema1.xsd"));
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(
                    (ValidationEvent ve) -> {
                        if (ve.getSeverity() != ValidationEvent.WARNING) {
                            System.out.printf("%s: Line:Col[%d:%d] - %s%n",
                                    ve.getSeverity(),
                                    ve.getLocator().getLineNumber(),
                                    ve.getLocator().getColumnNumber(),
                                    ve.getMessage()
                            );
                        }
                        return true;
                    }
            );

            // 6. Unmarshal to People
            FileInputStream fileInputStream = new FileInputStream(PEOPLE_XML_PATH);
            People unmarshalledPeople = (People) unmarshaller.unmarshal(fileInputStream);

            for (Person person : unmarshalledPeople.getPeople()) {
                System.out.printf(
                        "name: %-8.8s | country: %-10.10s | city: %-10.10s | phone numbers: %s%n",
                        person.getName(),
                        person.getAddress().getCountry(),
                        person.getAddress().getCity(),
                        person.getPhoneNumbers()
                                .stream()
                                .map(PhoneNumber::getNumber)
                                .collect(Collectors.joining(", "))
                );
            }

        } catch (JAXBException | SAXException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
