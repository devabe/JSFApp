package ch.gibm.dao;

import java.util.HashMap;
import java.util.Map;

import com.model.User;

import ch.gibm.entity.Person;

public class PersonDAO extends GenericDAO<Person> {

	private static final long serialVersionUID = 1L;

	public PersonDAO() {
		super(Person.class);
	}

	public Person findPersonWithAllLanguages(int personId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("personId", personId);

		return super.findOneResult(Person.FIND_PERSON_BY_ID_WITH_LANGUAGES, parameters);
	}

	public void delete(Person person) {
        	super.delete(person.getId(), Person.class);
	}
	
    public Person findPersonByLogin(String login){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("login", login);     
 
        return super.findOneResult(Person.FIND_PERSON_BY_LOGIN, parameters);
    }
}
