package io.github.ninobomba.commons.properties;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public final class LocalPropertiesLoader {

    private static final String DEFAULT_PROPERTIES_PATH = "src/main/resources/custom/";

    private static final Properties properties = new Properties();

    private static LocalPropertiesLoader instance;

    private static String finalPath;

    private LocalPropertiesLoader() {
    }

    private LocalPropertiesLoader(String path) {
        finalPath = StringUtils.isEmpty( path ) ? DEFAULT_PROPERTIES_PATH : path;
        log.debug("LocalPropertiesLoader():: checking resources from path: {}", finalPath);
        load();
    }

    public static LocalPropertiesLoader getInstance() {
        return getInstance(DEFAULT_PROPERTIES_PATH);
    }

    public static LocalPropertiesLoader getInstance(String requestedPath) {
        if (Objects.isNull(instance)) {
            instance = new LocalPropertiesLoader(requestedPath);
        }
        return instance;
    }

    public String getProperty(String name, String defaultValue) {
        var value = getProperty(name);
        return Objects.isNull( value ) ? defaultValue : value;
    }

    public String getProperty(String name) {
        var property = properties.get(name);
        return Objects.nonNull( property ) ? property.toString() : null;
    }

    @SneakyThrows
    private void load() {
        log.trace("LocalPropertiesLoader::load() >: start");

        var propertiesFiles = listPropertiesFile();
        log.info("LocalPropertiesLoader::load() _: properties files: {}", propertiesFiles);

        for (String path : propertiesFiles) {
            File file = ResourceUtils.getFile(path);
            try( var in = new FileInputStream(file)) {
                properties.load(in);
            }
        }

        print();

        log.trace("LocalPropertiesLoader::load() <: complete");
    }

    private void print() {
        log.trace("LocalPropertiesLoader::print() >: start");

        properties
                .forEach((k, v) -> {
                    var key = String.valueOf(k);
                    log.debug("LocalPropertiesLoader::print() _: kv: {}:{}",
                            key,
                            StringUtils.containsAnyIgnoreCase(key, "token", "secret", "password", "key") ? "******" : v
                    );
                });

        log.trace("LocalPropertiesLoader::print() <: complete");
    }

    private static Set<String> listPropertiesFile() throws IOException {
        log.trace("LocalPropertiesLoader::listPropertiesFile() >: start");

        var paths = Paths.get(finalPath);

        log.debug("LocalPropertiesLoader::listPropertiesFile() _: paths: {}", paths);

        Set<String> response = new HashSet<>();

        var streamPath = Files.list(paths);
        streamPath.forEach(e -> {
            var item = finalPath.concat(e.getFileName().toString());
            log.debug("LocalPropertiesLoader::listPropertiesFile() _: evaluating: {}", item);
            if (!Files.isDirectory(e) && "properties".equals(FilenameUtils.getExtension(String.valueOf(e)))) {
                log.debug("LocalPropertiesLoader::listPropertiesFile() _: adding: {}", item);
                response.add(item);
            }
        });
        streamPath.close();

        if (!response.isEmpty())
            log.debug("LocalPropertiesLoader::listPropertiesFile() _: files: {}", response);

        log.debug("LocalPropertiesLoader::listPropertiesFile() _: response: {}", response);

        log.trace("LocalPropertiesLoader::listPropertiesFile() <: complete");

        return response;
    }

}
