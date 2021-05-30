package net.javaguides.usermanagement.utl;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import net.javaguides.usermanagement.model.User;

/**
 * HibernateUtil�- Bu, haz�rda bekletme�SessionFactory'yi ba�latmak i�in yard�mc� s�n�ft�r. 
 * �o�u Haz�rda Bekletme uygulamas�nda,�SessionFactory�uygulama ba�latma s�ras�nda bir kez ba�lat�lmal�d�r.
 * Tek �rnek daha sonra belirli bir i�lemdeki t�m kodlar taraf�ndan kullan�lmal� ve bu tek SessionFactory
 * kullan�larak herhangi bir Oturum olu�turulmal�d�r. SessionFactory i� par�ac��� i�in g�venlidir
 * ve payla��labilir; a�Oturum�tek i� par�ac�kl� bir nesnedir.
 *
 */
public class HibernateUtil {
 private static SessionFactory sessionFactory;

 public static SessionFactory getSessionFactory() {
  if (sessionFactory == null) {
   try {
    Configuration configuration = new Configuration();

    // hibernate.cfg.xml'nin �zelliklerine e�de�er Haz�rda Bekletme ayarlar� i�in Java kodunu g�stermektedir
    Properties settings = new Properties();
    settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
    settings.put(Environment.URL, "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT");
    settings.put(Environment.USER, "root");
    settings.put(Environment.PASS, "toor");
    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

    settings.put(Environment.SHOW_SQL, "true");

    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

    settings.put(Environment.HBM2DDL_AUTO, "create-drop");

    configuration.setProperties(settings);
    // JPA varl�k e�leme s�n�f� ekleme
    configuration.addAnnotatedClass(User.class);

    // StandardServiceRegistryBuilder - Standart ServiceRegistry �rnekleri i�in olu�turucu.
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties()).build();
    System.out.println("Hibernate Java Config serviceRegistry created");
    /*
     * ServiceRegistry, Haz�rda Bekletme'nin �ny�kleme s�ras�nda ve �al��ma 
     * zaman�nda ihtiya� duyaca�� hizmetleri tutar.
    */
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    return sessionFactory;

   } catch (Exception e) {
    e.printStackTrace();
   }
  }
  return sessionFactory;
 }
}