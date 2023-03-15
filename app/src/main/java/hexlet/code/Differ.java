package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Differ {
    private static final String DEFAULT_FORMAT = "stylish";

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, DEFAULT_FORMAT);
    }

    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        List<Map<String, Object>> parsedMapsList = getData(firstFilePath, secondFilePath);
        Map<String, String> keyDifferTypes = Tree.findDiff(parsedMapsList);
        return Formatter.format(keyDifferTypes, parsedMapsList, format);
    }

    private static List<Map<String, Object>> getData(String firstFilePath, String secondFilePath) throws Exception {
        List<Map<String, Object>> parsedMapsList = new ArrayList<>();

        String firstFileAbsolutePath = checkIsFileExistThenToAbsolutePath(firstFilePath);
        String secondFileAbsolutePath = checkIsFileExistThenToAbsolutePath(secondFilePath);

        String firstFileExtension = findFileExtension(firstFilePath);
        String secondFileExtension = findFileExtension(secondFilePath);

        Parser.checkFilesExtension(firstFileExtension, secondFileExtension);
        Map<String, Object> firstFileParsedMap = Parser.parseToMap(firstFileExtension, firstFileAbsolutePath);
        Map<String, Object> secondFileParsedMap = Parser.parseToMap(firstFileExtension, secondFileAbsolutePath);
        Parser.checkIsEmptyFiles(firstFileExtension, firstFileParsedMap, secondFileParsedMap);

        parsedMapsList.add(firstFileParsedMap);
        parsedMapsList.add(secondFileParsedMap);
        return parsedMapsList;
    }

    private static String checkIsFileExistThenToAbsolutePath(String filePath) throws IOException {
        Path absoluteFilePath = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(absoluteFilePath)) {
            throw new IOException("'" + absoluteFilePath + "' does not exist.\nCheck it!");
        }
        return Files.readString(absoluteFilePath);
    }

    private static String findFileExtension(String filePath) {
        String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();

        if (fileExtension.equals(Parser.FILE_EXTENSIONS[2])) {
            return Parser.FILE_EXTENSIONS[1];
        }
        return fileExtension;
    }
}
