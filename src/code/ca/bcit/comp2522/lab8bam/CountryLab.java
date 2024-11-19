package ca.bcit.comp2522.lab8bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CountryLab {

    /**
     * Main method to execute the program.
     * Reads a list of countries from a file, performs various operations on the list,
     * and writes results to a file in a directory.
     *
     * @throws IOException if there is an error accessing files
     */
    public static void main(final String[] args) throws IOException {

        final Path pathMatches;
        final Path pathCountries;


        pathMatches = Paths.get("matches");
        pathCountries = Paths.get("week8countries.txt");

        if (Files.notExists(pathMatches)) {
            Files.createDirectory(pathMatches);
        }

        if (Files.notExists(pathCountries)) {
            throw new IOException("File not found.");
        }

        try {
            final List<String> countries;
            final List<String> currentList;

            countries = Files.readAllLines(pathCountries);
            uniqueLetters(countries);
            countryNamesStartingWithA(countries, pathMatches);

        } catch (final IOException e) {
            e.printStackTrace();
        }



    }
    /*
     * Filters the countries so there's only countries that start with the letter A and outputs the result to the
     * .txt file in pathMatches with the header:
     * "Country names starting with A:"
     */
    private static List<String> countryNamesStartingWithA(final List<String> countries, final Path pathMatches) {
        final Stream<String> countriesStartingWithA;

        countriesStartingWithA = filteredCountryStream(countries)
                .filter(country -> startsWith(country, 'A'));

        return streamToListWithHeader(countriesStartingWithA, "Country names starting with 'A':");
    }

    /*
     * Sorts all countries and outputs the result to the .txt file in pathMatches with the header:
     * "Sorted countries:"
     */
    private static void sortCountries(final List<String> countries, final Path pathMatches) {
        final List<String> sortedCountries;
        final Stream<String> sortedStream;

        sortedStream = filteredCountryStream(countries)
                .sorted();
        sortedCountries = streamToListWithHeader(sortedStream, "Sorted countries:");

        outputResult(sortedCountries, pathMatches);
    }

    /*
     * Counts how many countries and outputs the result to the .txt file in pathMatches with the header:
     * "There are " + count + " countries."
     */
    private static void countCountries(final List<String> countries, final Path pathMatches) {
        final long count;
        count = filteredCountryStream(countries).count();

        outputResult("There are " + count + " countries.", pathMatches);
    }

    /*
     * Changes all countries to uppercase and outputs the result to the .txt file in pathMatches with the header:
     * "All countries in uppercase:"
     */
    private static void countriesInUppercase(final List<String> countries, final Path pathMatches) {
        final List<String> uppercaseCountriesList;
        final Stream<String> uppercaseCountriesStream;

        uppercaseCountriesStream = filteredCountryStream(countries)
                .map(String::toUpperCase);
        uppercaseCountriesList = streamToListWithHeader(uppercaseCountriesStream, "All countries in uppercase:");

        outputResult(uppercaseCountriesList, pathMatches);
    }

    /*
     * Checks if a country starts with the letter Z and outputs the result to the .txt file in pathMatches with the header:
     * "Are there any countries that start with Z?"
     */
    private static void isThereCountryWithZ(final List<String> countries, final Path pathMatches) {
        final boolean anyCountryWithZ;

        anyCountryWithZ = filteredCountryStream(countries)
                .anyMatch(country -> startsWith(country, 'Z'));

        outputResult("Are there any countries that start with Z? " + anyCountryWithZ, pathMatches);
    }

    /*
     * Returns a List with all countries that start with a specific letter with this header as first value:
     * "Country names starting with " + initial + ":"
     */
    private static List<String> countriesThatStartWith(final List<String> countries,
                                                       final char initial) {

        final Stream<String> countriesStartingWith;

        countriesStartingWith = filteredCountryStream(countries)
                .filter(country -> startsWith(country, initial));

        return streamToListWithHeader(countriesStartingWith, "Country names starting with '" + Character.toUpperCase(initial) + "':");
    }

    /* Returns true if a String str starts with the given initial.
    */
    private static boolean startsWith(final String str, final char initial) {

        final char firstChar;
        final char initialChar;
        final int FIRST_CHAR_IDX = 0;

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
    /*
     * Outputs the country, countriesList or value as a String to the .txt file in pathMatches.
     * Automatically cleans (if exists) or creates the file and writes to it.
     */
    private static void outputResult(final String countryString, final Path pathMatches) {
        final Path path;
        final String DEFAULT_OUTPUT = "data.txt";
        path = pathMatches.resolve(DEFAULT_OUTPUT);

        try {
            if(Files.exists(path)) {
                Files.delete(path);
            }

            Files.writeString(path, countryString, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * Outputs the countriesList as a List<String> to the .txt file in pathMatches.
     * Automatically cleans (if exists) or creates the file and writes to it.
     */
    private static void outputResult(final List<String> countriesList, final Path pathMatches) {
        final Path path;
        final String DEFAULT_OUTPUT = "data.txt";
        path = pathMatches.resolve(DEFAULT_OUTPUT);

        try {
            if(Files.exists(path)) {
                Files.delete(path);
            }

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
    /* Returns a list of countries' names that are shorter than 5*/
    private static void countriesShorterThan5Letters(final List<String> countries) {
        final List<String> countriesLessThanfive;

        final int SHORT_CHAR_LENGTH = 5;
        countriesLessThanfive = countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.length() > SHORT_CHAR_LENGTH)
                .toList();
        outputResult(countriesLessThanfive, Paths.get("matches"));

    }
    /* Returns a list of countries containing the word "United" */
    private static void countriesContainingUnited(final List<String> countries) {
        final List<String> countriesUnited;

        countriesUnited = countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank() && p.contains("United"))
                .toList();
        outputResult(countriesUnited, Paths.get("matches"));

    }
    /* Returns a list of unique chars of the first letters of each country*/
    private static void uniqueLetters(final List<String> countries) {
        final StringBuilder filteredCountries;

        filteredCountries = new StringBuilder();
        countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .distinct()
                .forEach(filteredCountries::append);

        outputResult(filteredCountries.toString(), Paths.get("matches"));

    }
    /* Returns a stream of the shortest country name */
    private static Stream<String> shortestCountryName(final List<String> countries) {
        return countries.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank())
                .min(Comparator.comparingInt(String::length))
                .stream();
    }
    /* Returns a stream of country count */
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
     */
    private static void LongCountries(final List<String> countries) {
        final List<String> countriesLong;
        final int LONG_COUNTRY_COUNT = 10;

        countriesLong = filteredCountryStream(countries)
                .filter(count -> count.length() >= LONG_COUNTRY_COUNT)
                .collect(Collectors.toList());
        outputResult(countriesLong, Paths.get("matches"));
    }
    /**
     *
     * Writes a list of countries whose names end with "land".
     *
     * @param countries a list of country names
     * @return a list of country names that end with "land" and have more than 10 letters
     */
    public static void writeCountriesEndingWithLand(final List<String> countries) {
        final List<String> countriesNamesEndWithLand;

        countriesNamesEndWithLand = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.endsWith("land"))
                .collect(Collectors.toList());

        outputResult(countriesNamesEndWithLand, Paths.get("matches"));
    }
    /**
     * Returns a list of countries sorted in reverse alphabetical order.
     *
     * @param countries a list of country names
     * @return a list of country names sorted in reverse alphabetical order
     */
    private static void sortedNamesDescending(final List<String> countries) {
        final List<String> sortedCountries;

        sortedCountries = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        outputResult(sortedCountries, Paths.get("matches"));
    }
    /**
     * Returns the longest country name from a list of countries.
     *
     * @param countries a list of country names
     * @return the longest country name
     */
    private static void longestCountryName(final List<String> countries) {
        final String longestCountry;

        longestCountry = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .max(Comparator.comparingInt(String::length))
                .orElse("No countries found");

        outputResult(longestCountry, Paths.get("matches"));
    }

    /**
     * Returns a list of countries whose names consist of more than one word.
     *
     * @param countries a list of country names
     */
    private static void countriesWithMoreThanOneWord(final List<String> countries) {
        final List<String> multiWordCountries;

        multiWordCountries = countries.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.isBlank())
                .filter(c -> c.trim().contains(" "))
                .collect(Collectors.toList());

        outputResult(multiWordCountries, Paths.get("matches"));
    }

}