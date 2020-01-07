import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;

import java.io.IOException;
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
                System.out.println("Per ricevere i messaggi digitare Receiver");
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
            case ("RECEIVE"):
                receiveMessage();
                System.out.println("Sei in ascolto...");
                break;
            default:
                System.out.println("Non hai inserito un comando valido, riprovare");
                MenuPrincipale();
        }
    }

    public static void MenuMulti() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Inserire un intervallo di numeri da testare (Estremi inclusi)...");
        System.out.println("Inserire il primo numero esadecimale compreso tra 0 e 7ff: ");
        String firstNumber = keyboard.nextLine();
        System.out.println("Inserire il secondo numero esadecimale compreso tra 0 e 7ff...");
        String secondNumber = keyboard.nextLine();
        System.out.println("Inserire il BaudRate...");
        int BaudRate = keyboard.nextInt();
        BruteWithMultiplesId(firstNumber, secondNumber,BaudRate);
    }

    public static void BruteWithMultiplesId(String firstNumber, String secondNumber, int baudRate) {
        try {
            // create the instances
            int first = Integer.parseInt(firstNumber,16);
            int second = Integer.parseInt(secondNumber,16);
            USBtin usbtin = new USBtin();
            usbtin.connect("COM3");
            usbtin.openCANChannel(baudRate, USBtin.OpenMode.ACTIVE);
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
        System.out.println("Inserire un id da testare in formato esadecimale compreso tra 0 e 7ff");
        String Id = keyboard.nextLine();
        System.out.println("Inserire il Baudrate");
        int BaudRate = keyboard.nextInt();
        BruteWithSingleId(Id,BaudRate);
    }

    public static void BruteWithSingleId(String Id, int BaudRate){
        try {
            int finalHex = Integer.parseInt(Id,16);
            // create the instances
            USBtin usbtin = new USBtin();
            usbtin.connect("COM3");
            usbtin.openCANChannel(BaudRate, USBtin.OpenMode.ACTIVE);
            usbtin.send(new CANMessage(finalHex, new byte[]{0x11, 0x22, 0x33}));
            usbtin.closeCANChannel();
            usbtin.disconnect();
        } catch (USBtinException ex) {
            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);
        }
    }

    public static void receiveMessage() {
        try {
            // create the instances
            USBtin usbtin = new USBtin();
            // connect to USBtin and open CAN channel with 10kBaud in Active-Mode
            usbtin.connect("COM3"); // Windows e.g. "COM3"
            usbtin.openCANChannel(50000, USBtin.OpenMode.ACTIVE);
            usbtin.addMessageListener(canmsg -> System.out.println(canmsg));
            System.in.read();

            // close the CAN channel and close the connection
            usbtin.closeCANChannel();
            usbtin.disconnect();

        } catch (USBtinException | IOException ex) {

            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);

        }
    }
    public static void Error() {
        System.out.println("Dato non valido, sarai reindirizzato alla pagina principale");
    }
}