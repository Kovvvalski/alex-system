package by.kovalski.alexsystem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlexSystemApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void jpaTest(){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory( "JavaRush" );
    EntityManager entityManager = emf.createEntityManager();
  }

}
