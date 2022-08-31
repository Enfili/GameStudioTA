package sk.tsystems.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GamestudioController {

    @Autowired
    private UserController userController;

    @RequestMapping("/gamestudio")
    public String mainPage(Model model) {
        prepareModel(model);
        return "gamestudio";
    }

    private void prepareModel(Model model) {
        model.addAttribute("logged", userController.isLogged());
        model.addAttribute("existingUser", userController.isUserAlreadyExists());
    }

    //    @RequestMapping("/gamestudio")
//    private String mainPage() {
//        return "gamestudio";
//    }

//    @RequestMapping("/loggedIn")
//    @ResponseBody
//    public boolean isLoggedIn() { return userController.isLogged(); }
}
