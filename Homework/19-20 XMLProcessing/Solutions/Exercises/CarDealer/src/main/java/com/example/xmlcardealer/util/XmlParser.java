package com.example.xmlcardealer.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <E> void writeToFile(String filePath, E entity) throws JAXBException;

    <T> T fromFile(String filePath, Class<T> type) throws JAXBException;
}
