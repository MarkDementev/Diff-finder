### CI status:
[![Actions Status](https://github.com/MarkDementev/java-project-71/workflows/Java%20CI/badge.svg)](https://github.com/MarkDementev/java-project-71/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/41de654d3f5b3b68cee3/maintainability)](https://codeclimate.com/github/MarkDementev/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/41de654d3f5b3b68cee3/test_coverage)](https://codeclimate.com/github/MarkDementev/java-project-71/test_coverage)

### Hexlet tests and linter status:
[![Actions Status](https://github.com/MarkDementev/java-project-61/workflows/hexlet-check/badge.svg)](https://github.com/MarkDementev/java-project-61/actions)

# Overview

Program for finding differences between data in two files.
For input files, popular formats are supported - json and yaml.
The output of differences is also possible in different versions - plain, stylish and json.

## Output by plain:
```sh
./app --format plain path/to/file.yml another/path/file.json
Property 'follow' was added with value: false
Property 'baz' was updated. From 'bas' to 'bars'
Property 'group2' was removed
```

## Output by stylish:
```sh
./app filepath1.json filepath2.json
{
  + follow: false
  + numbers: [1, 2, 3]
    setting1: Value 1
  - setting2: 200
  - setting3: true
  + setting3: {key=value}
  + setting4: blah blah
}
```

## Programme work session demo:
https://asciinema.org/a/GfJa1sRyFTL9HNHuaS8aXiyZH
