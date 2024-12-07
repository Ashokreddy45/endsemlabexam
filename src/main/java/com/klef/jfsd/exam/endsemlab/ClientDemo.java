package com.klef.jfsd.exam.endsemlab;



import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            insertRecords(session);
            performAggregateQueries(session);
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    private static void insertRecords(Session session) {
        Transaction tx = session.beginTransaction();

        Project p1 = new Project("Hospital Management System", 120, 100000.0, "Alice Johnson");
        Project p2 = new Project("Online Shopping Platform", 90, 75000.0, "Bob Smith");
        Project p3 = new Project("Payroll System", 60, 50000.0, "Carol White");
        Project p4 = new Project("Inventory Control System", 45, 45000.0, "David Brown");

        session.save(p1);
        session.save(p2);
        session.save(p3);
        session.save(p4);

        tx.commit();
    }

    private static void performAggregateQueries(Session session) {
        Criteria countCriteria = session.createCriteria(Project.class);
        countCriteria.setProjection(Projections.rowCount());
        Long totalProjects = (Long) countCriteria.uniqueResult();
        System.out.println("Total Number of Projects: " + totalProjects);

        Criteria maxCriteria = session.createCriteria(Project.class);
        maxCriteria.setProjection(Projections.max("budget"));
        Double maxBudget = (Double) maxCriteria.uniqueResult();
        System.out.println("Maximum Budget: " + maxBudget);

        Criteria minCriteria = session.createCriteria(Project.class);
        minCriteria.setProjection(Projections.min("budget"));
        Double minBudget = (Double) minCriteria.uniqueResult();
        System.out.println("Minimum Budget: " + minBudget);

        Criteria sumCriteria = session.createCriteria(Project.class);
        sumCriteria.setProjection(Projections.sum("budget"));
        Double totalBudget = (Double) sumCriteria.uniqueResult();
        System.out.println("Total Budget: " + totalBudget);

        Criteria avgCriteria = session.createCriteria(Project.class);
        avgCriteria.setProjection(Projections.avg("budget"));
        Double avgBudget = (Double) avgCriteria.uniqueResult();
        System.out.println("Average Budget: " + avgBudget);
    }
}
