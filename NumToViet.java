import java.util.HashMap;

import java.util.Map;

public class NumToViet {

  // Algorithm section

  public static String num2String(long num) {

    if (num == 0L) {
      return "không";
    }

    if (num < 0L) {
      return "âm " + num2String(-num);
    }

    return "";
  }

  // Test section

  private static final Map<Long, String> testCase = new HashMap<Long, String>();
  static {
    testCase.put(0L, "không");
    testCase.put(1L, "một");
    testCase.put(-1L, "âm một");
    testCase.put(10L, "mười");
    testCase.put(100L, "một trăm");
  }

  public static void main(String[] args) {
    Boolean result = testCase.entrySet().stream().map(entry -> {
      String vietString = num2String(entry.getKey());

      boolean comparision = entry.getValue().equals(vietString);
      if (!comparision) {
        System.out.println(String.format("Got result: |%s| expected |%s|", vietString, entry.getValue()));
      }

      return comparision;
    }).reduce(Boolean.TRUE, (a, b) -> a && b);

    if (result) {
      System.out.println("Finished testing!");
    } else {
      System.out.println("Some test(s) failed!");
    }
  }
}
