package com.packt.albumstwo;

import java.time.LocalDate;

public record Player(String id, int jerseyNumber, String name, String position, LocalDate dateOfBirth) {
}
