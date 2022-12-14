package controller;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.porterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class InvertedIndexFileReader {

    public void readFilesInFolder(final File folder, HashMap<String, ArrayList<String>> invertedIndexMap) throws FileNotFoundException {
        if (folder.exists() && folder.isDirectory()) {
            for (final File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    readFilesInFolder(file, invertedIndexMap);
                } else {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()) {
                        String word = stem(scanner.next().toLowerCase());
                        if (invertedIndexMap.containsKey(word)) {
                            invertedIndexMap.get(word).add(file.getName());
                        } else {
                            invertedIndexMap.put(word, new ArrayList<>(Arrays.asList(file.getName())));
                        }
                    }
                }
            }
        } else {
            throw new FileNotFoundException();
        }
    }

    private String stem(String word) {
        word = word.replace(".", "");
        word = word.replace(",", "");
        word = word.replace("!", "");
        word = word.replace("?", "");
        SnowballStemmer stemmer = new porterStemmer();
        stemmer.setCurrent(word);
        stemmer.stem();
        word = stemmer.getCurrent();
        return word;
    }
}
