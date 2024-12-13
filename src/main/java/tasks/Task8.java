package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<Integer> personsId = persons.stream().map(Person::id).collect(toSet());
    Set<Resume> resumes = personService.findResumes(personsId);
    Map<Integer, Set<Resume>> resumePersonIdMap = resumes.stream().collect(groupingBy(Resume::personId, toSet()));

    return persons.stream()
        .map(person -> new PersonWithResumes(person, resumePersonIdMap.getOrDefault(person.id(), Set.of())))
        .collect(toSet());
  }
}
