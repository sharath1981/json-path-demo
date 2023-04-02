package com.ryana;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListAllTerminalAttributePaths2 {
  public static void main(String[] args) throws IOException {
    final var paths = listTerminalAttributePaths();
    paths.entrySet().forEach(System.out::println);
  }

  private static Map<String, String> listTerminalAttributePaths() throws IOException {
    final var filePath = Paths.get("sample.json");
    final var jsonStr = new String(Files.readAllBytes(filePath));
    final var jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
    final var jsonPaths = new LinkedHashMap<String, String>();
    traverseJson(jsonObject, new StringBuffer("$"), jsonPaths);
    return jsonPaths;
  }

  private static void traverseJson(Object jsonValue, StringBuffer path, Map<String, String> jsonPaths) {
    if (jsonValue instanceof JsonObject jsonObject) {
      jsonObject.entrySet().forEach(entry -> {
        traverseJson(entry.getValue(), path.append(".").append(entry.getKey()), jsonPaths);
        path.setLength(path.length() - entry.getKey().length() - 1);
      });
    } else if (jsonValue instanceof JsonArray jsonArray) {
      IntStream.range(0, jsonArray.size())
          .forEach(i -> {
            traverseJson(jsonArray.get(i), path.append("[").append(i).append("]"), jsonPaths);
            path.setLength(path.length() - 3);
          });
    } else {
      jsonPaths.put(String.valueOf(path), String.valueOf(jsonValue));
    }
  }
}
