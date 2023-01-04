package com.example.xmlcardealer.util.impl;

import com.example.xmlcardealer.util.XmlParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {

    private JAXBContext context;

    @Override
    public <E> void writeToFile(String filePath, E entity) throws JAXBException {
        context = JAXBContext.newInstance(entity.getClass());

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(entity, new File(filePath));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T fromFile(String filePath, Class<T> type) throws JAXBException {
        context = JAXBContext.newInstance(type);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new File(filePath));
    }
}
