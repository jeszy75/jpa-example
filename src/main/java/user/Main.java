package user;

import user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        User user = User.builder().username("linus").password("123456").build();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        System.out.println(user);

        em.getTransaction().begin();
        user.setPassword("secret");
        em.getTransaction().commit();

        System.out.println(em.find(User.class, "linus"));
        System.out.println(user == em.find(User.class, "linus"));

        em.clear();

        System.out.println(em.find(User.class, "linus"));
        System.out.println(user == em.find(User.class, "linus"));

        em.getTransaction().begin();
        //em.merge(user);
        //user.setPassword("ApW6,#g>1");
        User managedUser = em.merge(user);
        managedUser.setPassword("ApW6,#g>1");
        em.getTransaction().commit();
        System.out.println(em.find(User.class, "linus"));

        em.close();
        emf.close();
    }

}
