package sk.tsystems.gamestudio.minesweeper;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tsystems.gamestudio.entity.*;
import sk.tsystems.gamestudio.service.*;

import java.util.Date;

//@Transactional
public class PlaygroundJPA {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentGroupService studentGroupService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private OccupationService occupationService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    public void play() {

//        scoreService.addScore(new Score("minesweeper", "janko", 1000, new Date()));
//        System.out.println(scoreService.getBestScores("minesweeper"));

//        ratingService.setRating(new Rating("minesweeper", "janko", 1, new Date()));
//        ratingService.getRating("minesweeper", "janko");

//        scoreService.addScore(new Score("kamene", "Stefan", 100, new Date()));

//        countryService.addCountry(new Country("Slovensko"));
//        countryService.addCountry(new Country("Cesko"));

//        System.out.println(countryService.getCountries());

//        occupationService.addOccupation(new Occupation("student"));
//        occupationService.addOccupation(new Occupation("ziak"));
//        occupationService.addOccupation(new Occupation("skolkar"));
//        occupationService.addOccupation(new Occupation("zamestnanec"));
//        occupationService.addOccupation(new Occupation("zivnostnik"));
//        occupationService.addOccupation(new Occupation("nezamestnany"));
//        occupationService.addOccupation(new Occupation("dochodca"));
//        occupationService.addOccupation(new Occupation("invalid"));

//        playerService.addPlayer(new Player("Stefan", "Stefan Korecko", 3, countryService.getCountries().get(1), occupationService.getOccupations().get(1)));
//        playerService.addPlayer(new Player("Stefan", "Stefan Korenko", 4, countryService.getCountries().get(1), occupationService.getOccupations().get(1)));
//        playerService.addPlayer(new Player("Jaroslav", "Jaroslav Pavlicko", 5, countryService.getCountries().get(1), occupationService.getOccupations().get(2)));
//        playerService.addPlayer(new Player("Martin", "Martin Petruska", 9, countryService.getCountries().get(1), occupationService.getOccupations().get(3)));
//        playerService.addPlayer(new Player("Peter", "Peter Zivcak", 1, countryService.getCountries().get(1), occupationService.getOccupations().get(4)));

//        System.out.println(playerService.getPlayersByUserName("Stefan"));
//        System.out.println(playerService.getPlayersByUserName("Stefan2"));

//        System.out.println(occupationService.getOccupations());

    }
}



//        testService.addTest(new Test("minesweeper", "test1"));
//        testService.addTest(new Test("minesweeper", "test2"));
//        testService.addTest(new Test("kamene", "test1"));
//        System.out.println(testService.getTests("minesweeper"));


//        entityManager.persist(new StudyGroup("basic"));
//        entityManager.persist(new StudyGroup("intermediate"));
//        entityManager.persist(new StudyGroup("advanced"));

//        String firstName = "Raweel";
//        String lastName = "Powick";
//        int group = 1;

//        studentGroupService.addStudyGroup(new StudyGroup("basic"));
//        studentGroupService.addStudyGroup(new StudyGroup("intermediate"));
//        studentGroupService.addStudyGroup(new StudyGroup("advanced"));

//        List<StudyGroup> studyGroups = studentGroupService.getStudyGroups();
//        int noOfStudyGroups = studyGroups.size();
//
//        String firstName = "";
//        String lastName = "";
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        while (firstName.length() <= 0 || firstName.length() > 100) {
//            System.out.println("What is your first name?");
//            try {
//                firstName = br.readLine();
//            } catch (Exception e) {
//                System.out.println("Problem with accessing the database.");
//            }
//        }
//        while (lastName.length() <= 0 || lastName.length() > 100) {
//            System.out.println("What is your last name?");
//            try {
//                lastName = br.readLine();
//            } catch (Exception e) {
//                System.out.println("Problem with accessing the database.");
//            }
//        }
//
//        int noOfGroups = studyGroups.size();
//        for (int i = 0; i < noOfGroups; i++) {
//            System.out.println(i + " " + studyGroups.get(i));
//        }
//
//        int group = -1;
//        while (group < 0 || group >= noOfStudyGroups) {
//            System.out.println("Select study group by choosing number.");
//            try {
//                group = Integer.parseInt(br.readLine().toLowerCase());
//            } catch (Exception e) {
//                System.out.println("Problem with accessing the database.");
//            }
//        }
//
//        studentService.addStudent(new Student(firstName, lastName, studyGroups.get(group)));
//        System.out.println(studentService.getStudents(studyGroups.get(group)));

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
