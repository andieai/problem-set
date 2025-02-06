package com.directa24.main.challenge.service;

import com.directa24.main.challenge.dto.MovieDTO;
import com.directa24.main.challenge.dto.MovieResponseDTO;
import com.directa24.main.challenge.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final String MOVIES_URL = "https://wiremock.dev.eroninternational.com/api/movies/search?page=";
    @Override
    public List<String> getDirectors(int threshold) throws ResourceNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        int currentPage = 1;

        ResponseEntity<MovieResponseDTO> response;
        MovieResponseDTO responseDTO;
        List<MovieDTO> movies = new ArrayList<>();

        do {
            response = restTemplate.getForEntity(MOVIES_URL + currentPage, MovieResponseDTO.class);
            responseDTO = response.getBody();
            if(responseDTO != null) {
                movies.addAll(responseDTO.getData());
            } else {
                throw new ResourceNotFoundException("Data not found.");
            }
            currentPage++;
        } while (currentPage <= responseDTO.getTotalPages());

        Map<String, Long> movieCountPerDirector = movies
                .stream()
                .collect(Collectors.groupingBy(
                    MovieDTO::getDirector, Collectors.counting()));

        return movieCountPerDirector.entrySet()
                .stream()
                .filter(m -> m.getValue() > threshold)
                .map(Map.Entry::getKey).sorted().collect(Collectors.toList());
    }

}
