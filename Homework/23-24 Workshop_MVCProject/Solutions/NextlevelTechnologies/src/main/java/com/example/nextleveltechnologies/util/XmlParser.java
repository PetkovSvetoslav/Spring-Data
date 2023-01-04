package com.example.nextleveltechnologies.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <O> O parseXml(String data, Class<O> objectClass) throws JAXBException;
}
