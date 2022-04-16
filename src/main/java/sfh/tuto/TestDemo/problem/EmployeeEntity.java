package sfh.tuto.TestDemo.problem;
import java.util.*;
import java.util.logging.*;
import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.Query;

public class EmployeeEntity {

    @Entity(name = "Section")
    @Table
    public static class Section {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        public Integer id;
        @OneToMany(mappedBy="section")
        public List<Employee> employees = new ArrayList<>();
    }
    
    @Entity(name = "Employee")
    @Table
    public static class Employee {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        public Integer id;
        @Column
        public String name;
        
        @ManyToOne
        public Section section;
    }
    
    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.OFF);
        
        Properties prop = new Properties();

        prop.setProperty("hibernate.connection.url", "jdbc:h2:mem:db1");
        prop.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
        prop.setProperty("hibernate.hbm2ddl.auto", "create");

        SessionFactory sessionFactory = new Configuration().addProperties(prop)
            .addAnnotatedClass(Section.class).addAnnotatedClass(Employee.class)
            .buildSessionFactory();
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Section section = new Section();

        Employee employee = new Employee();
        employee.name = "John Doe";
        employee.section = section;
        
        session.save(section);
        session.save(employee);
        
        session.flush();
        session.clear();
        
        Query<Employee> query = session
            .createQuery("FROM Employee", Employee.class);
        List<Employee> employees = query.list();

        System.out.println(employees.size());
    }
}