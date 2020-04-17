package legoset;

import java.time.Year;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import legoset.model.LegoSet;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static void createLegoSets() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new LegoSet("60073", "Service Truck", Year.of(2015), 233));
            em.persist(new LegoSet("75211", "Imperial TIE Fighter", Year.of(2018), 519));
            em.persist(new LegoSet("21034", "London", Year.of(2017), 468));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static List<LegoSet> getLegoSets() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM LegoSet l ORDER BY l.number", LegoSet.class).getResultList();
        } finally {
            em.close();
        }
    }

    private static Long getTotalPieces() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT SUM(l.pieces) FROM LegoSet l", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    private static void deleteLegoSets() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            long count = em.createQuery("DELETE FROM LegoSet").executeUpdate();
            log.info("Deleted {} Lego set(s)", count);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        createLegoSets();
        log.info("All Lego sets:");
        getLegoSets().forEach(log::info);
        log.info("Total number of Lego pieces: {}", getTotalPieces());
        deleteLegoSets();
        emf.close();
    }

}
