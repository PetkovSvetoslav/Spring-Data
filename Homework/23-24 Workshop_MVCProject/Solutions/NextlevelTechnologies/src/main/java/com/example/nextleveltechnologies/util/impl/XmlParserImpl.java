package com.example.nextleveltechnologies.util.impl;

import com.example.nextleveltechnologies.util.XmlParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Component
public class XmlParserImpl implements XmlParser {

    private JAXBContext jaxbContext;

    @SuppressWarnings("unchecked")
    @Override
    public <O> O parseXml(String data, Class<O> objectClass) throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(data);

        return (O) unmarshaller.unmarshal(reader);
    }
}
