package softuni.exam.service;


import softuni.exam.models.entity.Town;

import java.io.IOException;

//ToDo - Before start App implement this Service and set areImported to return false
public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importTowns() throws IOException;

	Boolean ifExists(String towName);

	Town getTownByName(String towName);
}