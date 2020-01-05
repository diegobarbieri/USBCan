package com.company;

import java.awt.*;
import java.util.Scanner;
import de.fischl.usbtin.*;
import jssc.SerialPortList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Benvenuto all'app, per procedere inserire un comando, per maggiori informazioni inserire Help:");
        MenuPrincipale();
    }

    public static void MenuPrincipale() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println(">>");
        String inputText = keyboard.next();
        switch (inputText) {
            case ("Help"):
                System.out.println("Per inserire un intervallo di ID da testare digitare Brute e seguire le indicazioni mostrate sullo schermo");
                MenuPrincipale();
                break;
            case ("Brute"):
                System.out.println("Brute");
                MenuBrute();
                break;
            default:
                System.out.println("Non hai inserito un comando valido, riprovare");
                MenuPrincipale();
        }
    }

    public static void MenuBrute() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Inserire un intervallo di numeri da testare (Estremi inclusi)...");
        System.out.println("Inserire il primo numero: ");
        int firstNumber = keyboard.nextInt();
        System.out.println("Inserire il secondo numero...");
        int secondNumber = keyboard.nextInt();
        BeginCanComunication(firstNumber, secondNumber);
    }

    public static void BeginCanComunication(int firstNumber, int secondNumber) {
        try {
            // create the instances
            USBtin usbtin = new USBtin();
            usbtin.connect("/dev/ttyACM1");
            usbtin.openCANChannel(10000, USBtin.OpenMode.ACTIVE);
            for (int i = firstNumber; i <= secondNumber; i++) {
                usbtin.send(new CANMessage(i, new byte[]{0x11, 0x22, 0x33}));
                usbtin.closeCANChannel();
                usbtin.disconnect();
            }
        } catch (USBtinException ex) {
            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);

        }
    }
}
