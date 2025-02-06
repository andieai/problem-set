package com.directa24.main.challenge.controller;

import com.directa24.main.challenge.dto.DirectorResponseDTO;
import com.directa24.main.challenge.exception.ResourceNotFoundException;
import com.directa24.main.challenge.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class ChallengeController {

    @Autowired
    ChallengeService challengeService;

    @GetMapping
    public DirectorResponseDTO getDirectors(@RequestParam(name = "threshold") int threshold) throws ResourceNotFoundException {
        List<String> directors =
                challengeService.getDirectors(threshold);

        DirectorResponseDTO directorResponseDTO = new DirectorResponseDTO();
        directorResponseDTO.setDirectors(directors);
        return directorResponseDTO;
    }
}
