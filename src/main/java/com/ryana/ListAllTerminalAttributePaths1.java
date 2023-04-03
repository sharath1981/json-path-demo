package com.ryana;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListAllTerminalAttributePaths1 {
  public static void main(String[] args) throws IOException {
    final var jsonStr = new String(Files.readAllBytes(Paths.get("sample.json")));
    final var paths = listTerminalAttributePaths(jsonStr);
    paths.forEach(System.out::println);
  }

  private static List<String> listTerminalAttributePaths(String jsonString) {
    final var jsonObject = new JSONObject(jsonString);
    final var paths = new ArrayList<String>();
    traverseJson(jsonObject, "$", paths);
    return paths;
  }

  private static void traverseJson(Object jsonValue, String path, List<String> paths) {
    if (jsonValue instanceof JSONObject jsonObject) {
      for (final var key : jsonObject.keySet()) {
        final var currentPath = path + "." + key;
        traverseJson(jsonObject.get(key), currentPath, paths);
      }
    } else if (jsonValue instanceof JSONArray jsonArray) {
      for (int i = 0; i < jsonArray.length(); i++) {
        final var currentPath = path + "[" + i + "]";
        traverseJson(jsonArray.get(i), currentPath, paths);
      }
    } else {
      paths.add(path + " : " + jsonValue);
    }
  }
}
