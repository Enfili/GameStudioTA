package sk.tsystems.gamestudio.minesweeper;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tsystems.gamestudio.entity.*;
import sk.tsystems.gamestudio.minesweeper.consoleui.WrongFormatException;
import sk.tsystems.gamestudio.service.StudentGroupService;
import sk.tsystems.gamestudio.service.StudentService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

//@Transactional
public class PlaygroundJPA {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentGroupService studentGroupService;

    public void play() {
//        entityManager.persist(new StudyGroup("basic"));
//        entityManager.persist(new StudyGroup("intermediate"));
//        entityManager.persist(new StudyGroup("advanced"));

//        String firstName = "Raweel";
//        String lastName = "Powick";
//        int group = 1;

//        studentGroupService.addStudyGroup(new StudyGroup("basic"));
//        studentGroupService.addStudyGroup(new StudyGroup("intermediate"));
//        studentGroupService.addStudyGroup(new StudyGroup("advanced"));

        List<StudyGroup> studyGroups = studentGroupService.getStudyGroups();
        int noOfStudyGroups = studyGroups.size();

        String firstName = "";
        String lastName = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (firstName.length() <= 0 || firstName.length() > 100) {
            System.out.println("What is your first name?");
            try {
                firstName = br.readLine();
            } catch (Exception e) {
                System.out.println("Problem with accessing the database.");
            }
        }
        while (lastName.length() <= 0 || lastName.length() > 100) {
            System.out.println("What is your last name?");
            try {
                lastName = br.readLine();
            } catch (Exception e) {
                System.out.println("Problem with accessing the database.");
            }
        }

        int noOfGroups = studyGroups.size();
        for (int i = 0; i < noOfGroups; i++) {
            System.out.println(i + " " + studyGroups.get(i));
        }

        int group = -1;
        while (group < 0 || group >= noOfStudyGroups) {
            System.out.println("Select study group by choosing number.");
            try {
                group = Integer.parseInt(br.readLine().toLowerCase());
            } catch (Exception e) {
                System.out.println("Problem with accessing the database.");
            }
        }

        studentService.addStudent(new Student(firstName, lastName, studyGroups.get(group)));
        System.out.println(studentService.getStudents(studyGroups.get(group)));

//        entityManager.persist(new Student(firstName, lastName, studyGroups.get(group)));

//        List<Student> students = entityManager.createQuery("select s from Student s").getResultList();
//        System.out.println(students);



        /*
//        entityManager.persist(new Score("minesweeper", "stefan2", 10, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan2", 3, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan2", 5, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan5", 1, new Date()));
//        entityManager.persist(new Comment("minesweeper", "stefan2", "hihi", new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan", 4, new Date()));

        String game = "minesweeper";
        String username = "stefan";

//        Rating rating = new Rating("minesweeper", "stefan2", 5, new Date());

        Rating ratingToWrite = null;

        try {
            ratingToWrite = (Rating) entityManager
                    .createQuery("select r from Rating r where r.username = :user and r.game = :game")
                    .setParameter("user", username)
                    .setParameter("game", game)
                    .getSingleResult();
            ratingToWrite.setRating(5);
            ratingToWrite.setRatedOn(new Date());
        } catch (NoResultException e) {
            entityManager.persist(new Rating(game, "stefan", 3, new Date()));
        }


//        if (entityManager
//                .createQuery("select r from Rating r where r.game = :myGame and username = :myUsername")
//                .setParameter("myGame", game)
////                .setParameter("myUsername", username)
////                .getResultList()
////                .size() == 0) {
////            entityManager.persist(rating);
////        } else {
////            entityManager
////                    .createNativeQuery("update rating set rating = ?, rated_on = ? where game = ? and username = ?")
////                    .setParameter(3, game)
////                    .setParameter(4, username)
////                    .setParameter(1, rating.getRating())
////                    .setParameter(2, rating.getRatedOn());
//        }

//        List<Score> bestScores =
//                entityManager
//                .createQuery("select s from Score s where s.game = :myGame order by s.points desc")
//                .setParameter( "myGame", game)
//                .getResultList();
//
//        Number number =
//                (Number) (entityManager
//                        .createQuery("select avg(r.rating) from Rating r where r.game = :myGame")
//                        .setParameter("myGame", game)
//                        .getSingleResult());
//        double avgRating = (double) number;
//
//        int rating =
//                entityManager
//                        .createQuery("select r.rating from Rating r where r.game = :myGame and r.username = :myUsername")
//                        .setParameter("myGame", game)
//                        .setParameter("myUsername", username)
//                        .getFirstResult();
//
//        List<Comment> comments =
//                entityManager
//                .createQuery("select c from Comment c where c.game = :myGame")
//                .setParameter( "myGame", game)
//                .getResultList();
//
//        System.out.println("BEST SCORES: " + bestScores);
//        System.out.println("AVERAGE RATING: " + avgRating);
//        System.out.println("RATING: " + rating);
//        System.out.println("COMMENTS: " + comments);
         */
    }
}
