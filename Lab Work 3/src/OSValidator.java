public abstract class OSValidator {
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    public static String OSType() {
        //System.out.println("os.name: " + OS);
        if (IS_WINDOWS) {
            //System.out.println("This is Windows");
            return "Windows";
        } else if (IS_MAC) {
            //System.out.println("This is Mac");
            return "Mac";
        } else if (IS_UNIX) {
            //System.out.println("This is Unix or Linux");
            return "Unix";
        } else {
            //System.out.println("Your OS is not support!!");
            return "Not Supported";
        }
    }
}
