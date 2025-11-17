package com.packt.fooballobs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {
    private String fileName;
    private List<String> name;
    private String folder;

    public FileLoader(String folder) {
        this.folder = folder;
    }

    private void laodFile() throws Exception {
        this.fileName = fileName;
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(fileName);
        teams = mapper.readValue(file, new TypeReference<String>() {
        });
    }

    private void loadFile() throws IOException {
        Files.list(Paths.get(folder))
                .filter(Files::isRegularFile)
                .findFirst()
                .ifPresent(file -> {
                    try {
                        loadFile(file.toString());
                    } catch (Exception e)  {
                        e.printStackTrace();
                    }
                });
    }
}
