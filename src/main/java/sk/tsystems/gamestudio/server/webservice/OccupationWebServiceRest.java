package sk.tsystems.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tsystems.gamestudio.entity.Occupation;
import sk.tsystems.gamestudio.service.OccupationService;

import java.util.List;

@RestController
@RequestMapping("/api/occupation")
public class OccupationWebServiceRest {

    @Autowired
    OccupationService occupationService;

    @GetMapping
    public List<Occupation> getOccupations() {
        return occupationService.getOccupations();
    }

    @PostMapping
    public void addOccupation(@RequestBody Occupation occupation) {
        occupationService.addOccupation(occupation);
    }
}
