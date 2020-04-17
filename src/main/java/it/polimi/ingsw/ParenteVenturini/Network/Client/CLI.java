package it.polimi.ingsw.ParenteVenturini.Network.Client;

import it.polimi.ingsw.ParenteVenturini.Network.MessagesToServer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements ViewInterface {

    private Scanner stdIn = new Scanner(System.in);
    private ClientSideController clientSideController;
    private String nickname;

    public CLI(ClientSideController clientInMessageHandler) {
        this.clientSideController = clientInMessageHandler;
    }

    @Override
    public String login(){
        try {
            System.out.println("Inserire nickname");
            String name = stdIn.nextLine();
            System.out.println("Inserire numero giocatori");
            String numOfPlayers = stdIn.nextLine();
            MessageToServer message = new AccessGameMessageRequest(name, numOfPlayers);
            clientSideController.sendMessage(message);
            System.out.println("Message inviato");
            System.out.println("Il tuo nickanme è: "+name);
            nickname = name;
            return name;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void chooseCards(List<String> cardsName, int numberOfCardsRequired) {
        System.out.println("Digita il numero delle carte che vuoi usare:");
        List<String> choosen = new ArrayList<>();

        int i = 1;
        for (String name: cardsName){
            System.out.println(i+" - "+name);
            i++;
        }

        while(choosen.size()!=numberOfCardsRequired){
            System.out.println("numero: ");
            String num = stdIn.nextLine();
            if(!choosen.contains(cardsName.get(Integer.parseInt(num)-1)))
                choosen.add(cardsName.get(Integer.parseInt(num)-1));
            else
                System.out.println("Carta già scelta");
        }

        MessageToServer message = new StoreSelectedCardsRequest(nickname, choosen);
        clientSideController.sendMessage(message);
        System.out.println("Message inviato");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printString(String s){
        System.out.println(s);
    }

    @Override
    public void displayMenu(){
        while(true) {
            printString("--Menu--");
            printString("1- Scegli la tua carta");
            printString("2- Posiziona operai");
            printString("Scelta: ");
            stdIn.nextLine();
        }
    }

    @Override
    public void displayChooseCardMenu() {
        int choice;
        do {
            printString("--Menu card SetUp--");
            printString("1- Get possible Cards");
            printString("2- Choose and send your card");
            printString("Choice: ");
            String number = stdIn.nextLine();
            choice = Integer.parseInt(number);
            if (choice == 1) {
                MessageToServer message = new AviableCardRequest(nickname);
                clientSideController.sendMessage(message);
            } else if (choice == 2) {
                printString("card name:");
                String card = stdIn.nextLine();
                MessageToServer message = new SetPlayerCardRequest(nickname, card);
                clientSideController.sendMessage(message);
            }
        }while(choice<1 || choice>2);
    }

    @Override
    public void displayMessage(String s) {
        printString(s);
    }


}
