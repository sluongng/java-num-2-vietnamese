import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

  /**
   * turn triplet digits into vietnamese words
   * 
   * @param triplet         a string of 3 digit integer
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
    String temp;

    switch (b) {
    case 0:
      return c == 0 ? "" : "lẻ " + digitsName.get(c);
    case 1:
      switch (c) {
      case 0:
        temp = " ";
        break;
      case 5:
        temp = "lăm ";
        break;
      default:
        temp = digitsName.get(c);
        break;
      }

      return "mười " + temp;
    default:
      switch (c) {
      case 0:
        temp = "";
        break;
      case 1:
        temp = "mốt ";
        break;
      case 4:
        temp = "tư ";
        break;
      case 5:
        temp = "lăm ";
        break;
      default:
        temp = digitsName.get(c);
        break;
      }

      return digitsName.get(b) + " mươi " + temp;
    }
  }

  private static List<Integer> stringToInt(String triplet) {
    return triplet.chars().map(NumToViet::charToInt).boxed().collect(Collectors.toList());
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

    AtomicInteger index = new AtomicInteger();
    String result = groupOfThousand.stream()
      .map(triplet -> readTriple(triplet, showZeroHundred && index.get() > 0))
      .map(vnString -> vnString.trim().isEmpty() 
        ? "" 
        : vnString + " " + thousandsName.get(groupOfThousand.size() - 1 - index.get())
      )
      .peek(s -> index.getAndIncrement())
      .collect(Collectors.joining(" "))
      .replaceAll("\\s+", " ")
      .trim();

    return result;
  }

  /**
   * determine whether to show zero-hundred text
   *
   * Explain: count the amount of consecutive "000" at the end of the number and
   * compare it with number length
   *
   * @param groupOfThousand number represented in group of 3 digits of each 1000^n
   * @return a boolean
   */
  private static boolean doShowZeroHundred(List<String> groupOfThousand) {
    int count = 0;
    int i = groupOfThousand.size() - 1;
    while (i >= 0 && groupOfThousand.get(i).equals("000")) {
      count++;
      i--;
    }

    return count < groupOfThousand.size() - 1;
  }

  public static void main(String[] args) {
    // Test section
    Map<Long, String> testCase = new LinkedHashMap<>();
    testCase.put(0L, "không");
    testCase.put(1L, "một");
    testCase.put(-1L, "âm một");
    testCase.put(10L, "mười");
    testCase.put(100L, "một trăm");
    testCase.put(1_000L, "một nghìn");
    testCase.put(-55L,"Âm năm mươi lăm");
    testCase.put(-1_055L,"Âm một nghìn không trăm năm mươi lăm");
    testCase.put(101_002_101_000_000_000L,"Một trăm lẻ một triệu tỷ không trăm lẻ hai nghìn tỷ một trăm lẻ một tỷ");
    testCase.put(100_000_000_000L,"Một trăm tỷ");
    testCase.put(1_000_000_000_000L,"Một nghìn tỷ");
    testCase.put(1_000_000_000_000_000L,"Một triệu tỷ");
    testCase.put(1_000_000_000_000_000_000L,"Một tỷ tỷ");
    testCase.put(1_000_000_234_000_000_000L,"Một tỷ tỷ hai trăm ba mươi tư tỷ");
    testCase.put(1_000_000_000_222_000_000L,"Một tỷ tỷ hai trăm hai mươi hai triệu");
    testCase.put(1_009L,"Một nghìn không trăm lẻ chín");
    testCase.put(9L,"Chín");
    testCase.put(90L,"Chín mươi");
    testCase.put(900L,"Chín trăm");
    testCase.put(1_000_000L,"Một triệu");
    testCase.put(1_090_000L,"Một triệu không trăm chín mươi nghìn");
    testCase.put(1_090_010L,"Một triệu không trăm chín mươi nghìn không trăm mười");
    testCase.put(1_000_010L,"Một triệu không trăm mười");
    testCase.put(10L,"Mười");
    testCase.put(100L,"Một trăm");
    testCase.put(55L,"Năm mươi lăm");
    testCase.put(20_000L,"Hai mươi nghìn");
    testCase.put(100_000L,"Một trăm nghìn");
    testCase.put(110_000L,"Một trăm mười nghìn");
    testCase.put(120_000L,"Một trăm hai mươi nghìn");
    testCase.put(130_000L,"Một trăm ba mươi nghìn");
    testCase.put(140_000L,"Một trăm bốn mươi nghìn");
    testCase.put(150_000L,"Một trăm năm mươi nghìn");
    testCase.put(160_000L,"Một trăm sáu mươi nghìn");
    testCase.put(170_000L,"Một trăm bảy mươi nghìn");
    testCase.put(180_000L,"Một trăm tám mươi nghìn");
    testCase.put(190_000L,"Một trăm chín mươi nghìn");
    testCase.put(200_000L,"Hai trăm nghìn");
    testCase.put(210_000L,"Hai trăm mười nghìn");
    testCase.put(220_000L,"Hai trăm hai mươi nghìn");
    testCase.put(230_000L,"Hai trăm ba mươi nghìn");
    testCase.put(240_000L,"Hai trăm bốn mươi nghìn");
    testCase.put(250_000L,"Hai trăm năm mươi nghìn");
    testCase.put(260_000L,"Hai trăm sáu mươi nghìn");
    testCase.put(270_000L,"Hai trăm bảy mươi nghìn");
    testCase.put(280_000L,"Hai trăm tám mươi nghìn");
    testCase.put(290_000L,"Hai trăm chín mươi nghìn");
    testCase.put(300_000L,"Ba trăm nghìn");
    testCase.put(310_000L,"Ba trăm mười nghìn");
    testCase.put(320_000L,"Ba trăm hai mươi nghìn");
    testCase.put(330_000L,"Ba trăm ba mươi nghìn");
    testCase.put(340_000L,"Ba trăm bốn mươi nghìn");
    testCase.put(350_000L,"Ba trăm năm mươi nghìn");
    testCase.put(360_000L,"Ba trăm sáu mươi nghìn");
    testCase.put(370_000L,"Ba trăm bảy mươi nghìn");
    testCase.put(380_000L,"Ba trăm tám mươi nghìn");
    testCase.put(390_000L,"Ba trăm chín mươi nghìn");
    testCase.put(400_000L,"Bốn trăm nghìn");
    testCase.put(410_000L,"Bốn trăm mười nghìn");
    testCase.put(420_000L,"Bốn trăm hai mươi nghìn");
    testCase.put(430_000L,"Bốn trăm ba mươi nghìn");
    testCase.put(440_000L,"Bốn trăm bốn mươi nghìn");
    testCase.put(450_000L,"Bốn trăm năm mươi nghìn");
    testCase.put(460_000L,"Bốn trăm sáu mươi nghìn");
    testCase.put(470_000L,"Bốn trăm bảy mươi nghìn");
    testCase.put(480_000L,"Bốn trăm tám mươi nghìn");
    testCase.put(490_000L,"Bốn trăm chín mươi nghìn");
    testCase.put(500_000L,"Năm trăm nghìn");
    testCase.put(510_000L,"Năm trăm mười nghìn");
    testCase.put(520_000L,"Năm trăm hai mươi nghìn");
    testCase.put(530_000L,"Năm trăm ba mươi nghìn");
    testCase.put(540_000L,"Năm trăm bốn mươi nghìn");
    testCase.put(550_000L,"Năm trăm năm mươi nghìn");
    testCase.put(560_000L,"Năm trăm sáu mươi nghìn");
    testCase.put(570_000L,"Năm trăm bảy mươi nghìn");
    testCase.put(580_000L,"Năm trăm tám mươi nghìn");
    testCase.put(590_000L,"Năm trăm chín mươi nghìn");
    testCase.put(600_000L,"Sáu trăm nghìn");
    testCase.put(610_000L,"Sáu trăm mười nghìn");
    testCase.put(620_000L,"Sáu trăm hai mươi nghìn");
    testCase.put(630_000L,"Sáu trăm ba mươi nghìn");
    testCase.put(640_000L,"Sáu trăm bốn mươi nghìn");
    testCase.put(650_000L,"Sáu trăm năm mươi nghìn");
    testCase.put(660_000L,"Sáu trăm sáu mươi nghìn");
    testCase.put(670_000L,"Sáu trăm bảy mươi nghìn");
    testCase.put(680_000L,"Sáu trăm tám mươi nghìn");
    testCase.put(690_000L,"Sáu trăm chín mươi nghìn");
    testCase.put(700_000L,"Bảy trăm nghìn");
    testCase.put(710_000L,"Bảy trăm mười nghìn");
    testCase.put(720_000L,"Bảy trăm hai mươi nghìn");
    testCase.put(730_000L,"Bảy trăm ba mươi nghìn");
    testCase.put(740_000L,"Bảy trăm bốn mươi nghìn");
    testCase.put(750_000L,"Bảy trăm năm mươi nghìn");
    testCase.put(760_000L,"Bảy trăm sáu mươi nghìn");
    testCase.put(770_000L,"Bảy trăm bảy mươi nghìn");
    testCase.put(780_000L,"Bảy trăm tám mươi nghìn");
    testCase.put(790_000L,"Bảy trăm chín mươi nghìn");
    testCase.put(800_000L,"Tám trăm nghìn");
    testCase.put(810_000L,"Tám trăm mười nghìn");
    testCase.put(820_000L,"Tám trăm hai mươi nghìn");
    testCase.put(830_000L,"Tám trăm ba mươi nghìn");
    testCase.put(840_000L,"Tám trăm bốn mươi nghìn");
    testCase.put(850_000L,"Tám trăm năm mươi nghìn");
    testCase.put(860_000L,"Tám trăm sáu mươi nghìn");
    testCase.put(870_000L,"Tám trăm bảy mươi nghìn");
    testCase.put(880_000L,"Tám trăm tám mươi nghìn");
    testCase.put(890_000L,"Tám trăm chín mươi nghìn");
    testCase.put(900_000L,"Chín trăm nghìn");
    testCase.put(910_000L,"Chín trăm mười nghìn");
    testCase.put(920_000L,"Chín trăm hai mươi nghìn");
    testCase.put(930_000L,"Chín trăm ba mươi nghìn");
    testCase.put(940_000L,"Chín trăm bốn mươi nghìn");
    testCase.put(950_000L,"Chín trăm năm mươi nghìn");
    testCase.put(960_000L,"Chín trăm sáu mươi nghìn");
    testCase.put(970_000L,"Chín trăm bảy mươi nghìn");
    testCase.put(980_000L,"Chín trăm tám mươi nghìn");
    testCase.put(990_000L,"Chín trăm chín mươi nghìn");
    testCase.put(1_000_000L,"Một triệu");
    testCase.put(1_010_000L,"Một triệu không trăm mười nghìn");
    testCase.put(1_020_000L,"Một triệu không trăm hai mươi nghìn");
    testCase.put(1_030_000L,"Một triệu không trăm ba mươi nghìn");
    testCase.put(1_040_000L,"Một triệu không trăm bốn mươi nghìn");
    testCase.put(1_050_000L,"Một triệu không trăm năm mươi nghìn");
    testCase.put(1_060_000L,"Một triệu không trăm sáu mươi nghìn");
    testCase.put(1_070_000L,"Một triệu không trăm bảy mươi nghìn");
    testCase.put(1_080_000L,"Một triệu không trăm tám mươi nghìn");
    testCase.put(1_090_000L,"Một triệu không trăm chín mươi nghìn");
    testCase.put(1_100_000L,"Một triệu một trăm nghìn");
    testCase.put(1_110_000L,"Một triệu một trăm mười nghìn");
    testCase.put(1_120_000L,"Một triệu một trăm hai mươi nghìn");
    testCase.put(1_130_000L,"Một triệu một trăm ba mươi nghìn");
    testCase.put(1_140_000L,"Một triệu một trăm bốn mươi nghìn");
    testCase.put(1_150_000L,"Một triệu một trăm năm mươi nghìn");
    testCase.put(1_160_000L,"Một triệu một trăm sáu mươi nghìn");
    testCase.put(1_170_000L,"Một triệu một trăm bảy mươi nghìn");
    testCase.put(1_180_000L,"Một triệu một trăm tám mươi nghìn");
    testCase.put(1_190_000L,"Một triệu một trăm chín mươi nghìn");
    testCase.put(1_200_000L,"Một triệu hai trăm nghìn");
    testCase.put(1_210_000L,"Một triệu hai trăm mười nghìn");
    testCase.put(1_220_000L,"Một triệu hai trăm hai mươi nghìn");
    testCase.put(1_230_000L,"Một triệu hai trăm ba mươi nghìn");
    testCase.put(1_240_000L,"Một triệu hai trăm bốn mươi nghìn");
    testCase.put(1_250_000L,"Một triệu hai trăm năm mươi nghìn");
    testCase.put(1_260_000L,"Một triệu hai trăm sáu mươi nghìn");
    testCase.put(1_270_000L,"Một triệu hai trăm bảy mươi nghìn");
    testCase.put(1_280_000L,"Một triệu hai trăm tám mươi nghìn");
    testCase.put(1_290_000L,"Một triệu hai trăm chín mươi nghìn");
    testCase.put(1_300_000L,"Một triệu ba trăm nghìn");
    testCase.put(1_310_000L,"Một triệu ba trăm mười nghìn");
    testCase.put(1_320_000L,"Một triệu ba trăm hai mươi nghìn");
    testCase.put(1_330_000L,"Một triệu ba trăm ba mươi nghìn");
    testCase.put(1_340_000L,"Một triệu ba trăm bốn mươi nghìn");
    testCase.put(1_350_000L,"Một triệu ba trăm năm mươi nghìn");
    testCase.put(1_360_000L,"Một triệu ba trăm sáu mươi nghìn");
    testCase.put(1_370_000L,"Một triệu ba trăm bảy mươi nghìn");
    testCase.put(1_380_000L,"Một triệu ba trăm tám mươi nghìn");
    testCase.put(1_390_000L,"Một triệu ba trăm chín mươi nghìn");
    testCase.put(1_400_000L,"Một triệu bốn trăm nghìn");
    testCase.put(1_410_000L,"Một triệu bốn trăm mười nghìn");
    testCase.put(1_420_000L,"Một triệu bốn trăm hai mươi nghìn");
    testCase.put(1_430_000L,"Một triệu bốn trăm ba mươi nghìn");
    testCase.put(1_440_000L,"Một triệu bốn trăm bốn mươi nghìn");
    testCase.put(1_450_000L,"Một triệu bốn trăm năm mươi nghìn");
    testCase.put(1_460_000L,"Một triệu bốn trăm sáu mươi nghìn");
    testCase.put(1_470_000L,"Một triệu bốn trăm bảy mươi nghìn");
    testCase.put(1_480_000L,"Một triệu bốn trăm tám mươi nghìn");
    testCase.put(1_490_000L,"Một triệu bốn trăm chín mươi nghìn");
    testCase.put(1_500_000L,"Một triệu năm trăm nghìn");
    testCase.put(1_510_000L,"Một triệu năm trăm mười nghìn");
    testCase.put(1_520_000L,"Một triệu năm trăm hai mươi nghìn");
    testCase.put(1_530_000L,"Một triệu năm trăm ba mươi nghìn");
    testCase.put(1_540_000L,"Một triệu năm trăm bốn mươi nghìn");
    testCase.put(1_550_000L,"Một triệu năm trăm năm mươi nghìn");
    testCase.put(1_560_000L,"Một triệu năm trăm sáu mươi nghìn");
    testCase.put(1_570_000L,"Một triệu năm trăm bảy mươi nghìn");
    testCase.put(1_580_000L,"Một triệu năm trăm tám mươi nghìn");
    testCase.put(1_590_000L,"Một triệu năm trăm chín mươi nghìn");
    testCase.put(1_600_000L,"Một triệu sáu trăm nghìn");
    testCase.put(1_610_000L,"Một triệu sáu trăm mười nghìn");
    testCase.put(1_620_000L,"Một triệu sáu trăm hai mươi nghìn");
    testCase.put(1_630_000L,"Một triệu sáu trăm ba mươi nghìn");
    testCase.put(1_640_000L,"Một triệu sáu trăm bốn mươi nghìn");
    testCase.put(1_650_000L,"Một triệu sáu trăm năm mươi nghìn");
    testCase.put(1_660_000L,"Một triệu sáu trăm sáu mươi nghìn");
    testCase.put(1_670_000L,"Một triệu sáu trăm bảy mươi nghìn");
    testCase.put(1_680_000L,"Một triệu sáu trăm tám mươi nghìn");
    testCase.put(1_690_000L,"Một triệu sáu trăm chín mươi nghìn");
    testCase.put(1_700_000L,"Một triệu bảy trăm nghìn");
    testCase.put(1_710_000L,"Một triệu bảy trăm mười nghìn");
    testCase.put(1_720_000L,"Một triệu bảy trăm hai mươi nghìn");
    testCase.put(1_730_000L,"Một triệu bảy trăm ba mươi nghìn");
    testCase.put(1_740_000L,"Một triệu bảy trăm bốn mươi nghìn");
    testCase.put(1_750_000L,"Một triệu bảy trăm năm mươi nghìn");
    testCase.put(1_760_000L,"Một triệu bảy trăm sáu mươi nghìn");
    testCase.put(1_770_000L,"Một triệu bảy trăm bảy mươi nghìn");
    testCase.put(1_780_000L,"Một triệu bảy trăm tám mươi nghìn");
    testCase.put(1_790_000L,"Một triệu bảy trăm chín mươi nghìn");
    testCase.put(1_800_000L,"Một triệu tám trăm nghìn");
    testCase.put(1_810_000L,"Một triệu tám trăm mười nghìn");
    testCase.put(1_820_000L,"Một triệu tám trăm hai mươi nghìn");
    testCase.put(1_830_000L,"Một triệu tám trăm ba mươi nghìn");
    testCase.put(1_840_000L,"Một triệu tám trăm bốn mươi nghìn");
    testCase.put(1_850_000L,"Một triệu tám trăm năm mươi nghìn");
    testCase.put(1_860_000L,"Một triệu tám trăm sáu mươi nghìn");
    testCase.put(1_870_000L,"Một triệu tám trăm bảy mươi nghìn");
    testCase.put(1_880_000L,"Một triệu tám trăm tám mươi nghìn");
    testCase.put(1_890_000L,"Một triệu tám trăm chín mươi nghìn");
    testCase.put(1_900_000L,"Một triệu chín trăm nghìn");
    testCase.put(1_910_000L,"Một triệu chín trăm mười nghìn");
    testCase.put(1_920_000L,"Một triệu chín trăm hai mươi nghìn");
    testCase.put(1_930_000L,"Một triệu chín trăm ba mươi nghìn");
    testCase.put(1_940_000L,"Một triệu chín trăm bốn mươi nghìn");
    testCase.put(1_950_000L,"Một triệu chín trăm năm mươi nghìn");
    testCase.put(1_960_000L,"Một triệu chín trăm sáu mươi nghìn");
    testCase.put(1_970_000L,"Một triệu chín trăm bảy mươi nghìn");
    testCase.put(1_980_000L,"Một triệu chín trăm tám mươi nghìn");
    testCase.put(1_990_000L,"Một triệu chín trăm chín mươi nghìn");
    testCase.put(2_000_000L,"Hai triệu");

    // Execute tests
    AtomicInteger passed = new AtomicInteger();
    Boolean result = testCase.entrySet().stream().map(entry -> {
      String vietString = num2String(entry.getKey());

      boolean comparision = entry.getValue().equalsIgnoreCase(vietString);
      if (!comparision) {
        System.out.println(String.format("[Failed] result: |%s| expected |%s|", vietString, entry.getValue()));
      } else {
        passed.getAndIncrement();
        System.out.println(String.format("[Passed] result: |%d| - |%s|", entry.getKey(), entry.getValue()));
      }

      return comparision;
    }).reduce(Boolean.TRUE, (a, b) -> a && b);

    // Final report
    System.out.println("Test passed: " + passed.get() + "/" + testCase.size());
    if (!result) {
      System.out.println("Some test(s) failed!");
    }
  }
}
