package hexlet.code;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 0.1",
        description = "Compares two configuration files and shows a difference.")

public class App implements Callable<Integer> {
    @Option(names = {"-f", "--format"}, paramLabel = "format",
            description = "output format [default: stylish]", defaultValue = "stylish")
    private String format;

    @Parameters(paramLabel = "filepath1", index = "0", description = "path to first file")
    private String firstFilePath;

    @Parameters(paramLabel = "filepath2", index = "1", description = "path to second file")
    private String secondFilePath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Differ.generate(firstFilePath, secondFilePath, format));
        return 0;
    }
}
