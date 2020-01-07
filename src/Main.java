import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Benvenuto all'app, per procedere inserire un comando, per maggiori informazioni inserire Help:");
        MenuPrincipale();
    }

    public static void MenuPrincipale() {
        Scanner keyboard = new Scanner(System.in);
        String inputText = keyboard.next();
        switch (inputText.toUpperCase()) {
            case ("HELP"):
                System.out.println("Per inserire un unico ID da testare digitare Single e seguire le indicazioni mostrate sullo schermo");
                System.out.println("Per inserire un intervallo di ID da testare digitare Multiple e seguire le indicazioni mostrate sullo schermo");
                MenuPrincipale();
                break;
            case ("SINGLE"):
                MenuSingle();
                break;
            case ("MULTIPLE"):
                MenuMulti();
                break;
            case ("QUIT"):
                System.out.println("Disconnesso");
                return;
            default:
                System.out.println("Non hai inserito un comando valido, riprovare");
                MenuPrincipale();
        }
    }

    public static void MenuMulti() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Inserire un intervallo di numeri da testare (Estremi inclusi)...");
        System.out.println("Inserire il primo numero: ");
        String firstNumber = keyboard.nextLine();
        System.out.println("Inserire il secondo numero...");
        String secondNumber = keyboard.nextLine();
        System.out.println("Inserire il BaudRate...");
        BruteWithMultiplesId(firstNumber, secondNumber);
    }

    public static void BruteWithMultiplesId(String firstNumber, String secondNumber) {
        try {
            // create the instances
            int first = Integer.parseInt(firstNumber,16);
            int second = Integer.parseInt(secondNumber,16);
            USBtin usbtin = new USBtin();
            usbtin.connect("COM3");
            usbtin.openCANChannel(50000, USBtin.OpenMode.ACTIVE);
           for (int i = first; i <= second; i++) {
                usbtin.send(new CANMessage(i, new byte[]{0x11, 0x22, 0x33}));
                System.out.println("Prova Id: " +Integer.toHexString(i));
               TimeUnit.SECONDS.sleep((long) 0.5);
            }
            usbtin.closeCANChannel();
            usbtin.disconnect();
        } catch (USBtinException | InterruptedException ex) {
            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);
        }
    }
    public static void MenuSingle() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Inserire un id da testare");
        String Id = keyboard.nextLine();
        System.out.println("Inserire il Baudrate");
        int BaudRate = keyboard.nextInt();
        BruteWithSingleId(Id,BaudRate);
    }

    public static void BruteWithSingleId(String Id, int BaudRate){
        try {
            int finalHex = Integer.parseInt(Id,16);
            System.out.println(finalHex);
            // create the instances
            USBtin usbtin = new USBtin();
            usbtin.connect("COM3");
            usbtin.openCANChannel(BaudRate, USBtin.OpenMode.ACTIVE);
            // for (int i = firstNumber; i <= secondNumber; i++) {
            usbtin.send(new CANMessage(finalHex, new byte[]{0x11, 0x22, 0x33}));
            usbtin.closeCANChannel();
            usbtin.disconnect();
            //}
        } catch (USBtinException ex) {
            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);
        }
    }
    public static void Error() {
        System.out.println("Dato non valido, sarai reindirizzato alla pagina principale");
    }
}