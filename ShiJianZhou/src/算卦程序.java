import java.util.Random;
import java.util.Scanner;

// 爻实体类：存储阴阳、是否动爻
class Yao {
    boolean isYang;  // true阳爻 false阴爻
    boolean isDong;  // 是否动爻（老阴老阳）

    public Yao(boolean yang, boolean dong) {
        this.isYang = yang;
        this.isDong = dong;
    }
}

public class 算卦程序 {
    // 随机工具
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    // 摇一次铜钱，生成一爻
    public static Yao createSingleYao() {
        int sum = 0;
        // 三枚铜钱，反面1点，正面2点
        for (int i = 0; i < 3; i++) {
            // 随机1或2
            sum += random.nextInt(2) + 1;
        }
        // 传统铜钱起卦规则
        if (sum == 3) {
            // 三反 老阳9，动爻
            return new Yao(true, true);
        } else if (sum == 6) {
            // 三正 老阴6，动爻
            return new Yao(false, true);
        } else if (sum == 4 || sum == 5) {
            // 少阳7 静阳
            return new Yao(true, false);
        } else {
            // 少阴8 静阴
            return new Yao(false, false);
        }
    }

    // 生成完整六爻本卦
    public static Yao[] createWholeGua() {
        Yao[] gua = new Yao[6];
        for (int i = 0; i < 6; i++) {
            gua[i] = createSingleYao();
        }
        return gua;
    }

    // 打印卦象（从上爻到初爻）
    public static void printGua(Yao[] gua) {
        System.out.println("\n========== 本次卦象 ==========");
        for (int i = 5; i >= 0; i--) {
            String line;
            if (gua[i].isYang) {
                line = "━━━━━━";
            } else {
                line = "━━　━━";
            }
            if (gua[i].isDong) {
                line += "  ○动";
            }
            System.out.println(line);
        }
        System.out.println("==============================");
    }

    // 简易解卦，统计动爻数量断吉凶
    public static void analyzeGua(Yao[] gua) {
        int dongCount = 0;
        for (Yao yao : gua) {
            if (yao.isDong) dongCount++;
        }
        System.out.println("\n【卦象解析】");
        System.out.println("卦内动爻总数：" + dongCount + " 个");
        if (dongCount == 0) {
            System.out.println("无动爻：局势稳定，不可急躁，安分守己等待时机，稳中求进。");
        } else if (dongCount == 1) {
            System.out.println("单爻独动：事情存在明确转机，主动行动更容易成事，小吉。");
        } else if (dongCount >= 2 && dongCount <= 3) {
            System.out.println("2~3个动爻：前路起伏较多，好坏参半，重大决定务必三思，切勿冲动。");
        } else {
            System.out.println("多爻乱动：变数极大，近期不适合签约、投资、远行，建议暂缓计划。");
        }
    }

    // 菜单界面
    public static void showMenu() {
        System.out.println("\n===== 简易铜钱算卦程序 =====");
        System.out.println("1. 静心起卦测算");
        System.out.println("0. 退出程序");
        System.out.println("=============================");
        System.out.print("请输入操作序号：");
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int op = scanner.nextInt();
            if (op == 0) {
                System.out.println("程序退出，万事顺遂！");
                scanner.close();
                break;
            } else if (op == 1) {
                System.out.println("\n心中默念所求之事，正在摇卦......");
                Yao[] benGua = createWholeGua();
                printGua(benGua);
                analyzeGua(benGua);
            } else {
                System.out.println("输入错误！只能输入 1 或 0");
            }
            System.out.println("--------------------------------");
        }
    }
}