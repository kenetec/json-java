package com.github.kenetec.json;

 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Arrays;

 public class JsonTest {
     public static void main(String[] args) {
         JsonReader reader;
         File jsonSample = new File("./src/com/github/kenetec/json/sample.json");
         File jsonDump = new File("./src/com/github/kenetec/json/dump.json");

         try {
             JsonObject root = JsonReader.read(jsonSample);
             System.out.println(root.prettyDump());

             JsonObject target = (JsonObject) root.getFromPath("quiz.maths.q2");
             System.out.println(target);

             ArrayList<Number> options = target.getNumberList("options");

             options.forEach((o) -> System.out.println(o.intValue()));

             int answer = target.getNumber("answer").intValue();
             System.out.println(answer * 5);

             target.putFromPath("answer", answer * 20);

             ArrayList<Integer> otherOptions = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9));
             target.put("other_options", otherOptions);

             FileWriter writer = new FileWriter(jsonDump);
             writer.write(root.prettyDump());
             writer.close();
         } catch (EntryNotFoundException | IOException e) {
             e.printStackTrace();
         }
     }
 }
