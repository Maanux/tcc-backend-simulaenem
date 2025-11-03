package SimulaEnem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RotaParaTesteBackProd {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
