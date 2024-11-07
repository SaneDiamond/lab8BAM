package ca.bcit.comp2522.lab8bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountryLab {
    public static void main(final String[] args) throws IOException {

        final Path pathMatches;
        final Path pathCountries;

        pathMatches = Paths.get("src", "code", "ca", "bcit", "comp2522", "lab8bam", "matches");
        pathCountries = Paths.get("week8countries.txt");
        if (Files.notExists(pathMatches)) {
            Files.createDirectory(pathMatches);
        }

        if (Files.notExists(pathCountries)) {
            throw new IOException("File not found.");
        }

        try {
            final List<String> countries;
            countries = Files.readAllLines(pathCountries);
            countries.forEach(System.out::println);
        } catch (final IOException e) {
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

    private static Stream<Character> uniqueLetters(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .flatMap(p -> p.chars().mapToObj(c -> (char) c))
                .distinct();
    }

    private static Stream<String> shortestCountryName(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .min(Comparator.comparingInt(String::length))
                .stream();
    }

    private static Stream<String> countryNamesToCount(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .map(country -> String.format("%s: %d characters", country, country.length()));
    }
    /**
     * Returns a list of countries with names that have more than 10 letters.
     *
     * @param countries a list of country names
     * @return a list of country names with more than 10 letters
     */    private static List<String> LongCountries(final List<String> countries) {
        final List<String> countriesLong;

        countriesLong = filteredCountryStream(countries)
                .filter(count -> count.length() >= 10)
                .collect(Collectors.toList());
        return countriesLong;
    }
    /**
     *
     * Writes a list of countries whose names end with "land".
     *
     * @param countries a list of country names
     * @return a list of country names that end with "land" and have more than 10 letters
     */
    public static List<String> writeCountriesEndingWithLand(final List<String> countries) {
        final List<String> countriesNamesEndWithLand;

        countriesNamesEndWithLand = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.endsWith("land"))
                .collect(Collectors.toList());

        return LongCountries(countriesNamesEndWithLand);
    }
    /**
     * Returns a list of countries sorted in reverse alphabetical order.
     *
     * @param countries a list of country names
     * @return a list of country names sorted in reverse alphabetical order
     */
    private static List<String> sortedNamesDescending(final List<String> countries) {
        final List<String> sortedCountries;

        sortedCountries = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return sortedCountries;
    }
    /**
     * Returns the longest country name from a list of countries.
     *
     * @param countries a list of country names
     * @return the longest country name
     */
    private static String longestCountryName(final List<String> countries) {
        final String longestCountry;

        longestCountry = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .max(Comparator.comparingInt(String::length))
                .orElse("No countries found");

        return longestCountry;
    }

    /**
     * Returns a list of countries whose names consist of more than one word.
     *
     * @param countries a list of country names
     * @return a list of country names with more than one word
     */
    private static List<String> countriesWithMoreThanOneWord(final List<String> countries) {
        final List<String> multiWordCountries;

        multiWordCountries = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.trim().contains(" "))
                .collect(Collectors.toList());

        return multiWordCountries;
    }

}