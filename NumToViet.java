import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class NumToViet {

  private static final List<String> digitsName = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("không");
      add("một");
      add("hai");
      add("ba");
      add("bốn");
      add("năm");
      add("sáu");
      add("bảy");
      add("tám");
      add("chín");
    }
  };

  private static final List<String> thousandsName = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("");
      add("nghìn");
      add("triệu");
      add("tỷ");
      add("nghìn tỷ");
      add("triệu tỷ");
      add("tỷ tỷ");
    }
  };

  // Algorithm section

  public static String num2String(long num) {

    if (num == 0L) {
      return "không";
    }

    if (num < 0L) {
      return "âm " + num2String(-num);
    }

    thousandsName.get(1);

    return digitsName.get(1);
  }

  // Test section

  private static final Map<Long, String> testCase = new HashMap<Long, String>() {
    private static final long serialVersionUID = 9999L;

    {
      put(0L, "không");
      put(1L, "một");
      put(-1L, "âm một");
      put(10L, "mười");
      put(100L, "một trăm");

      put(1_000L, "một nghìn");
    }
  };

  public static void main(String[] args) {
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

    if (result) {
      System.out.println("Finished testing!");
    } else {
      System.out.println("Some test(s) failed!");
    }
  }
}
