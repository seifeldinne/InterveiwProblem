package sfh.tuto.TestDemo.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MenuSaver {
	public void saveMenuAndLinks(Session session, Menu menu, List<Link> links) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			links.forEach(link -> {
				link.menu = menu;
				session.save(link);
				session.save(menu);
			});

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}
	}

	@Entity(name = "Link")
	@Table
	public static class Link {
		@Id
		public Integer id;
		@ManyToOne
		@JoinColumn(name = "menuId")
		public Menu menu;
		@Column
		public String text;
	}

	@Entity(name = "Menu")
	@Table
	public static class Menu {
		@Id
		public Integer id;
		@OneToMany(mappedBy = "menu")
		public List<Link> links = new ArrayList<Link>();
		@Column
		public String text;

	}

	public static void main(String[] args) {
		LogManager logManager = LogManager.getLogManager();
		Logger logger = logManager.getLogger("");
		logger.setLevel(Level.OFF);
		Properties prop = new Properties();
		prop.setProperty("hibernate.connection.url", "jdbc:h2:mem:dbl");
		prop.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
		prop.setProperty("hibernate.hbm2ddl.auto", "create");
		SessionFactory sessionFactory = new Configuration().addProperties(prop).addAnnotatedClass(Link.class)
				.addAnnotatedClass(Menu.class).buildSessionFactory();
		Menu menu = new Menu();
		menu.id = 0;
		menu.text = "Main menu";
		Link firstLink = new Link();
		firstLink.id = 0;
		firstLink.text = "Details";
		Link secondLink = new Link();
		secondLink.id = 1;
		secondLink.text = "Contact";
		List<Link> linksToSave = new ArrayList<Link>();
		linksToSave.add(firstLink);
		linksToSave.add(secondLink);
		MenuSaver saver = new MenuSaver();
		saver.saveMenuAndLinks(sessionFactory.openSession(), menu, linksToSave);
		Session session = sessionFactory.openSession();
		Menu menuFromDb = session.get(Menu.class, 0);
		menuFromDb.links.forEach(link -> System.out.println(link.id + " - " + link.text));
		
	}
}
