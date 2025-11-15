package com.packt.footballresource;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/football")
@RestController
public class FootballController {
    @GetMapping("/teams")
    public List<String> getTeams() {
        return List.of("Argentina", "Australia", "Brazil");
    }

    @PostMapping("/teams")
    public String addTeam(@RequestBody String teamName) {
        return teamName + " added";
    }
}
