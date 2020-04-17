package legoset;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import legoset.model.LegoSet;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LegoSetService {

    private EntityManager em;
    private LegoSetService(EntityManager em) {
        this.em = em;
    }


    public LegoSet create(String number, String name, Year year, int pieces) {
        LegoSet legoSet = new LegoSet(number, name, year, pieces);
        em.persist(legoSet);
        return legoSet;
    }

    public Optional<LegoSet> find(String number) {
        return Optional.ofNullable(em.find(LegoSet.class, number));
    }

    public List<LegoSet> findAll() {
        return em.createQuery("SELECT l FROM LegoSet l ORDER BY l.number", LegoSet.class).getResultList();
    }

    public Long totalPieces() {
        return em.createQuery("SELECT SUM(l.pieces) FROM LegoSet l", Long.class).getSingleResult();
    }

    public void delete(String number) {
        find(number).ifPresent(legoSet -> {
            em.remove(legoSet);
            log.debug("Deleted Lego set {}", number);
        });
    }

    public void deleteAll() {
        long count = em.createQuery("DELETE FROM LegoSet").executeUpdate();
        log.debug("Deleted {} Lego set(s)", count);
    }

    public static void main(String[] args) {
          EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
          EntityManager em = emf.createEntityManager();
          LegoSetService service = new LegoSetService(em);

          em.getTransaction().begin();
          service.create("60073", "Service Truck", Year.of(2015), 233);
          service.create("75211", "Imperial TIE Fighter", Year.of(2018), 519);
          service.create("21034", "London", Year.of(2017), 468);
          em.getTransaction().commit();

          log.info("Lego set 60073: {}", service.find("60073"));
          log.info("Lego set 10212: {}", service.find("10212"));

          log.info("All Lego sets:");
          service.findAll().forEach(log::info);

          log.info("Total number of Lego pieces: {}", service.totalPieces());

          em.getTransaction().begin();
          service.delete("60073");
          em.getTransaction().commit();

          log.info("All Lego sets:");
          service.findAll().forEach(log::info);

          em.getTransaction().begin();
          service.deleteAll();
          em.getTransaction().commit();

          log.info("All Lego sets:");
          service.findAll().forEach(log::info);

          em.close();
          emf.close();
    }

}
