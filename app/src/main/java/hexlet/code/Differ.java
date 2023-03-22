package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Differ {
    private static List<String> extensionsList = new ArrayList<>(2);
    private static final String DEFAULT_FORMAT = "stylish";

    //Метод ниже создан для тестирования, чтобы очищать поле extensionsList перед каждым запуском теста
    //Создан потому, что в этом проекте используются static классы и методы
    //Потому поле чистится c помощью @BeforeEach
    //Но для инкапсуляции поле сделано private, и чистится через public метод ниже:
    public static void cleanExtensionsList() {
        extensionsList.clear();
    }

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, DEFAULT_FORMAT);
    }

    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        List<Map<String, Object>> parsedMapsList = new ArrayList<>();

        Map<String, Object> firstFileParsedMap = getData(firstFilePath);
        Map<String, Object> secondFileParsedMap = getData(secondFilePath);

        Parser.checkIsEmptyBothFiles(extensionsList, firstFileParsedMap, secondFileParsedMap);

        parsedMapsList.add(firstFileParsedMap);
        parsedMapsList.add(secondFileParsedMap);

        Map<String, String> keyDifferTypes = Tree.findDiff(parsedMapsList);
        return Formatter.format(keyDifferTypes, parsedMapsList, format);
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path fileAbsolutePath = getAbsolutePath(filePath);
        isFileExist(fileAbsolutePath);
        String fileAbsolutePathString = pathToString(fileAbsolutePath);
        String fileExtension = findFileExtension(filePath);
        extensionsList.add(fileExtension);
        return Parser.parseToMap(fileExtension, fileAbsolutePathString);
    }

    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static void isFileExist(Path absoluteFilePath) throws IOException {
        if (!Files.exists(absoluteFilePath)) {
            throw new IOException("'" + absoluteFilePath + "' does not exist.\nCheck it!");
        }
    }

    private static String pathToString(Path absoluteFilePath) throws IOException {
        return Files.readString(absoluteFilePath);
    }

    private static String findFileExtension(String filePath) throws Exception {
        String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();

        if (fileExtension.equals(Parser.FILE_EXTENSIONS[2])) {
            return Parser.FILE_EXTENSIONS[1];
        }
        Parser.checkFileExtension(fileExtension);
        return fileExtension;
    }
}
