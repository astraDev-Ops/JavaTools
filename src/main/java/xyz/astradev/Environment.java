package xyz.astradev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This class is for the retrieval of the .env file that should be located within the project's root dir.
 * If the file is not found an error will be generated and the application will exit. It should be assumed that
 * the environment values are required for the application to safely continue execution and the omission of data will lead
 * to some form of instability.
 */
public class Environment {
    private static final HashMap<String, String> env = new HashMap<>();
    public Environment() throws IOException {
        if (env.isEmpty()){
            Path path = Paths.get(".env").toAbsolutePath();
            List<String> lines;
            lines = Files.readAllLines(path, UTF_8);

            if (lines.isEmpty()){
                return;
            }
            for (String line : lines){
                if (line.startsWith("#") || line.startsWith("/") || line.isBlank()){ //filter comments and empty lines
                    continue;
                }
                String[] splitter = line.split("=");
                if (splitter.length != 2){
                    continue;
                }
                env.put(splitter[0], splitter[1]);
            }
        }
    }

    /**
     * A not null safe implementation to get a key from
     * a .env file that should be located within the project root dir.
     *
     * @param key the key to retrieve
     * @return the value of the key or a blank string
     */
    public String get(String key){
        return env.get(key);
    }

    /**
     * A null safe implementation of the get feature
     *
     * @param key the key to retrieve
     * @return the value of the key or a blank string
     */
    public String getNotNull(String key){
        return env.getOrDefault(key, "");
    }
}