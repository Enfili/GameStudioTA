package sk.tsystems.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.CountryService;
import sk.tsystems.gamestudio.service.OccupationService;
import sk.tsystems.gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    @Autowired
    PlayerService playerService;
    @Autowired
    CountryService countryService;
    @Autowired
    OccupationService occupationService;

    private String loggedUser;
    private boolean userAlreadyExists = true;

    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            this.loggedUser = login.trim();
            if (this.loggedUser.length() > 0) {
                if (playerService.getPlayerByUserName(loggedUser) != null) {
                    userAlreadyExists = true;
                } else {
                    userAlreadyExists = false;
                }
                return "redirect:/gamestudio";
            }
        }
        this.loggedUser = null;
        return "redirect:/gamestudio";
    }

    @RequestMapping("/logout")
    public String logout() {
        this.loggedUser = null;
        return "redirect:/gamestudio";
    }

    @RequestMapping("/register")
    public String register(String fullName, Integer selfEvaluation, int country, int occupation) {
        fullName = fullName.trim();
        if (fullName.length() > 0) {
            userAlreadyExists = true;
            playerService.addPlayer(new Player(loggedUser, fullName, selfEvaluation, countryService.getCountries().get(country), occupationService.getOccupations().get(occupation)));
        }
        return "redirect:/gamestudio";
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

    public boolean isUserAlreadyExists() {
        return userAlreadyExists;
    }

    //    @RequestMapping("/loggedUser")
//    @ResponseBody
//    public void loginUser(String login) {
//        System.out.println("login: " + login);
//        this.loggedUser = login.trim();
//    }
}
