package com.directa24.main.challenge.service;

import com.directa24.main.challenge.exception.ResourceNotFoundException;

import java.util.List;

public interface ChallengeService {
    List<String> getDirectors(int threshold) throws ResourceNotFoundException;
}
