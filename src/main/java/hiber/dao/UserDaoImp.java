package hiber.dao;

import hiber.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   //1 метод Определяет, нужно ли создавать нового пользователя или обновлять существующего
   @Override
   public void add(User user) {
      if (user.getId() == null) {
         entityManager.persist(user);
      } else {
         entityManager.merge(user);
      }
   }

   //2 метод для поиска пользователя по первичному ключу (id)
   @Override
   public User findById(Long id) {
      return entityManager.find(User.class, id);
   }

   //3 метод Возвращает список всех пользователей в базе данных
   @Override
   public List<User> listUsers() {
      return entityManager.createQuery("from User", User.class).getResultList();
   }

   //4 метод удаления
   @Override
   public void delete(Long id) {
      User user = findById(id);
      if (user != null) {
         entityManager.remove(user);
      }
   }
}