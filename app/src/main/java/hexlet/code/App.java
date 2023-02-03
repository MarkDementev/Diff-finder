package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

//Темур! Стоит ли отправить текст, выводимый @Command, @Option, @Parameters
//в String константы класса? Это ведь элементы фреймворка picocli, а не исходная Java
//я предположил, что не нужно. Поправь меня, если я не прав, и наличие фреймворков не отменяет правило
@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 0.1",
        description = "Compares two configuration files and shows a difference.")

public class App implements Callable<Integer> {
    @Option(names = {"-f", "--format"}, paramLabel = "format",
            description = "output format [default: stylish]", defaultValue = "stylish")
    private String formatName;

    @Parameters(paramLabel = "filePath1", index = "0", description = "path to first file")
    private String firstFilePath;

    @Parameters(paramLabel = "filePath2", index = "1", description ="path to second file")
    private String secondFilePath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Differ.generate(firstFilePath, secondFilePath));
        return 0;
    }
}
