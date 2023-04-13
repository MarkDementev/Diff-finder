package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {
    private static final String DEFAULT_FORMAT = "stylish";

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, DEFAULT_FORMAT);
    }

    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        Map<String, Object> firstFileParsedMap = getData(firstFilePath);
        Map<String, Object> secondFileParsedMap = getData(secondFilePath);

        Parser.checkIsEmptyBothFiles(firstFileParsedMap, secondFileParsedMap);
        Map<String, Object[]> diffMap = Tree.findDiff(firstFileParsedMap, secondFileParsedMap);
        return Formatter.format(diffMap, format);
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path fileAbsolutePath = getAbsolutePath(filePath);
        checkFileExistance(fileAbsolutePath);
        String fileAbsolutePathString = pathToString(fileAbsolutePath);
        String fileExtension = findFileExtension(filePath);
        return Parser.parseToMap(fileExtension, fileAbsolutePathString);
    }

    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    //Проработка 3-го комментария. Метод переименовал.
    private static void checkFileExistance(Path absoluteFilePath) throws IOException {
        if (!Files.exists(absoluteFilePath)) {
            throw new IOException("'" + absoluteFilePath + "' does not exist.\nCheck it!");
        }
    }

    private static String pathToString(Path absoluteFilePath) throws IOException {
        return Files.readString(absoluteFilePath);
    }

    private static String findFileExtension(String filePath) throws Exception {
        String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
        return Parser.checkFileExtension(fileExtension);
    }
}
