package net.javaguides.usermanagement.utl;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import net.javaguides.usermanagement.model.User;

/**
 * HibernateUtil - Bu, hazýrda bekletme SessionFactory'yi baþlatmak için yardýmcý sýnýftýr. 
 * Çoðu Hazýrda Bekletme uygulamasýnda, SessionFactory uygulama baþlatma sýrasýnda bir kez baþlatýlmalýdýr.
 * Tek örnek daha sonra belirli bir iþlemdeki tüm kodlar tarafýndan kullanýlmalý ve bu tek SessionFactory
 * kullanýlarak herhangi bir Oturum oluþturulmalýdýr. SessionFactory iþ parçacýðý için güvenlidir
 * ve paylaþýlabilir; a Oturum tek iþ parçacýklý bir nesnedir.
 *
 */
public class HibernateUtil {
 private static SessionFactory sessionFactory;

 public static SessionFactory getSessionFactory() {
  if (sessionFactory == null) {
   try {
    Configuration configuration = new Configuration();

    // hibernate.cfg.xml'nin özelliklerine eþdeðer Hazýrda Bekletme ayarlarý için Java kodunu göstermektedir
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
    // JPA varlýk eþleme sýnýfý ekleme
    configuration.addAnnotatedClass(User.class);

    // StandardServiceRegistryBuilder - Standart ServiceRegistry örnekleri için oluþturucu.
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties()).build();
    System.out.println("Hibernate Java Config serviceRegistry created");
    /*
     * ServiceRegistry, Hazýrda Bekletme'nin önyükleme sýrasýnda ve çalýþma 
     * zamanýnda ihtiyaç duyacaðý hizmetleri tutar.
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