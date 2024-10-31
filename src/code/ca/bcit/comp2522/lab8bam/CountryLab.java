package ca.bcit.comp2522.lab8bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        } catch (final IOException e){
            e.printStackTrace();
        }

    }
}