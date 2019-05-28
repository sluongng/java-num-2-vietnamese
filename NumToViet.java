import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NumToViet {

  // Static
  private static final List<String> digitsName = Arrays.asList("không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy",
      "tám", "chín");

  private static final List<String> thousandsName = Arrays.asList("", "nghìn", "triệu", "tỷ", "nghìn tỷ", "triệu tỷ",
      "tỷ tỷ");

  // Algorithm section

  private static String decorateTriple(String triplet) {
    // TODO
    return triplet;
  }

  /**
   * turn triplet digits into vietnamese words
   * @param triplet a string of 3 digit integer
   * @param showZeroHundred whether to show Zero hundred
   * @return vietnamese string represent the input number
   */
  private static String readTriple(String triplet, boolean showZeroHundred) {
    List<Integer> digitList = stringToInt(triplet);

    int a = digitList.get(0);
    int b = digitList.get(1);
    int c = digitList.get(2);
    
    if (a == 0) {
      if (b == 0 && c == 0) {
        return "";
      }

      if (showZeroHundred) {
        return "không trăm " + readPair(b, c);
      }
      
      if (b == 0) {
        return digitsName.get(c);
      } else {
        return readPair(b, c);
      }

    }

    return digitsName.get(a) + " trăm " + readPair(b, c);
  }

  private static String readPair(int b, int c) {
    // TODO
    return "";
  }

  private static List<Integer> stringToInt(String triplet) {
     return triplet.chars()
      .map(NumToViet::charToInt)
      .boxed()
      .collect(Collectors.toList());
  }

  private static int charToInt(int c) {
    return c - '0';
  }

  public static String num2String(long num) {

    if (num == 0L) {
      return "không";
    }

    if (num < 0L) {
      return "âm " + num2String(-num);
    }

    String str = Long.valueOf(num).toString();

    // zero padding in front of string to prepare for splitting
    switch (str.length() % 3) {
    case 1:
      str = "00" + str;
      break;
    case 2:
      str = "0" + str;
      break;
    default:
      break;
    }

    // Split into chunks of 3 digits each
    List<String> groupOfThousand = Arrays.asList(str.split("(?<=\\G.{3})"));

    boolean showZeroHundred = doShowZeroHundred(groupOfThousand);

    groupOfThousand.stream()
      .map(triplet -> readTriple(triplet, showZeroHundred))
      .map(NumToViet::decorateTriple);

    thousandsName.get(1);

    return digitsName.get(1);
  }

  /**
   * determine whether to show zero-hundred text
   * @param groupOfThousand number represented in group of 3 digits of each 1000^n
   * @return a boolean
   */
  private static boolean doShowZeroHundred(List<String> groupOfThousand) {
    int count = 0;
    int i = groupOfThousand.size() - 1;
    while (i > 1 && !groupOfThousand.get(i).equals("000")) {
      count++;
    }

    return count < groupOfThousand.size() - 1;
  }


  public static void main(String[] args) {

    // Test section
    Map<Long, String> testCase = new HashMap<>();
    testCase.put(0L, "không");
    testCase.put(1L, "một");
    testCase.put(-1L, "âm một");
    testCase.put(10L, "mười");
    testCase.put(100L, "một trăm");
    testCase.put(1_000L, "một nghìn");

    // Execute tests
    Boolean result = testCase.entrySet().stream().map(entry -> {
      String vietString = num2String(entry.getKey());

      boolean comparision = entry.getValue().equals(vietString);
      if (!comparision) {
        System.out.println(String.format("[Failed] result: |%s| expected |%s|", vietString, entry.getValue()));
      } else {
        System.out.println(String.format("[Passed] result: |%d| - |%s|", entry.getKey(), entry.getValue()));
      }

      return comparision;
    }).reduce(Boolean.TRUE, (a, b) -> a && b);

    // Final report
    if (result) {
      System.out.println("Finished testing!");
    } else {
      System.out.println("Some test(s) failed!");
    }
  }
}
