package hexlet.code;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Parser {
    public static final String[] FILE_EXTENSIONS = {"json", "yml", "yaml"};
    public static final String UNKNOWN_EXTENSION_ERROR = "There is unknown filename extension.\nCheck input files!";
    public static final String BOTH_FILES_EMPTY = "Both files are empty!";
    private static final String NO_FILE_EXTENSION_WARNING = "There is no file extension!!!";
    private static final String[] EMPTY_STATUSES = {"Empty", "Not empty"};

    public static String checkFileExtension(String fileExtension) throws Exception {
        String checkingFileExtension = fileExtension;

        if (checkingFileExtension.equals(FILE_EXTENSIONS[2])) {
            checkingFileExtension = FILE_EXTENSIONS[1];
        }

        if (!checkingFileExtension.equals(FILE_EXTENSIONS[0]) && !checkingFileExtension.equals(FILE_EXTENSIONS[1])) {
            throw new Exception(UNKNOWN_EXTENSION_ERROR);
        }
        return checkingFileExtension;
    }

    public static Map<String, Object> parseToMap(String filesExtension, String fileData)
            throws JsonProcessingException {
        return switch (filesExtension) {
            case "json" -> parseFromJSON(fileData);
            case "yml" -> parseFromYAML(fileData);
            default -> throw new RuntimeException(NO_FILE_EXTENSION_WARNING);
        };
    }

    public static void checkIsEmptyBothFiles(Map<String, Object> firstFileParsedMap,
                                             Map<String, Object> secondFileParsedMap) throws Exception {
        String[] checkEmptyArray = {EMPTY_STATUSES[1], EMPTY_STATUSES[1]};

        if (firstFileParsedMap == null || firstFileParsedMap.size() == 0) {
            checkEmptyArray[0] = EMPTY_STATUSES[0];
        }

        if (secondFileParsedMap == null || secondFileParsedMap.size() == 0) {
            checkEmptyArray[1] = EMPTY_STATUSES[0];
        }

        if (checkEmptyArray[0].equals(EMPTY_STATUSES[0]) && checkEmptyArray[1].equals(EMPTY_STATUSES[0])) {
            throw new Exception(BOTH_FILES_EMPTY);
        }
    }

    private static Map<String, Object> parseFromJSON(String fileData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(fileData, new TypeReference<>() { });
    }

    private static Map<String, Object> parseFromYAML(String fileData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(fileData, new TypeReference<>() { });
    }
}
