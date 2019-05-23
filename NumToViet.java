import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NumToViet {
  // Static
  private static final List<String> digitsName = Arrays.asList(
    "không",
    "một",
    "hai",
    "ba",
    "bốn",
    "năm",
    "sáu",
    "bảy",
    "tám",
    "chín"
  );

  private static final List<String> thousandsName = Arrays.asList(
    "",
    "nghìn",
    "triệu",
    "tỷ",
    "nghìn tỷ",
    "triệu tỷ",
    "tỷ tỷ"
  );

  // Algorithm section

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

    // TODO: remove Debug
    System.out.println("Group size is" + groupOfThousand.size());

    thousandsName.get(1);

    return digitsName.get(1);
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
