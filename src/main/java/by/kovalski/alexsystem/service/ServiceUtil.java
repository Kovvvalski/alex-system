package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.entity.AbstractPerson;
import by.kovalski.alexsystem.exception.ServiceException;

public class ServiceUtil {
  public static final String TELEPHONE_NUMBER_REGEX = "^\\D*(?:\\d\\D*){12,}$";
  public static final String EMAIL_REGEX = """
          ^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]\
          {0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""";

  public static void validateAbstractPerson(AbstractPerson abstractPerson) throws ServiceException {
    if (!abstractPerson.getEmail().matches(EMAIL_REGEX)) {
      throw new ServiceException("Not valid email");
    }

    if (!abstractPerson.getTelephoneNumber().matches(TELEPHONE_NUMBER_REGEX)) {
      throw new ServiceException("Not valid telephone number");
    }
  }
}
