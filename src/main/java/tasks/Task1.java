package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  /*
  Ассимптотика O(n):
  - сначала я прохожусь один раз по сету с персонами и раскладываю их в мапу с ключом в виде айди персоны
  - далее я прохожусь по списку с айди персон и в таком же порядке раскладываю персоны из мапы по айди персоны

  Получается финально O(n) + O(n) = 2*O(n), и по правилу ассимптотики мы опускаем константу, и остается O(n).
  Все операции внутри циклов (добавление в конец списка, добавление/получение из списка) это О(1).
   */
  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personsIdMap = persons.stream().collect(Collectors.toMap(Person::id, Function.identity()));
    List<Person> result = new ArrayList<>(personIds.size());

    for (Integer personId : personIds) {
      result.add(personsIdMap.get(personId));
    }

    return result;
  }
}
