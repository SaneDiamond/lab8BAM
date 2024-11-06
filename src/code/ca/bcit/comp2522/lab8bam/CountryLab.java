package ca.bcit.comp2522.lab8bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CountryLab {
    public static void main(final String[] args) throws IOException {

        final Path pathMatches;
        final Path pathCountries;

        pathMatches = Paths.get("src", "code", "ca", "bcit", "comp2522", "lab8bam", "matches");
        pathCountries = Paths.get("week8countries.txt");
        if(Files.notExists(pathMatches)) {
            Files.createDirectory(pathMatches);
        }

        if(Files.notExists(pathCountries)){
            throw new IOException("File not found.");
        }

        try {
            final List<String>countries;
            countries = Files.readAllLines(pathCountries);
            countries.forEach(System.out::println);
        } catch (final IOException e){
            e.printStackTrace();
        }

    }

    /* Returns a stream of the list of countries with no empty or null countries in the list. */
    private static Stream<String> filteredCountryStream(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank() && p.isEmpty());

    }

    private static Stream<String> countriesShorterThan5Letters(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.length() > 5);
    }

    private static Stream<String> countriesContainingUnited(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank() && p.contains("United"));
    }

    private static Stream<Character> uniqueLetters(final List<String> countries){
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .flatMap(p -> p.chars().mapToObj(c -> (char) c))
                .distinct();
    }

    private static Stream<String> shortestCountryName(final List<String> countries){
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .min(Comparator.comparingInt(String::length))
                .stream();
    }

    private static Stream<String> countryNamesToCount(final List<String> countries){
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .map(country -> String.format("%s: %d characters", country, country.length()));
    }
}