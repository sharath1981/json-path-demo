package com.ryana;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListAllTerminalAttributePaths3 {
  public static void main(String[] args) throws IOException {
    final var jsonStr = new String(Files.readAllBytes(Paths.get("sample.json")));
    final var paths = listTerminalAttributePaths(jsonStr);
    paths.forEach(System.out::println);
  }

  private static List<String> listTerminalAttributePaths(String jsonString) {
    final var jsonObject = new JSONObject(jsonString);
    final var paths = new ArrayList<String>();
    traverseJson(jsonObject, paths);
    return paths;
  }

  private static void traverseJson(Object jsonValue, List<String> paths) {
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(jsonValue, "$"));

    while (!stack.empty()) {
      final var currentPair = stack.pop();
      final var currentJsonValue = currentPair.key();
      final var currentPath = currentPair.value();

      if (currentJsonValue instanceof JSONObject jsonObject) {
        for (final var key : jsonObject.keySet()) {
          final var newPath = currentPath + "." + key;
          stack.push(new Pair(jsonObject.get(key), newPath));
        }
      } else if (currentJsonValue instanceof JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
          final var newPath = currentPath + "[" + i + "]";
          stack.push(new Pair(jsonArray.get(i), newPath));
        }
      } else {
        paths.add(currentPath + " : " + currentJsonValue);
      }
    }
  }

  private static final record Pair(Object key, String value) {
  }

}
