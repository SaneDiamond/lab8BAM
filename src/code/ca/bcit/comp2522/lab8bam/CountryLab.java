package ca.bcit.comp2522.lab8bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CountryLab {
    private static final int FIRST_CHAR_IDX = 0;
    private static final String DEFAULT_OUTPUT = "data.txt";

    public static void main(final String[] args) throws IOException {

        final Path pathMatches;
        final Path pathCountries;

        pathMatches = Paths.get("src", "code", "ca", "bcit", "comp2522", "lab8bam", "matches");
        pathCountries = Paths.get("week8countries.txt");

        if(Files.notExists(pathMatches)) {
            Files.createDirectory(pathMatches);
        }

        if(Files.notExists(pathCountries)){
            // Marcus: you should treat the error, not throw it.
            throw new IOException("File not found.");
        }

        final List<String> countries;
        countries = Files.readAllLines(pathCountries);

        // --------------------------------------------
        // Q3
        // countryNamesStartingWithA(countries, pathMatches);

        // --------------------------------------------
        // Q6
        sortCountries(countries, pathMatches);

    }

    private static void sortCountries(final List<String> countries, final Path pathMatches) {
        final List<String> sortedCountries;
        final Stream<String> sortedStream;

        sortedStream = filteredCountryStream(countries)
                .sorted();
        sortedCountries = streamToListWithHeader(sortedStream, "Sorted countries:");

        outputResult(sortedCountries, pathMatches);
    }

    private static void countryNamesStartingWithA(final List<String> countries, final Path pathMatches) {
        final List<String> countriesStartsWithA;
        countriesStartsWithA = countriesThatStartWith(countries, 'A');

        outputResult(countriesStartsWithA, pathMatches);
    }

    /* Returns a List with all countries that start with a specific letter. */
    private static List<String> countriesThatStartWith(final List<String> countries,
                                                       final char initial) {

        final Stream<String> countriesStartingWith;

        countriesStartingWith = filteredCountryStream(countries)
                .filter(country -> startsWith(country, initial));

        return streamToListWithHeader(countriesStartingWith, "Country names starting with 'A':");
    }

    /* Returns true if  */
    private static boolean startsWith(final String str, final char initial) {

        final char firstChar;
        final char initialChar;

        firstChar = Character.toLowerCase(str.charAt(FIRST_CHAR_IDX));
        initialChar = Character.toLowerCase(initial);

        return firstChar == initialChar;
    }

    /* Tranforms a stream into a List<String> with the specified header. */
    private static List<String> streamToListWithHeader(final Stream<String> countryStream,
                                                       final String header) {

        final List<String> listWithHeader;

        listWithHeader = countryStream.collect(ArrayList::new,
                List::add,
                List::addAll);
        listWithHeader.addFirst(header);

        return listWithHeader;
    }

    private static void outputResult(final List<String> countriesList, final Path pathMatches) {
        final Path path;
        path = pathMatches.resolve(DEFAULT_OUTPUT);

        try {
            Files.write(path, countriesList, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /* Returns a stream of the list of countries with no empty or null countries in the list. */
    private static Stream<String> filteredCountryStream(final List<String> countries) {

        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank() && !p.isEmpty());

    }

}